package com.hnlx.collegeinfo.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class OssUtils {
    @Value("${oss.key}")
    private String accessKeyId;

    @Value("${oss.password}")
    private String secretAccessKey;

    @Value("${oss.endpoint}")
    private String endPoint;

    @Value("${oss.bucketName}")
    private String bucketName;

    @Value("${oss.baseurl}")
    private String baseUrl;

    public String uploadImage(String image, String name) throws IOException {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, secretAccessKey);
            // 获取文件后缀名
            String type =  "." + image.split(",")[0].split(";")[0].split("/")[1];
            name += type;
            // 获取base64的文件
            image = image.split(",")[1];
            byte[] bytesFile = Base64.decode(image);
            try {
                // 创建PutObject请求。
                InputStream inputStream = new ByteArrayInputStream(bytesFile);
                ossClient.putObject(bucketName, name, inputStream);
                String url = baseUrl + name;
                return url;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }


    public String uploadContent(String content, String name) throws IOException {
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, secretAccessKey);
            // 获取文件后缀名
            name += ".html";
            // 获取base64的文件
            byte[] bytesFile = Base64.decode(content);
            try {
                // 创建PutObject请求。
                InputStream inputStream = new ByteArrayInputStream(bytesFile);
                ossClient.putObject(bucketName, name, inputStream);
                String url = baseUrl + name;
                return url;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }
}
