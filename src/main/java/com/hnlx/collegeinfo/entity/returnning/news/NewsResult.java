package com.hnlx.collegeinfo.entity.returnning.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * description : 新闻返回对象
 * @author : Pikachudy
 * @date : 2022/11/16 22:58
 */
@Data
@Schema(name = "新闻返回对象")
public class NewsResult {
    @Schema(description = "是否成功")
    int code;
    @Schema(description = "提示信息")
    String msg;
    @Schema(description = "请求主体")
    Object newslist;
}
