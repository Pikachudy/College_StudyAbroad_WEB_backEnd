package com.hnlx.collegeinfo.entity.returnning.college;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;

/**
 * description : bing搜索返回对象
 * @author : Pikachudy
 * @date : 2022/10/18 20:52
 */
@Schema(description = "bing搜索返回对象")
public class BingSearchResult {
    public HashMap<String, String> relevantHeaders;
    public String jsonResponse;
    public BingSearchResult(HashMap<String, String> headers, String json) {
        relevantHeaders = headers;
        jsonResponse = json;
    }
}
