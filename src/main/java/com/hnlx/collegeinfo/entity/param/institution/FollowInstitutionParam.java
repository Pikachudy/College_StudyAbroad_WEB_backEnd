package com.hnlx.collegeinfo.entity.param.institution;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author: qxh
 * @Date: 2022/12/13
 * @Description: 关注机构入参
 */
@Data
@Schema(name="关注机构入参")
public class FollowInstitutionParam {
    int user_id;
    int institution_id;
}
