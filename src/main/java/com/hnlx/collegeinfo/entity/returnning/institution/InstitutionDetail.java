package com.hnlx.collegeinfo.entity.returnning.institution;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: qxh
 * @Date: 2022/12/12
 * @Description: 机构详情 返回对象
 */
@Data
@Schema(name = "获取机构详情返回对象")
public class InstitutionDetail {
    int institutionId;
    String institutionEmail;
    String institutionPhone;
    String institutionName;
    String institutionQualify;
    String institutionProfile;
    String institutionCity;
    String institutionLessons;
    String institutionIntroduction;
    String institutionLessonsCharacter;
    String institutionLocation;
    Timestamp institutionCreatetime;
    String institutionProvince;
    String institutionTarget;
    List<String> institutionPhoto;
}
