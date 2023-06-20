package com.hnlx.collegeinfo.unit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnlx.collegeinfo.CollegeInfoApplication;
import com.hnlx.collegeinfo.entity.param.college.SelectListParam;
import com.hnlx.collegeinfo.entity.returnning.college.CollegeListResult;
import com.hnlx.collegeinfo.service.impl.CollegeServiceImpl;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.Data;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CollegeInfoApplication.class)
public class GetUniversityListTest {
    @Resource
    CollegeServiceImpl collegeService;
    @Tag("获取大学列表")
    @Tag("输入合法")
    @Feature("用户输入筛选条件，返回符合条件的大学列表")
    @Story("用户输入合法，返回符合条件的大学列表")
    @ParameterizedTest
    @MethodSource("testCasesNormally")
    void getUniversityList(SelectListParam param,int expectedLength){
        CollegeListResult res = collegeService.getUniversityList(param);
        assertEquals(expectedLength,res.getCollegeBasicInfoList().size());
    }

    @Tag("获取大学列表")
    @Tag("输入非法")
    @Feature("用户输入筛选条件，返回符合条件的大学列表")
    @Story("用户输入非法，返回空值")
    @ParameterizedTest
    @MethodSource("testCasesUnexpectedly")
    void getUniversityListUnexpected(SelectListParam param,int expectedLength){
        CollegeListResult res = collegeService.getUniversityList(param);
        assertEquals(expectedLength,res.getCollegeBasicInfoList().size());
    }
    /**
     * 测试用例——输入正常
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesNormally() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getUniversityList/Normal.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<GetUniversityListTest.TestCase> testCases = jsonArray.toJavaList(GetUniversityListTest.TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getParam() ,testCase.getResArrayLength())
        );
    }
    /**
     * 测试用例——输入非法
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesUnexpectedly() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getUniversityList/Unexpected.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<GetUniversityListTest.TestCase> testCases = jsonArray.toJavaList(GetUniversityListTest.TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getParam() ,testCase.getResArrayLength())
        );
    }

    @Data
    static class TestCase{
        SelectListParam param;
        int resArrayLength;
    }
}
