package com.hnlx.collegeinfo.service.impl;

import com.hnlx.collegeinfo.entity.param.college.ZhihuAnswerParam;
import com.hnlx.collegeinfo.service.ZhihuService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * description : 知乎相关服务实现
 * @author : Pikachudy
 * @date : 2022/10/17 20:18
 */
@Service
public class ZhihuServiceImpl implements ZhihuService {
    @Override
    public Object getAnswer(ZhihuAnswerParam param) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://www.zhihu.com/search?q={question}&type=content&vertical=answer",Object.class,param.getCollege_name());
    }
}
