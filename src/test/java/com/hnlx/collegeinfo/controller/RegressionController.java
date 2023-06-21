package com.hnlx.collegeinfo.controller;

import com.hnlx.collegeinfo.RegressionTest;
import com.hnlx.collegeinfo.integration.IntegrationTest;
import com.hnlx.collegeinfo.integration.external.ExternalTest;
import com.hnlx.collegeinfo.unit.UnitTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("test")
public class RegressionController {
    @GetMapping("regression")
    public String doRegressionTest(){
        RegressionTest.doRegressionTest();
        return "success";
    }

    @GetMapping("unit")
    public String doUnitTest(){
        UnitTest.doUnitTest();
        return "success";
    }

    @GetMapping("integration")
    public String doIntegrationTest(){
        IntegrationTest.doIntegrationTest();
        return "success";
    }

    @GetMapping("external")
    public String doExternalTest(){
        ExternalTest.doExternalTest();
        return "success";
    }
}
