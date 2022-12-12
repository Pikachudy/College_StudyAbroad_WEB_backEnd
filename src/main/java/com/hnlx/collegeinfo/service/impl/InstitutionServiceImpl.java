package com.hnlx.collegeinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hnlx.collegeinfo.entity.param.institution.InstitutionListParam;
import com.hnlx.collegeinfo.entity.param.institution.InstitutionPostParam;
import com.hnlx.collegeinfo.entity.po.Institution;
import com.hnlx.collegeinfo.entity.returnning.institution.InstitutionDetail;
import com.hnlx.collegeinfo.entity.vo.InstitutionBasicInfo;
import com.hnlx.collegeinfo.map.InstitutionMapper;
import com.hnlx.collegeinfo.service.InstitutionService;
import com.hnlx.collegeinfo.utils.OssUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: qxh
 * @Date: 2022/12/12
 * @Description: 机构信息 服务类实现
 */
@Service
public class InstitutionServiceImpl implements InstitutionService {
    @Resource
    private InstitutionMapper institutionMapper;
    @Resource
    OssUtils ossUtils;

    /**
     * @Author qxh
     * @Description 通过id获取机构详情
     **/
    @Override
    public Object getInstitutionById(int institution_id) {
        Institution institution = institutionMapper.selectById(institution_id);
        InstitutionDetail result = new InstitutionDetail();
        BeanUtils.copyProperties(institution,result);
        result.setInstitutionPhoto(List.of(institution.getInstitutionPhoto().split(";")));
        return result;
    }

    /**
     * @Author qxh
     * @Description 获取机构列表
     **/
    @Override
    public Object getInstitutionList(InstitutionListParam param) {
        MPJLambdaWrapper<Institution> wrapper = new MPJLambdaWrapper<Institution>()
                .selectAll(Institution.class)
                .like(Institution::getInstitutionCity,param.getInstitution_city())
                .like(Institution::getInstitutionProvince,param.getInstitution_province())
                .like(Institution::getInstitutionTarget,param.getInstitution_target());

        List<InstitutionBasicInfo> listResult;
        if(param.getPage_size()==0){
            // 不进行分页，返回所有
            listResult = institutionMapper.selectJoinList(InstitutionBasicInfo.class,wrapper);
        }
        else{
            IPage<InstitutionBasicInfo> iPage = institutionMapper.selectJoinPage(
                    new Page<InstitutionBasicInfo>(param.getPage(), param.getPage_size()),
                    InstitutionBasicInfo.class,
                    wrapper
            );
            listResult = iPage.getRecords();
        }

        for(InstitutionBasicInfo basicInfo:listResult) {
            String temp = basicInfo.getInstitutionIntroduction();
            int len = temp.length();
            if (len > 140) {
                temp = temp.substring(0, Math.min(140, len));
                basicInfo.setInstitutionIntroduction(temp.substring(0, temp.lastIndexOf('，')) + "......");
            }
        }
        return listResult;
    }

    /**
     * @Author qxh
     * @Description 创建机构信息
     **/
    @Override
    public Object createInstitution(InstitutionPostParam param){
        Institution newInstitution = new Institution();
        newInstitution.setInstitutionCity(param.getInstitution_city());
        newInstitution.setInstitutionCreatetime(param.getInstitution_createtime());
        newInstitution.setInstitutionEmail(param.getInstitution_email());
        newInstitution.setInstitutionPhone(param.getInstitution_phone());
        newInstitution.setInstitutionName(param.getInstitution_name());
        newInstitution.setInstitutionLessons(param.getInstitution_lessons());
        newInstitution.setInstitutionIntroduction(param.getInstitution_introduction());
        newInstitution.setInstitutionTarget(param.getInstitution_target());
        newInstitution.setInstitutionProvince(param.getInstitution_province());
        newInstitution.setInstitutionLessonsCharacter(param.getInstitution_lessons_characteristic());

        Institution t = institutionMapper.selectOne(new QueryWrapper<Institution>()
                .orderByDesc("institution_id")
                .last("limit 1"));

        int id = 0;
        if(t != null) {
            id = t.getInstitutionId() + 1;
        }

        newInstitution.setInstitutionId(id);

        try {
            String qualifyUrl = ossUtils.uploadImage(param.getInstitution_qualify(), "institution/qualify/" + id);
            newInstitution.setInstitutionQualify(qualifyUrl);
            String profileUrl = ossUtils.uploadImage(param.getInstitution_profile(), "institution/profile/" + id);
            newInstitution.setInstitutionProfile(profileUrl);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }


        institutionMapper.insert(newInstitution);
        Map<String, Object> map = new HashMap<>();
        map.put("institution_id",id);
        return new JSONObject(map);
    }
}
