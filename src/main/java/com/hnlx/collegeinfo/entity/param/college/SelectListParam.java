package com.hnlx.collegeinfo.entity.param.college;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * description : 筛选高校列表入参对象
 * @author : Pikachudy
 * @date : 2022/11/15 14:52
 */
@Data
@Schema(description = "筛选高校信息入参对象")
public class SelectListParam {
    @Schema(description = "排名年份")
    int rank_year;
    @Schema(description = "排名类型")
    String tag;
    @Schema(description = "大学所在国家")
    String university_country;
    @Schema(description = "当前页")
    int page;
    @Schema(description = "页大小")
    int page_size;
}
