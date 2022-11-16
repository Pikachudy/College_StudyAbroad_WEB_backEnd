package com.hnlx.collegeinfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.hnlx.collegeinfo.common.ApiKey;
import com.hnlx.collegeinfo.entity.param.weather.GetWeatherParam;
import com.hnlx.collegeinfo.entity.returnning.weather.WeatherResult;
import com.hnlx.collegeinfo.service.WeatherService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * description : 天气服务实现
 * @author : Pikachudy
 * @date : 2022/10/17 17:12
 */
@Service
public class WeatherServiceImpl implements WeatherService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public Object getWeather(GetWeatherParam param) {
        // 读取缓存
        String location_key = param.getLatitude()+"_"+ param.getLongitude();
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        WeatherResult weatherResult = JSON.parseObject(ops.get(location_key), WeatherResult.class);

        if(weatherResult!=null){
            return weatherResult;
        }

        // 获取数据
        RestTemplate restTemplate = new RestTemplate();
        weatherResult = restTemplate.
                getForObject(
                        "http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}&lang={language}&units={measurement}",
                WeatherResult.class,param.getLatitude(),param.getLongitude(), ApiKey.OPENWEATHER_KEY,"zh_cn","metric");

        if(weatherResult.getCod().equals("200")){
            // 存入缓存
            ops.set(location_key,JSON.toJSONString(weatherResult),3, TimeUnit.HOURS);
        }
        return weatherResult;
    }
}
