package com.hnlx.collegeinfo.service;

import com.hnlx.collegeinfo.entity.param.news.DistrictNewsParam;

/**
 * description : 新闻服务接口
 * @author : Pikachudy
 * @date : 2022/10/17 15:45
 */
public interface NewsService {
    public Object getDistrictNews(DistrictNewsParam param);
}
