package com.hnlx.collegeinfo.entity.param.institution;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

/**
 * @Author: qxh
 * @Date: 2022/12/13
 * @Description: 修改机构信息入参
 */
@Data
public class InstitutionPutParam {
    String id;
    String name;
    String phone;
    String qualify;
    String introduction;
    String profile;
    String city;
    String target;
    String location;
    String province;
    String email;
    String lessons_characteristic;
    String lessons;
}
