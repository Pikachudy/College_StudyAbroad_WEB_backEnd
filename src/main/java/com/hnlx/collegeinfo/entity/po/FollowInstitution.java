package com.hnlx.collegeinfo.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: qxh
 * @Date: 2022/12/13
 * @Description: 关注机构表
 */

@Data
@TableName("follow_institution")
public class FollowInstitution {
    int institutionId;
    int userId;
    Date followTime;
    boolean cancel;
}
