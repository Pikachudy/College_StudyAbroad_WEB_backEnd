package com.hnlx.collegeinfo.service;

import com.hnlx.collegeinfo.entity.param.college.BingVideoParam;


/**
 * description : 知乎相关服务接口
 * @author : Pikachudy
 * @date : 2022/10/17 20:17
 */
public interface BingService {
    String getVideo(BingVideoParam param);
}
