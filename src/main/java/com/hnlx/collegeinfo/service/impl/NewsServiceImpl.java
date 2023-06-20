package com.hnlx.collegeinfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.hnlx.collegeinfo.common.ApiKey;
import com.hnlx.collegeinfo.entity.param.news.DistrictNewsParam;
import com.hnlx.collegeinfo.entity.returnning.news.NewsResult;
import com.hnlx.collegeinfo.service.NewsService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * description : 新闻服务实现类
 * @author : Pikachudy
 * @date : 2022/10/17 15:53
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public NewsResult getDistrictNews(DistrictNewsParam param) {
        RestTemplate restTemplate = new RestTemplate();
        int news_num= param.getNews_num();
        String district_name=param.getDistrict_name();
        String district_name_key = district_name+"_news_"+param.getNews_num();
        // 读取缓存
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        NewsResult newsResult = JSON.parseObject(ops.get(district_name_key),NewsResult.class);
        if(newsResult!=null){
            return newsResult;
        }
        // 请求数据
        newsResult = restTemplate.getForObject("http://api.tianapi.com/world/index?key="+ ApiKey.TIANXING_KEY +"&num="+news_num+"&word="+district_name,NewsResult.class);
        if(newsResult.getCode()==200){
            ops.set(district_name_key,JSON.toJSONString(newsResult), Duration.ofDays(1));
        }
        return newsResult;
    }
}
