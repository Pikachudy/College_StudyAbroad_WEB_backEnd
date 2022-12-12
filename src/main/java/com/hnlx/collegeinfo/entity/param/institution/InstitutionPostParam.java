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
    String institution_name;
    String institution_phone;
    String institution_qualify;
    String institution_introduction;
    String institution_profile;
    String institution_city;
    String institution_province;
    String institution_location;
    String institution_email;
    String institution_lessons_characteristic;
    String institution_lessons;
    String institution_target;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date institution_createtime;
}
