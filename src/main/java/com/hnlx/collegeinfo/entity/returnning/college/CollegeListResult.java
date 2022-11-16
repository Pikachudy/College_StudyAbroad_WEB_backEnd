package com.hnlx.collegeinfo.entity.returnning.college;

import com.hnlx.collegeinfo.entity.vo.CollegeBasicInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * description : 获取高校列表返回对象
 * @author : Pikachudy
 * @date : 2022/11/16 23:47
 */
@Data
@Schema(name = "获取高校列表返回对象")
public class CollegeListResult {
    @Schema(description = "当前页高校列表")
    List<CollegeBasicInfo> collegeBasicInfoList;
    @Schema(description = "符合该条件的高校数")
    int college_num;
}
