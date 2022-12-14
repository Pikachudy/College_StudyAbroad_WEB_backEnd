package com.hnlx.collegeinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hnlx.collegeinfo.entity.param.institution.*;
import com.hnlx.collegeinfo.entity.po.Institution;
import com.hnlx.collegeinfo.entity.returnning.institution.InstitutionDetail;
import com.hnlx.collegeinfo.entity.vo.InstitutionBasicInfo;
import com.hnlx.collegeinfo.entity.vo.InstitutionNumInfo;
import com.hnlx.collegeinfo.map.InstitutionMapper;
import com.hnlx.collegeinfo.service.InstitutionService;
import com.hnlx.collegeinfo.utils.OssUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
     * @Description 获取满足要求的机构数量和简要信息
     **/
    @Override
    public Object getInstitutionNum(InstitutionNumParam param) {
        MPJLambdaWrapper<Institution> wrapper = new MPJLambdaWrapper<Institution>()
                .selectAll(Institution.class)
                .like(Institution::getInstitutionCity,param.getInstitution_city())
                .like(Institution::getInstitutionProvince,param.getInstitution_province())
                .like(Institution::getInstitutionTarget,param.getInstitution_target());

        List<InstitutionNumInfo> list = institutionMapper.selectJoinList(InstitutionNumInfo.class,wrapper);
        Map<String,Object> result = new HashMap<>();
        result.put("institution_list",list);
        result.put("num",list.size());
        return result;
    }

    /**
     * @Author qxh
     * @Description 创建机构
     **/
    @Override
    public Object createInstitution(InstitutionPostParam param){
        Institution newInstitution = new Institution();
        newInstitution.setInstitutionName(param.getName());
        newInstitution.setInstitutionEmail(param.getEmail());
        newInstitution.setInstitutionPhone(param.getPhone());
        newInstitution.setInstitutionCity(param.getCity());
        newInstitution.setInstitutionProvince(param.getProvince());
        newInstitution.setInstitutionLocation(param.getLocation());
        newInstitution.setInstitutionTarget(param.getTarget());
        newInstitution.setInstitutionIntroduction(param.getIntroduction());
        newInstitution.setInstitutionLessons(param.getLessons());
        newInstitution.setInstitutionLessonsCharacter(param.getLessons_characteristic());
        newInstitution.setInstitutionCreatetime(param.getCreatetime());

        Institution t = institutionMapper.selectOne(new QueryWrapper<Institution>()
                .orderByDesc("institution_id")
                .last("limit 1"));

        int id = 0;
        if(t != null) {
            id = t.getInstitutionId() + 1;
        }

        newInstitution.setInstitutionId(id);

        try {
            String qualifyUrl = ossUtils.uploadImage(param.getQualify(), "institution/qualify/" + id);
            newInstitution.setInstitutionQualify(qualifyUrl);
            String profileUrl = ossUtils.uploadImage(param.getProfile(), "institution/profile/" + id);
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

    /**
     * @Author qxh
     * @Description 修改机构信息
     **/
    @Override
    public Object changeInstitutionInfo(InstitutionPutParam param) {
        Institution old = institutionMapper.selectById(param.getId());
        if (old == null) {
            return -1;
        }
        try {
            UpdateWrapper<Institution> wrapper = new UpdateWrapper<Institution>()
                    .eq("institution_id", param.getId())
                    .set(param.getName() != null, "institution_name", param.getName())
                    .set(param.getPhone() != null, "institution_phone", param.getPhone())
                    .set(param.getEmail() != null, "institution_email", param.getEmail())
                    .set(param.getQualify() != null, "institution_qualify", param.getQualify())
                    .set(param.getIntroduction() != null, "institution_introduction", param.getIntroduction())
                    .set(param.getProvince() != null, "institution_province", param.getProvince())
                    .set(param.getProfile() != null, "institution_profile", param.getProfile())
                    .set(param.getCity() != null, "institution_city", param.getCity())
                    .set(param.getLocation() != null, "institution_location", param.getLocation())
                    .set(param.getTarget() != null, "institution_target", param.getTarget())
                    .set(param.getLessons() != null, "institution_lessons", param.getLessons())
                    .set(param.getLessons_characteristic() != null, "institution_lessons_character", param.getLessons_characteristic());
            institutionMapper.update(old,wrapper);
        } catch (Exception e){
            return -1;
        }
        return 0;
    }

    /**
     * @Author qxh
     * @Description 删除机构
     **/
    @Override
    public Object deleteInstitution(int institution_id) {
        QueryWrapper<Institution> wrapper = new QueryWrapper<Institution>()
                .eq("institution_id",institution_id);

        return institutionMapper.delete(wrapper);
    }

}
