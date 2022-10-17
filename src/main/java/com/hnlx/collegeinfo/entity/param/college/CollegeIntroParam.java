package com.hnlx.collegeinfo.entity.param.college;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * description : 查询大学信息入参对象
 * @author : Pikachudy
 * @date : 2022/10/15 11:17
 */
@Data
@Schema(description = "查询大学信息入参对象")
public class CollegeIntroParam {
    @NotNull
    @Schema(description = "大学名称")
    private String college_name;

//    @NotNull
//    @Schema(description = "大学id")
//    private int college_id;
}
