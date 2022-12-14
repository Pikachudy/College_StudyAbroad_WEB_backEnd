package com.hnlx.collegeinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hnlx.collegeinfo.entity.param.college.FollowCollegeParam;
import com.hnlx.collegeinfo.entity.param.college.SelectListParam;
import com.hnlx.collegeinfo.entity.po.College;
import com.hnlx.collegeinfo.entity.param.college.CollegeBasicInfoParam;
import com.hnlx.collegeinfo.entity.param.college.CollegeIntroParam;
import com.hnlx.collegeinfo.entity.po.FollowInstitution;
import com.hnlx.collegeinfo.entity.po.FollowUniversity;
import com.hnlx.collegeinfo.entity.po.Rank;
import com.hnlx.collegeinfo.entity.returnning.college.BaiduBaikeResult;
import com.hnlx.collegeinfo.entity.returnning.college.CollegeListResult;
import com.hnlx.collegeinfo.entity.vo.CollegeBasicInfo;
import com.hnlx.collegeinfo.map.CollegeMapper;
import com.hnlx.collegeinfo.map.FollowUniversityMapper;
import com.hnlx.collegeinfo.service.CollegeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.List;


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
    FollowUniversityMapper followUniversityMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public Object getUniversityById(int id) {
        College college = collegeMapper.selectById(id);
        return college;
    }

    @Override
    public Object getUniversityList(SelectListParam param) {
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

    /**
     * @Author qxh
     * @Description 关注高校
     **/
    @Override
    public Object followCollege(FollowCollegeParam param) {
        int user_id = param.getUser_id();
        int university_id = param.getUniversity_id();
        QueryWrapper<FollowUniversity> wrapper = new QueryWrapper<FollowUniversity>()
                .eq("university_id",university_id)
                .eq("user_id",user_id);

        FollowUniversity old = followUniversityMapper.selectOne(wrapper);

        try {
            if (old == null) {
                FollowUniversity followUniversity = new FollowUniversity();
                followUniversity.setUniversityId(university_id);
                followUniversity.setUserId(user_id);
                followUniversity.setFollowTime(new Date());
                followUniversity.setCancel(false);
                followUniversityMapper.insert(followUniversity);
            } else if (old.isCancel()) {
                UpdateWrapper<FollowUniversity> wrapper1 = new UpdateWrapper<FollowUniversity>()
                        .eq("user_id", user_id)
                        .eq("university_id", university_id)
                        .set("follow_time", new Date())
                        .set("cancel", false);
                followUniversityMapper.update(old, wrapper1);
            }
        } catch (Exception e){
            return -1;
        }
        return 0;
    }
}
