package com.hnlx.collegeinfo.entity.param.institution;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: qxh
 * @Date: 2022/12/12
 * @Description: 获取机构列表入参
 */
@Data
@Schema(description = "获取机构列表入参")
public class InstitutionListParam {
    @Schema(description = "机构所在省份")
    String institution_province = "";
    @Schema(description = "机构所在城市")
    String institution_city = "";
    @Schema(description = "机构面向国家")
    String institution_target = "";
    @Schema(description = "当前页")
    int page = 1;
    @Schema(description = "页大小")
    int page_size = 5;
}
