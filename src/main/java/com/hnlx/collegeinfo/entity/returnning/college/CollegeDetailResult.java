package com.hnlx.collegeinfo.entity.returnning.college;

import com.hnlx.collegeinfo.entity.po.Rank;
import lombok.Data;

import java.util.List;

/**
 * @Author: qxh
 * @Date: 2022/12/14
 * @Description: 高校详情返回对象
 */
@Data
public class CollegeDetailResult {
    int universityId;
    String universityEmail;
    String universityBadge;
    String universityEnName;
    String universityRegion;
    String universityCountry;
    String universityLocation;
    String universityIntroduction;
    int universityStudentNum;
    String universityWebsite;
    List<String> universityCollege;
    String universityAbbreviation;
    String universityChName;
    float universityAddressX;
    float universityAddressY;
    int universityTeacherNum;
    String universityTuition;
    float universityTofelRequirement;
    float universityIeltsRequirement;
    List<String> universityPhoto;
    List<Rank> rank;
}
