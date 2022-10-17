package com.hnlx.collegeinfo.entity.param.college;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description : 查询大学基本信息入参
 * @author : Pikachudy
 * @date : 2022/10/17 14:22
 */
@Data
@Schema(description = "查询大学基本信息入参")
public class CollegeBasicInfoParam {
    @NotNull
    @Schema(description = "大学英文名称")
    private String college_enname;
}
