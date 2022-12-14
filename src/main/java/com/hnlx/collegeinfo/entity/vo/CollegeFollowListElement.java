package com.hnlx.collegeinfo.entity.vo;

import lombok.Data;

/**
 * @Author: qxh
 * @Date: 2022/12/14
 * @Description: 用户关注高校列表元素
 */
@Data
public class CollegeFollowListElement {
    int universityId;
    String universityBadge;
    String universityEnname;
    String universityChname;
    String universityCountry;
    String universityRegion;
}
