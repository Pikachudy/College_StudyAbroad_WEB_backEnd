package com.hnlx.collegeinfo.entity.param.college;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description : 获取知乎大学相关回答入参
 * @author : Pikachudy
 * @date : 2022/10/17 20:14
 */
@Data
@Schema(description = "从知乎获取大学相关回答入参")
public class ZhihuAnswerParam {
    @NotNull
    @Schema(description = "大学名称")
    private String college_name;
}
