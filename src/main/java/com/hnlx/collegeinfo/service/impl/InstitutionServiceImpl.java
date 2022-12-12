package com.hnlx.collegeinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hnlx.collegeinfo.entity.po.Institution;
import com.hnlx.collegeinfo.entity.returnning.institution.InstitutionDetail;
import com.hnlx.collegeinfo.map.InstitutionMapper;
import com.hnlx.collegeinfo.service.InstitutionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: qxh
 * @Date: 2022/12/12
 * @Description: 机构信息 服务类实现
 */
@Service
public class InstitutionServiceImpl implements InstitutionService {
    @Resource
    private InstitutionMapper institutionMapper;
    @Override
    public Object getInstitutionById(int institution_id) {
        Institution institution = institutionMapper.selectById(institution_id);
        InstitutionDetail result = new InstitutionDetail();
        BeanUtils.copyProperties(institution,result);
        result.setInstitutionPhoto(List.of(institution.getInstitutionPhoto().split(";")));
        return result;
    }
}
