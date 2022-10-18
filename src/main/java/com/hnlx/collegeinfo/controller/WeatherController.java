package com.hnlx.collegeinfo.controller;

import com.hnlx.collegeinfo.entity.param.weather.GetWeatherParam;
import com.hnlx.collegeinfo.entity.returnning.ResultData;
import com.hnlx.collegeinfo.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description : 天气相关控制类
 * @author : Pikachudy
 * @date : 2022/10/17 17:03
 */
@Tag(name = "天气相关")
@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {
    @Resource
    WeatherService weatherService;

    @Operation(description = "获取某地区天气")
    @GetMapping("getWeather")
    public ResultData<Object> getWeather(GetWeatherParam param){
        Object weather = weatherService.getWeather(param);
        return new ResultData<>().sendObj(true,weather);
    }
}
