package com.hnlx.collegeinfo.integration.external;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnlx.collegeinfo.controller.WeatherController;
import com.hnlx.collegeinfo.entity.param.weather.GetWeatherParam;
import com.hnlx.collegeinfo.entity.returnning.ResultData;
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
public class GetWeatherTest {
    @Resource
    WeatherController weatherController;
    @Tag("获取天气")
    @Tag("输入合法")
    @Tag("外部系统集成测试")
    @Feature("根据经纬度返回天气")
    @Story("若经纬度存在则返回天气，若经纬度不存在则返回空结果")
    @ParameterizedTest
    @MethodSource("testCasesNormally")
    void getWeather(GetWeatherParam param, boolean expected){
        ResultData<Object> res = weatherController.getWeather(param);
        Assertions.assertEquals(expected, res.isStatus());
    }
    /**
     * 测试用例
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesNormally() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getWeather/Normal.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<GetWeatherTest.TestCase> testCases = jsonArray.toJavaList(GetWeatherTest.TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getParam(), testCase.isExpected())
        );
    }
    @Data
    static class TestCase {
        private GetWeatherParam param;
        private boolean expected;
    }
}
