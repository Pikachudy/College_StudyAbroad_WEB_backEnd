package com.hnlx.collegeinfo.entity.returnning.weather;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * description : 天气返回参数
 * @author : Pikachudy
 * @date : 2022/11/16 23:07
 */
@Data
@Schema(name = "天气返回参数")
public class WeatherResult {
    @Schema(description = "是否成功")
    String cod;
    @Schema(description = "提示信息")
    int message;
    @Schema(description = "天气列表")
    List<Object> list;
    @Schema(description = "cnt")
    int cnt;
    @Schema(description = "城市信息")
    Object city;


}
