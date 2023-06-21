package com.hnlx.collegeinfo.integration.external;

import com.hnlx.collegeinfo.unit.GetCollegeIdByNameTest;
import com.hnlx.collegeinfo.unit.GetUniversityByIdTest;
import com.hnlx.collegeinfo.unit.GetUniversityListTest;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class ExternalTest {
    public static void doExternalTest() {
        // 开辟子线程执行测试
//        String command = "D:\\CodeTestTools\\allure-2.22.1\\bin\\allure.bat serve allure-results";
        String command = "E:\\SoftWare\\allure-commandline-2.22.1\\allure-2.22.1\\bin\\allure.bat serve allure-results-external";
        Path absolutePath = Paths.get(System.getProperty("user.dir"))
                .toAbsolutePath();
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(absolutePath.toFile());
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(selectClass(GetNewsTest.class))
                .build();
        LauncherDiscoveryRequest request1 = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(selectClass(GetWeatherTest.class))
                .build();

        launcher.execute(request);
        launcher.execute(request1);
        // 测试完成后运行命令行命令
        //String command = "D:\\CodeTestTools\\allure-2.22.1\\bin\\allure.bat serve allure-results";
        String command = "E:\\SoftWare\\allure-commandline-2.22.1\\allure-2.22.1\\bin\\allure.bat serve allure-results";
        Path absolutePath = Paths.get(System.getProperty("user.dir"))
                .toAbsolutePath();
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.directory(absolutePath.toFile());
        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
