package com.hnlx.collegeinfo.integration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnlx.collegeinfo.CollegeInfoApplication;
import com.hnlx.collegeinfo.controller.CollegeController;
import com.hnlx.collegeinfo.entity.returnning.ResultData;
import com.hnlx.collegeinfo.entity.returnning.college.CollegeDetailResult;
import com.hnlx.collegeinfo.service.impl.CollegeServiceImpl;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

/**
 * 通过id获取大学详情信息测试
 *
 * @author Pikachudy
 * @date 2023/06/11
 */
@SpringBootTest(classes = CollegeInfoApplication.class)
public class GetUniversityByIdTest {
    @Resource
    CollegeController collegeController;

    @Tag("获取大学详情")
    @Tag("输入合法")
    @Feature("用户输入大学id，返回大学详情")
    @Story("用户输入合法，若存在则返回大学详情，若不存在则返回空结果")
    @ParameterizedTest
    @MethodSource("testCasesNormally")
    void getUniversityById(int id,String expectedChName){
        ResultData<Object> res = collegeController.getUniversityById(id);
        Assertions.assertEquals(expectedChName!=null,res.isStatus());
    }
    @Tag("获取大学详情")
    @Tag("输入非法")
    @Feature("用户输入大学id，返回大学详情")
    @Story("用户输入非法值，返回空结果")
    @ParameterizedTest
    @MethodSource("testCasesUnexpectedly")
    void getUniversityByIdUnexpectedly(Object id,String expectedChName){
        if(id instanceof Integer){
            ResultData<Object> res = collegeController.getUniversityById((Integer) id);
            Assertions.assertEquals(expectedChName!=null,res.isStatus());
        }
        else{
            Assertions.assertFalse(id instanceof Integer, "输入的大学id类型非法");
        }

    }
    /**
     * 测试用例——输入正常
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesNormally() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getUniversityById/Normal.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<TestCase> testCases = jsonArray.toJavaList(TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getId(), testCase.getExpectedChName())
        );
    }
    /**
     * 测试用例——输入非法
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesUnexpectedly() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getUniversityById/Unexpected.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<TestCase> testCases = jsonArray.toJavaList(TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getId(), testCase.getExpectedChName())
        );
    }
    @Data
    static class TestCase {
        private Object id;
        private String expectedChName;

    }
}
