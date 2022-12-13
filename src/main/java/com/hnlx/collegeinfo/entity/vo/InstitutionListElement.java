package com.hnlx.collegeinfo.entity.vo;

import lombok.Data;

/**
 * @Author: qxh
 * @Date: 2022/12/13
 * @Description: 机构列表元素
 */
@Data
public class InstitutionListElement{
    int institutionId;
    String institutionName;
    String institutionProfile;
    String institutionProvince;
    String institutionCity;
    String institutionTarget;
}
