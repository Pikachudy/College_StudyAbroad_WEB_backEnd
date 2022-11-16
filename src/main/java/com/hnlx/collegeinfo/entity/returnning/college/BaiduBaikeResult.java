package com.hnlx.collegeinfo.entity.returnning.college;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * description : 百度百科返回对象
 * @author : Pikachudy
 * @date : 2022/11/16 22:24
 */
@Schema(name = "百度百科返回对象")
@Data
public class BaiduBaikeResult {
    @Schema(description = "是否成功")
    int code;
    @Schema(description = "提示信息")
    String msg;
    @Schema(description = "请求主体")
    Object data;
}
