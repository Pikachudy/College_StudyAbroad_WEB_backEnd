package com.hnlx.collegeinfo.service;

import com.hnlx.collegeinfo.entity.param.college.CollegeBasicInfoParam;
import com.hnlx.collegeinfo.entity.param.college.FollowCollegeParam;
import com.hnlx.collegeinfo.entity.param.college.CollegeIntroParam;
import com.hnlx.collegeinfo.entity.param.college.SelectListParam;

/**
 * description : 大学信息 服务类
 * @author : Pikachudy
 * @date : 2022/10/15 12:25
 */
public interface CollegeService {
    Object getUniversityIdByChname(String chname);
    Object getUniversityIdByEnname(String enname);
    Object getUniversityByChName(String chname);
    public Object getUniversityById(int id);
    public Object getUniversityList(SelectListParam param);
    public Object baiduCollegeIntro(CollegeIntroParam param);
    public Object hipoCollegeBasicInfo(CollegeBasicInfoParam param);

}
