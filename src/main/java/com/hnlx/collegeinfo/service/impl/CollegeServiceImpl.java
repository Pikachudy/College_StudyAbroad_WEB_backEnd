package com.hnlx.collegeinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hnlx.collegeinfo.entity.param.college.SelectListParam;
import com.hnlx.collegeinfo.entity.po.*;
import com.hnlx.collegeinfo.entity.param.college.CollegeBasicInfoParam;
import com.hnlx.collegeinfo.entity.param.college.CollegeIntroParam;
import com.hnlx.collegeinfo.entity.returnning.college.BaiduBaikeResult;
import com.hnlx.collegeinfo.entity.returnning.college.CollegeDetailResult;
import com.hnlx.collegeinfo.entity.returnning.college.CollegeListResult;
import com.hnlx.collegeinfo.entity.vo.CollegeBasicInfo;
import com.hnlx.collegeinfo.map.CollegeMapper;
import com.hnlx.collegeinfo.map.RankMapper;
import com.hnlx.collegeinfo.service.CollegeService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;


/**
 * description : 大学信息 服务类实现
 * @author : Pikachudy
 * @date : 2022/10/15 12:33
 */
@Service
public class CollegeServiceImpl implements CollegeService {
    @Resource
    CollegeMapper collegeMapper;
    @Resource
    RankMapper rankMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * @Author qxh
     * @Description 根据中文名获取id
     **/
    @Override
    public Object getUniversityIdByChname(String chname) {
        QueryWrapper<College> wrapper = new QueryWrapper<College>()
                .eq("university_ch_name",chname);
        College college = collegeMapper.selectOne(wrapper);
        if(college == null){
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("university_id",college.getUniversityId());
        return new JSONObject(map);
    }

    /**
     * @Author qxh
     * @Description 根据中文名获取大学详情
     **/
    @Override
    public Object getUniversityByChName(String chname) {
        QueryWrapper<College> wrapper = new QueryWrapper<College>()
                .like("university_ch_name",chname);

        College college = collegeMapper.selectOne(wrapper);
        QueryWrapper<Rank> wrapper1 = new QueryWrapper<Rank>()
                .eq("university_id",college.getUniversityId());
        List<Rank> ranks = rankMapper.selectList(wrapper1);
        CollegeDetailResult result = new CollegeDetailResult();
        BeanUtils.copyProperties(college,result);
        result.setRank(ranks);
        result.setUniversityCollege(List.of(college.getUniversityCollege().split("\n")));
        result.setUniversityPhoto(List.of(college.getUniversityPhoto().split(";")));
        return result;
    }

    @Override
    public CollegeDetailResult getUniversityById(int id) {
        QueryWrapper<Rank> wrapper = new QueryWrapper<Rank>()
                .eq("university_id",id);
        List<Rank> ranks = rankMapper.selectList(wrapper);
        College college = collegeMapper.selectById(id);
        if(college == null){
            return null;
        }
        CollegeDetailResult result = new CollegeDetailResult();
        BeanUtils.copyProperties(college,result);
        result.setRank(ranks);
        result.setUniversityCollege(List.of(college.getUniversityCollege().split("\n")));
        result.setUniversityPhoto(List.of(college.getUniversityPhoto().split(";")));
        return result;
    }

    @Override
    public CollegeListResult getUniversityList(SelectListParam param) {
        System.out.println("\n"+param);
        MPJLambdaWrapper<College> wrapper = new MPJLambdaWrapper<College>()
                .selectAll(College.class)
                .selectAll(Rank.class)
                .rightJoin(Rank.class,Rank::getUniversityId,College::getUniversityId)
                .eq(Rank::getRankYear,param.getRank_year())
                .eq(param.getUniversity_country()!=null,College::getUniversityCountry,param.getUniversity_country());
        if(param.getTag()==null){
            wrapper.orderByAsc(true, Rank::getUniversityQsRank);
        }
        else{
            switch (param.getTag()) {
                case "THE_rank" -> wrapper.orderByAsc(true, Rank::getUniversityTheRank);
                case "USNews_rank" -> wrapper.orderByAsc(true, Rank::getUniversityUsnewsRank);
                case "QS_rank" -> wrapper.orderByAsc(true, Rank::getUniversityQsRank);
            }
        }

        // 分页
        int page_size = param.getPage_size();
        int cur_page = param.getPage();
        List<CollegeBasicInfo> collegeBasicInfoList = collegeMapper.selectJoinList(CollegeBasicInfo.class,wrapper);
        int college_num=collegeBasicInfoList.size();
        CollegeListResult listResult = new CollegeListResult();

        listResult.setCollege_num(college_num);
        if(page_size==0){
            // 不进行分页，返回所有
            listResult.setCollegeBasicInfoList(collegeBasicInfoList);
        }
        else{
            IPage<CollegeBasicInfo> iPage = collegeMapper.selectJoinPage(
                    new Page<CollegeBasicInfo>(cur_page,page_size),
                    CollegeBasicInfo.class,
                    wrapper
            );
            listResult.setCollegeBasicInfoList(iPage.getRecords());
        }
        return listResult;
    }

    @Override
    public Object baiduCollegeIntro(CollegeIntroParam param) {
        // 构造索引读取缓存
        String college_info_key = param.getCollege_name()+"info_baidu";
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        BaiduBaikeResult info_cached = JSONObject.parseObject(ops.get(college_info_key), BaiduBaikeResult.class);

        if(info_cached!=null){
            return info_cached;
        }
        // 若无缓存则获取
        RestTemplate restTemplate = new RestTemplate();
        BaiduBaikeResult baiduBaikeResult = restTemplate.getForObject("https://api.wer.plus/api/dub?t="+param.getCollege_name(),BaiduBaikeResult.class);
        //System.out.println(object);
        assert baiduBaikeResult != null;
        if(baiduBaikeResult.getCode()==200){
          // 缓存存储
            ops.set(college_info_key, JSONObject.toJSONString(baiduBaikeResult), Duration.ofDays(1));
        }
        return baiduBaikeResult;
    }

    @Override
    public Object hipoCollegeBasicInfo(CollegeBasicInfoParam param) {
        RestTemplate restTemplate = new RestTemplate();
        Object object = restTemplate.getForObject("http://universities.hipolabs.com/search?name="+param.getCollege_enname(),Object.class);
        return object;
    }
}
