package com.hnlx.collegeinfo.map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnlx.collegeinfo.entity.vo.CollegeBasicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * description : 用于Join后查表筛选高校列表，所以只需基本信息
 * @author : Pikachudy
 * @date : 2022/11/15 15:31
 */
@Mapper
public interface CollegeBasicInfoMapper extends BaseMapper<CollegeBasicInfo> {
    @Select("SELECT * FROM university INNER JOIN `rank` ON university.university_id=`rank`.university_id")
    List<CollegeBasicInfo> getAllCollegeBasicInfo();
}
