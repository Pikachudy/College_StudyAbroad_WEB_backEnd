package com.hnlx.collegeinfo.controller;

import com.alibaba.fastjson2.JSON;
import com.hnlx.collegeinfo.entity.param.college.CollegeBasicInfoParam;
import com.hnlx.collegeinfo.entity.param.college.CollegeIntroParam;
import com.hnlx.collegeinfo.entity.returnning.ResultData;
import com.hnlx.collegeinfo.service.CollegeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * description: 大学信息控制器
 * @author Pikachudy
 * @date 2022/10/15 11:00
 */

@Tag(name = "大学信息相关")
@RestController
@RequestMapping("api/v1/college")
public class CollegeController {
    @Resource
    CollegeService collegeService;
    @Operation(summary = "百度百科获取大学简介、图片")
    @GetMapping("intro")
    public ResultData<Object> baiduCollegeIntro(CollegeIntroParam param){
        Object obj=collegeService.baiduCollegeIntro(param);
        return new ResultData<>().sendObj(true,obj);
    }

    @Operation(summary = "Hipo获取大学域名、基本信息")
    @GetMapping("basic_info")
    public ResultData<Object> hipoCollegeBasicInfo(CollegeBasicInfoParam param){
        Object obj = collegeService.hipoCollegeBasicInfo(param);
        if(obj!=null){
            return new ResultData<>().sendObj(true,obj);
        }
        else{
            return new ResultData<>().FAILED();
        }
    }

}
