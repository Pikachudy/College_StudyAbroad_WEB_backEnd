package com.hnlx.collegeinfo.entity.param.institution;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

/**
 * @Author: qxh
 * @Date: 2022/12/12
 * @Description: 添加机构入参
 */
@Data
@Schema(description = "添加机构入参")
public class InstitutionPostParam {
    String name;
    String phone;
    String qualify;
    String introduction;
    String profile;
    String city;
    String province;
    String location;
    String email;
    String lessons_characteristic;
    String lessons;
    String target;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date createtime;
}
