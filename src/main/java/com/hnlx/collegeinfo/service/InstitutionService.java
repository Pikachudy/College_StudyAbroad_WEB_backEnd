package com.hnlx.collegeinfo.service;

import com.hnlx.collegeinfo.entity.param.institution.FollowInstitutionParam;
import com.hnlx.collegeinfo.entity.param.institution.InstitutionListParam;
import com.hnlx.collegeinfo.entity.param.institution.InstitutionPostParam;
import com.hnlx.collegeinfo.entity.param.institution.InstitutionPutParam;

import java.io.IOException;

/**
 * @Author: qxh
 * @Date: 2022/12/12
 * @Description: 机构信息 服务类
 */
public interface InstitutionService {
    Object getInstitutionById(int institution_id);
    Object getInstitutionList(InstitutionListParam param);
    Object createInstitution(InstitutionPostParam param);
    Object changeInstitutionInfo(InstitutionPutParam param);
    Object deleteInstitution(int institution_id);
    Object followInstitution(FollowInstitutionParam param);
    Object cancelFollowInstitution(FollowInstitutionParam param);
    Object isFollowInstitution(FollowInstitutionParam param);
    Object followInstitutionList(int user_id);
}