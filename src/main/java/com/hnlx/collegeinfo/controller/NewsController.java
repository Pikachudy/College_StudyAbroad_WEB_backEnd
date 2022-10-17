package com.hnlx.collegeinfo.controller;

import com.hnlx.collegeinfo.entity.param.news.DistrictNewsParam;
import com.hnlx.collegeinfo.entity.returnning.ResultData;
import com.hnlx.collegeinfo.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * description : 新闻相关 Api
 * @author : Pikachudy
 * @date : 2022/10/17 15:43
 */
@Tag(name = "新闻相关")
@RestController
@RequestMapping("api/v1/news")
public class NewsController {
    @Resource
    NewsService newsService;

    @Operation(summary = "获取地区国际新闻")
    @GetMapping("/district_news")
    public ResultData<Object> getDistrictNews(DistrictNewsParam param){
        Object obj = newsService.getDistrictNews(param);
        return new ResultData<>().sendObj(true,obj);
    }
}
