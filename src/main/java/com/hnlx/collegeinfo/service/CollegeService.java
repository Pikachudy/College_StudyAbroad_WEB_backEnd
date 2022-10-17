package com.hnlx.collegeinfo.service;

import com.hnlx.collegeinfo.entity.param.college.CollegeBasicInfoParam;
import com.hnlx.collegeinfo.entity.param.college.CollegeIntroParam;

/**
 * description : 大学信息 服务类
 * @author : Pikachudy
 * @date : 2022/10/15 12:25
 */
public interface CollegeService {
    public Object baiduCollegeIntro(CollegeIntroParam param);
    public Object hipoCollegeBasicInfo(CollegeBasicInfoParam param);
}
