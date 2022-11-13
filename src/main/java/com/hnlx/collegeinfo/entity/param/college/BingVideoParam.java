package com.hnlx.collegeinfo.entity.param.college;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description : 从Bing获取大学相关视频入参
 * @author : Pikachudy
 * @date : 2022/10/17 20:14
 */
@Data
@Schema(description = "从Bing获取大学相关信息入参")
public class BingVideoParam {
    @NotNull
    @Schema(description = "大学名称")
    private String college_name;
}
