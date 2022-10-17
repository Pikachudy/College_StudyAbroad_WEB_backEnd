package com.hnlx.collegeinfo.entity.param.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description : 获取地区新闻入参
 * @author : Pikachudy
 * @date : 2022/10/17 15:58
 */
@Data
@Schema(description = "获取地区新闻入参")
public class DistrictNewsParam {
    @NotNull
    @Schema(description = "地区名称")
    private String district_name;
    @NotNull
    @Schema(description = "请求新闻数")
    private int news_num;
}
