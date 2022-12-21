package com.hnlx.collegeinfo.entity.param.institution;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: qxh
 * @Date: 2022/12/13
 * @Description: 获取机构数量入参
 */

@Data
@Schema(description = "获取机构数量入参")
public class InstitutionNumParam {
    @Schema(description = "机构所在省份")
    String institution_province = "";
    @Schema(description = "机构所在城市")
    String institution_city = "";
    @Schema(description = "机构面向国家")
    String institution_target = "";
}
