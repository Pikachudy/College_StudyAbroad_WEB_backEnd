package com.hnlx.collegeinfo.integration.external;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnlx.collegeinfo.controller.CollegeController;
import com.hnlx.collegeinfo.controller.NewsController;
import com.hnlx.collegeinfo.entity.param.news.DistrictNewsParam;
import com.hnlx.collegeinfo.entity.returnning.ResultData;
import com.hnlx.collegeinfo.entity.returnning.news.NewsResult;
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
@SpringBootTest
public class GetNewsTest {
    @Resource
    NewsController newsController;
    @Tag("获取新闻")
    @Tag("输入合法")
    @Tag("外部系统集成测试")
    @Feature("根据地区名称返回指定条数地区新闻")
    @Story("输入合法，若地区名称存在则返回指定条数地区新闻，若地区名称不存在则返回空结果")
    @ParameterizedTest
    @MethodSource("testCasesNormally")
    void getNews(DistrictNewsParam param, boolean expected){
        ResultData<Object> res = newsController.getDistrictNews(param);
        Assertions.assertEquals(expected, ((NewsResult)res.getObj()).getNewslist()!=null);
    }
    @Tag("获取新闻")
    @Tag("输入非法")
    @Tag("外部系统集成测试")
    @Feature("根据地区名称返回指定条数地区新闻")
    @Story("输入非法，返回空结果")
    @ParameterizedTest
    @MethodSource("testCasesUnexpectedly")
    void getNewsUnexpectedly(DistrictNewsParam param, boolean expected){
        ResultData<Object> res = newsController.getDistrictNews(param);
        Assertions.assertEquals(expected, ((NewsResult)res.getObj()).getNewslist()!=null);
    }
    /**
     * 测试用例——输入正常
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesNormally() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getNews/Normal.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<TestCase> testCases = jsonArray.toJavaList(TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getParam(), testCase.isExpected())
        );
    }
    /**
     * 测试用例——输入非法
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesUnexpectedly() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getNews/unexpected.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<TestCase> testCases = jsonArray.toJavaList(TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getParam(), testCase.isExpected())
        );
    }

    @Data
    static class TestCase {
        private DistrictNewsParam param;
        private boolean expected;
    }
}
