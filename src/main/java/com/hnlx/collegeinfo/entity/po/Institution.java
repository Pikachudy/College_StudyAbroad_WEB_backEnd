package com.hnlx.collegeinfo.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

/**
 * @Author: qxh
 * @Date: 2022/12/12
 * @Description: 机构对象
 */
@Data
@TableName("institution")
public class Institution {
    @TableId
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
    Date institutionCreatetime;
    String institutionProvince;
    String institutionTarget;
    String institutionPhoto;
}
