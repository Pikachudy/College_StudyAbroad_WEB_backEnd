package com.hnlx.collegeinfo.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * description : 大学排名po
 * @author : Pikachudy
 * @date : 2022/11/15 16:19
 */
@Data
@TableName("`rank`")
public class Rank {
    int universityQsRank;
    int universityTheRank;
    int universityUsnewsRank;
    int universityId;
    @TableId
    int rankYear;
}
