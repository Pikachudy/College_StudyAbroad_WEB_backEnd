package com.hnlx.collegeinfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.hnlx.collegeinfo.entity.param.college.*;
import com.hnlx.collegeinfo.entity.param.institution.FollowInstitutionParam;
import com.hnlx.collegeinfo.entity.returnning.ResultData;
import com.hnlx.collegeinfo.service.BingService;
import com.hnlx.collegeinfo.service.CollegeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

/**
 * description: 大学信息控制器
 * @author Pikachudy
 * @date 2022/10/15 11:00
 */

@Tag(name = "大学信息相关")
@RestController
@RequestMapping("college")
public class CollegeController {
    @Resource
    CollegeService collegeService;
    @Resource
    BingService bingService;
    @Operation(summary = "根据高校中文名返回id")
    @GetMapping("id")
    public ResultData<Object> getUniversityIdByChName(String university_chname){
        Object obj = collegeService.getUniversityIdByChname(university_chname);
        if(obj == null){
            return new ResultData<>().FAILED();
        }
        return new ResultData<>().sendObj(true,obj);
    }
    @Operation(summary = "根据高校中文名返回id")
    @GetMapping("id/en")
    public ResultData<Object> getUniversityIdByEnName(String university_enname){
        Object obj = collegeService.getUniversityIdByEnname(university_enname);
        if(obj == null){
            return new ResultData<>().FAILED();
        }
        return new ResultData<>().sendObj(true,obj);
    }


    @Operation(summary = "根据id获取大学基本信息")
    @GetMapping("{university_id}")
    public ResultData<Object> getUniversityById(@PathVariable("university_id") int id){
        Object obj = collegeService.getUniversityById(id);
        return new ResultData<>().sendObj(true,obj);
    }
    @Operation(summary = "根据中文名获取大学基本信息")
    @GetMapping
    public ResultData<Object> getUniversityInfoByChName(String university_chname){
        Object obj = collegeService.getUniversityByChName(university_chname);
        return new ResultData<>().sendObj(true,obj);
    }
    @Operation(summary = "根据条件筛选高校列表（带分页）")
    @GetMapping("list")
    public ResultData<Object> getUniversityList(SelectListParam param){
        Object obj = collegeService.getUniversityList(param);
        return new ResultData<>().sendObj(true,obj);
    }
    @Operation(summary = "百度百科获取大学简介、图片")
    @GetMapping("detail/intro")
    public ResultData<Object> baiduCollegeIntro(CollegeIntroParam param){
        Object obj=collegeService.baiduCollegeIntro(param);
        return new ResultData<>().sendObj(true,obj);
    }

    @Operation(summary = "Hipo获取大学域名、基本信息")
    @GetMapping("detail/basic_info")
    public ResultData<Object> hipoCollegeBasicInfo(CollegeBasicInfoParam param){
        Object obj = collegeService.hipoCollegeBasicInfo(param);
        if(obj!=null){
            return new ResultData<>().sendObj(true,obj);
        }
        else{
            return new ResultData<>().FAILED();
        }
    }

    @Operation(summary = "Bing获取大学相关资料")
    @GetMapping("detail/bing_search")
    public ResultData<Object> bingSearchVideo(BingVideoParam param){
        Object obj = JSONObject.parseObject(bingService.getVideo(param),Object.class);
        return new ResultData<>().sendObj(true,obj);
    }
}
