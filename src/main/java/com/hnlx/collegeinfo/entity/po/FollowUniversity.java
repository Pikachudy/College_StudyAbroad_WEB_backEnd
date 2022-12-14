package com.hnlx.collegeinfo.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: qxh
 * @Date: 2022/12/14
 * @Description: 关注高校对象
 */
@Data
@TableName("follow_university")
public class FollowUniversity {
    int universityId;
    int userId;
    Date followTime;
    boolean cancel;
}
