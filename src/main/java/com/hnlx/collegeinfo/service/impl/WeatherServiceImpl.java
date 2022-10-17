package com.hnlx.collegeinfo.service.impl;

import com.hnlx.collegeinfo.common.ApiKey;
import com.hnlx.collegeinfo.entity.param.weather.GetWeatherParam;
import com.hnlx.collegeinfo.service.WeatherService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * description : 天气服务实现
 * @author : Pikachudy
 * @date : 2022/10/17 17:12
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    @Override
    public Object getWeather(GetWeatherParam param) {
        RestTemplate restTemplate = new RestTemplate();
        Object object = restTemplate.getForObject("https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&appid={API key}",
                Object.class,param.getLatitude(),param.getLongitude(), ApiKey.OPENWEATHER_KEY);
        return object;
    }
}
