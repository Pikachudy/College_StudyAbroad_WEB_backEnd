package com.hnlx.collegeinfo.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * description : College PO
 * @author : Pikachudy
 * @date : 2022/11/14 22:11
 */
@Data
@TableName("university")
public class College {
    @TableId
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
    String universityCollege;
    String universityAbbreviation;
    String universityChName;
    float universityAddressX;
    float universityAddressY;
    int universityTeacherNum;
    String universityTuition;
    float universityTofelRequirement;
    float universityIeltsRequirement;
    String universityPhoto;
}
