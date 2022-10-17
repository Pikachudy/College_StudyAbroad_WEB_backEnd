package com.hnlx.collegeinfo.service.impl;

import com.hnlx.collegeinfo.common.ApiKey;
import com.hnlx.collegeinfo.entity.param.news.DistrictNewsParam;
import com.hnlx.collegeinfo.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * description : 新闻服务实现类
 * @author : Pikachudy
 * @date : 2022/10/17 15:53
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Override
    public Object getDistrictNews(DistrictNewsParam param) {
        RestTemplate restTemplate = new RestTemplate();
        int news_num= param.getNews_num();
        String district_name=param.getDistrict_name();
        Object object = restTemplate.getForObject("http://api.tianapi.com/world/index?key="+ ApiKey.TIANXING_KEY +"&num="+news_num+"&word="+district_name,Object.class);
        return object;
    }
}
