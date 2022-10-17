package com.hnlx.collegeinfo.service;

import com.hnlx.collegeinfo.entity.param.weather.GetWeatherParam;

/**
 * description : 天气服务接口
 * @author : Pikachudy
 * @date : 2022/10/17 17:05
 */
public interface WeatherService {
    public Object getWeather(GetWeatherParam param);
}
