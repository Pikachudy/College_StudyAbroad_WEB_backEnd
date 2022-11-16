package com.hnlx.collegeinfo.map;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import com.hnlx.collegeinfo.entity.po.College;
import org.apache.ibatis.annotations.Mapper;
/**
 * description : 大学数据表Mapper
 * @author : Pikachudy
 * @date : 2022/11/14 22:07
 */

@Mapper
public interface CollegeMapper extends MPJBaseMapper<College> {
}
