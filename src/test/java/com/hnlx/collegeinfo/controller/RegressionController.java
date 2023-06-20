package com.hnlx.collegeinfo.controller;

import com.hnlx.collegeinfo.RegressionTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("test/regression")
public class RegressionController {
    @GetMapping
    public String doRegressionTest (){

        RegressionTest.doRegressionTest();
        return "success";
    }
}
