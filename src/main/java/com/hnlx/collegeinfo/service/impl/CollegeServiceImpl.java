package com.hnlx.collegeinfo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hnlx.collegeinfo.entity.param.college.SelectListParam;
import com.hnlx.collegeinfo.entity.po.College;
import com.hnlx.collegeinfo.entity.param.college.CollegeBasicInfoParam;
import com.hnlx.collegeinfo.entity.param.college.CollegeIntroParam;
import com.hnlx.collegeinfo.entity.po.Rank;
import com.hnlx.collegeinfo.entity.vo.CollegeBasicInfo;
import com.hnlx.collegeinfo.map.CollegeBasicInfoMapper;
import com.hnlx.collegeinfo.map.CollegeMapper;
import com.hnlx.collegeinfo.service.CollegeService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


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
    CollegeBasicInfoMapper collegeBasicInfoMapper;
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
        if(page_size==0){
            // 不进行分页，返回所有
            return collegeMapper.selectJoinList(CollegeBasicInfo.class,wrapper);
        }
        else{
            IPage<CollegeBasicInfo> iPage = collegeMapper.selectJoinPage(
                    new Page<CollegeBasicInfo>(cur_page,page_size),
                    CollegeBasicInfo.class,
                    wrapper
            );
            return iPage.getRecords();
        }
    }

    @Override
    public Object baiduCollegeIntro(CollegeIntroParam param) {
        RestTemplate restTemplate = new RestTemplate();
        Object object = restTemplate.getForObject("https://api.wer.plus/api/dub?t="+param.getCollege_name(),Object.class);
        return object;
    }

    @Override
    public Object hipoCollegeBasicInfo(CollegeBasicInfoParam param) {
        RestTemplate restTemplate = new RestTemplate();
        Object object = restTemplate.getForObject("http://universities.hipolabs.com/search?name="+param.getCollege_enname(),Object.class);
        return object;
    }
}
