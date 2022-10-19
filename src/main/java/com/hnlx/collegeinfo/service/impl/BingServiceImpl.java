package com.hnlx.collegeinfo.service.impl;



import com.hnlx.collegeinfo.common.ApiKey;
import com.hnlx.collegeinfo.entity.param.college.BingVideoParam;
import com.hnlx.collegeinfo.entity.returnning.BingSearchResult;
import com.hnlx.collegeinfo.service.BingService;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchResult;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static com.hnlx.collegeinfo.service.impl.BingWebSearch.SearchWeb;
import static com.hnlx.collegeinfo.service.impl.BingWebSearch.prettify;

/**
 * description : bing相关服务实现
 * @author : Pikachudy
 * @date : 2022/10/17 20:18
 */
@Service
public class BingServiceImpl implements BingService {
    @SneakyThrows
    @Override
    public String getVideo(BingVideoParam param) {
        try {
            SearchResults result = SearchWeb(param.getCollege_name());
            return prettify(result.jsonResponse);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(1);
        }
        return null;
    }
}

/**
 * discription : bing 搜索服务封装
 * @author : Pikachudy
 * @date : 2022/10/19 0:10
 */
class BingWebSearch {
    static String subscriptionKey = ApiKey.BING_KEY;
    static String endpoint = "https://api.bing.microsoft.com" + "/v7.0/search";
    // Add your own search terms, if desired.
    static String searchTerm = "Microsoft Bing";

    /**
     * 进行bing搜索
     * @param searchQuery 关键词
     * @return 请求结果对象
     * @throws Exception
     */
    public static SearchResults SearchWeb (String searchQuery) throws Exception {
        // 拼接URL
        URL url = new URL(endpoint + "?q=" +  URLEncoder.encode(searchQuery, "UTF-8"));
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

        // 接收JSON体
        InputStream stream = connection.getInputStream();
        Scanner scanner = new Scanner(stream);
        String response = scanner.useDelimiter("\\A").next();

        SearchResults results = new SearchResults(new HashMap<String, String>(), response);

        // 解header
        Map<String, List<String>> headers = connection.getHeaderFields();
        for (String header : headers.keySet()) {
            if (header == null) continue;      // may have null key
            if (header.startsWith("BingAPIs-") || header.startsWith("X-MSEdge-")) {
                results.relevantHeaders.put(header, headers.get(header).get(0));
            }
        }
        stream.close();
        scanner.close();

        return results;
    }
    /**
     * discription : 将JSON序列化
     * @author : Pikachudy
     * @date : 2022/10/18 21:08
     */
    public static String prettify (String json_text) {
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}

/**
 * discription : Bing Search 请求体返回接收对象
 * @author : Pikachudy
 * @date : 2022/10/19 0:08
 */
class SearchResults{
    HashMap<String, String> relevantHeaders;
    String jsonResponse;
    SearchResults(HashMap<String, String> headers, String json) {
        relevantHeaders = headers;
        jsonResponse = json;
    }
}

