package com.hnlx.collegeinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.query.MPJQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hnlx.collegeinfo.entity.param.institution.FollowInstitutionParam;
import com.hnlx.collegeinfo.entity.param.institution.InstitutionListParam;
import com.hnlx.collegeinfo.entity.param.institution.InstitutionPostParam;
import com.hnlx.collegeinfo.entity.param.institution.InstitutionPutParam;
import com.hnlx.collegeinfo.entity.po.FollowInstitution;
import com.hnlx.collegeinfo.entity.po.Institution;
import com.hnlx.collegeinfo.entity.returnning.institution.InstitutionDetail;
import com.hnlx.collegeinfo.entity.vo.InstitutionBasicInfo;
import com.hnlx.collegeinfo.entity.vo.InstitutionListElement;
import com.hnlx.collegeinfo.map.FollowInstitutionMapper;
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
    private FollowInstitutionMapper followInstitutionMapper;
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
     * @Description 修改机构信息
     **/


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

    /**
     * @Author qxh
     * @Description 关注机构
     **/
    @Override
    public Object followInstitution(FollowInstitutionParam param) {
        int user_id = param.getUser_id();
        int institution_id = param.getInstitution_id();
        QueryWrapper<FollowInstitution> wrapper = new QueryWrapper<FollowInstitution>()
                .eq("institution_id",institution_id)
                .eq("user_id",user_id);

        FollowInstitution old = followInstitutionMapper.selectOne(wrapper);

        try {
            if (old == null) {
                FollowInstitution followInstitution = new FollowInstitution();
                followInstitution.setInstitutionId(institution_id);
                followInstitution.setUserId(user_id);
                followInstitution.setFollowTime(new Date());
                followInstitution.setCancel(false);
                followInstitutionMapper.insert(followInstitution);
            } else if (old.isCancel()) {
                UpdateWrapper<FollowInstitution> wrapper1 = new UpdateWrapper<FollowInstitution>()
                        .eq("user_id", user_id)
                        .eq("institution_id", institution_id)
                        .set("follow_time", new Date())
                        .set("cancel", false);
                followInstitutionMapper.update(old, wrapper1);
            }
        } catch (Exception e){
            return -1;
        }
        return 0;
    }

    /**
     * @Author qxh
     * @Description 取消关注机构
     **/
    @Override
    public Object cancelFollowInstitution(FollowInstitutionParam param) {
        int user_id = param.getUser_id();
        int institution_id = param.getInstitution_id();
        QueryWrapper<FollowInstitution> wrapper = new QueryWrapper<FollowInstitution>()
                .eq("institution_id",institution_id)
                .eq("user_id",user_id);

        FollowInstitution old = followInstitutionMapper.selectOne(wrapper);

        if (old == null) {
            return -1;
        }

        try {
             if (!old.isCancel()) {
                UpdateWrapper<FollowInstitution> wrapper1 = new UpdateWrapper<FollowInstitution>()
                        .eq("user_id", user_id)
                        .eq("institution_id", institution_id)
                        .set("follow_time", new Date())
                        .set("cancel", true);
                followInstitutionMapper.update(old, wrapper1);
            }
        } catch (Exception e){
            return -1;
        }
        return 0;
    }

    /**
     * @Author qxh
     * @Description 查看是否关注机构
     **/
    @Override
    public Object isFollowInstitution(FollowInstitutionParam param) {
        int user_id = param.getUser_id();
        int institution_id = param.getInstitution_id();
        QueryWrapper<FollowInstitution> wrapper = new QueryWrapper<FollowInstitution>()
                .eq("institution_id",institution_id)
                .eq("cancel",false);

        long count = followInstitutionMapper.selectCount(wrapper);

        FollowInstitution followInstitution = followInstitutionMapper.selectOne(wrapper.eq("user_id",user_id));
        boolean flag = false;
        if(followInstitution != null){
            flag = true;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("isFollow",flag);
        map.put("count",count);
        return new JSONObject(map);
    }

    /**
     * @Author qxh
     * @Description 获取用户关注的机构列表
     **/
    @Override
    public Object followInstitutionList(int user_id) {
        QueryWrapper<FollowInstitution> wrapper = new QueryWrapper<FollowInstitution>()
                .eq("user_id",user_id)
                .eq("cancel",false);

        List<FollowInstitution> follows = followInstitutionMapper.selectList(wrapper);
        List<InstitutionListElement> result = new ArrayList<>();

        for(FollowInstitution follow:follows){
            MPJQueryWrapper<Institution> wrapper1 = new MPJQueryWrapper<Institution>()
                    .selectAll(Institution.class)
                    .eq("institution_id",follow.getInstitutionId());

            InstitutionListElement t = institutionMapper.selectJoinOne(InstitutionListElement.class,wrapper1);
            result.add(t);
        }

        int count = result.size();

        Map<String, Object> map = new HashMap<>();
        map.put("count",count);
        map.put("follows",result);
        return new JSONObject(map);
    }
}