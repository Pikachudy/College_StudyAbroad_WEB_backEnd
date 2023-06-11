package com.hnlx.collegeinfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

/**
 * 用户输入大学名称，返回大学id
 *
 * @author Pikachudy
 * @date 2023/06/10
 */
@SpringBootTest
class GetCollegeIdByNameTest{
    @Resource
    CollegeServiceImpl collegeService;

    @Tag("获取大学id")
    @Tag("输入合法")
    @Feature("用户输入大学中文名，返回大学id")
    @Story("用户输入合法，若存在则返回大学id，若不存在则返回空结果")
    @ParameterizedTest
    @MethodSource("testCasesNormally")
    void getIdByChName(String name,String id){
        Object res = collegeService.getUniversityIdByChname(name);
        if(res == null){
            // 若返回值为null，说明输入的大学名不存在。添加失败说明
            assertEquals(id,"-1","输入的大学名不存在");
        }
        else{
            // 从json字符串中获取university_id
            String resId = res.toString().substring(17,res.toString().length()-1);
            assertEquals(id,resId);
        }
    }
    /**
     * 测试用例——输入正常
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesNormally() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getUniversityIdByChName/Normal.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<TestCase> testCases = jsonArray.toJavaList(TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getChName(), testCase.getExpectedId())
        );
    }
    @Tag("获取大学id")
    @Tag("健壮性检测")
    @Tag("非法输入")
    @Feature("用户输入大学中文名，返回大学id")
    @Story("用户输入非法值，返回空结果")
    @ParameterizedTest
    @MethodSource("testCasesUnexpectedly")
    void getIdByChNameUnexpected(String name,String id){
        Object res = collegeService.getUniversityIdByChname(name);
        if(res == null){
            // 若返回值为null，说明输入的大学名不存在。添加失败说明
            assertEquals(id,"-1","输入的大学名不存在");
        }
        else{
            // 从json字符串中获取university_id
            String resId = res.toString().substring(17,res.toString().length()-1);
            assertEquals(id,resId);
        }
    }
    /**
     * 测试用例——健壮性检测——非法输入
     *
     * @return {@code Stream<Arguments>}
     * @throws IOException ioexception
     */
    static Stream<Arguments> testCasesUnexpectedly() throws IOException {
        String jsonContent = Files.readString(Path.of("src/test/testcases/getUniversityIdByChName/Unexpected.json"));
        JSONArray jsonArray = JSON.parseArray(jsonContent);

        List<TestCase> testCases = jsonArray.toJavaList(TestCase.class);

        return testCases.stream().map(testCase ->
                Arguments.of(testCase.getChName(), testCase.getExpectedId())
        );
    }
    @Data
    static class TestCase {
        private String chName;
        private String expectedId;
    }
}