package com.hnlx.collegeinfo.entity.vo;

import lombok.Data;

/**
 * description : 大学基本信息VO
 * @author : Pikachudy
 * @date : 2022/11/15 14:24
 */
@Data
public class CollegeBasicInfo {
    int universityId;
    String universityBadge;
    String universityEnName;
    String universityLocation;
    String universityIntroduction;
    int universityStudentNum;
    String universityChName;
    String universityTuition;
    int universityQsRank;
    int universityTheRank;
    int universityUsnewsRank;
}
