package com.hnlx.collegeinfo.entity.param.weather;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * description : 获取某地天气入参
 * @author : Pikachudy
 * @date : 2022/10/17 17:07
 */
@Data
@Schema(description = "获取某地天气入参")
public class GetWeatherParam {
    @NotNull
    @Schema(description = "地区经度")
    private float longitude;

    @NotNull
    @Schema(description = "地区纬度")
    private float latitude;
}
