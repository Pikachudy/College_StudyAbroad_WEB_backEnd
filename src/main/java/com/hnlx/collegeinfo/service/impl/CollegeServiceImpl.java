package com.hnlx.collegeinfo.service.impl;

import com.hnlx.collegeinfo.entity.param.college.CollegeBasicInfoParam;
import com.hnlx.collegeinfo.entity.param.college.CollegeIntroParam;
import com.hnlx.collegeinfo.service.CollegeService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * description : 大学信息 服务类实现
 * @author : Pikachudy
 * @date : 2022/10/15 12:33
 */
@Service
public class CollegeServiceImpl implements CollegeService {
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
