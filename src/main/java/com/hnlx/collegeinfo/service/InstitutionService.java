package com.hnlx.collegeinfo.service;

import com.hnlx.collegeinfo.entity.param.institution.InstitutionListParam;

/**
 * @Author: qxh
 * @Date: 2022/12/12
 * @Description: 机构信息 服务类
 */
public interface InstitutionService {
    Object getInstitutionById(int institution_id);
    Object getInstitutionList(InstitutionListParam param);
}
