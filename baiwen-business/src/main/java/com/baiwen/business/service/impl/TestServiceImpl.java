package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IsignMapper;
import com.baiwen.business.model.UserSign;
import com.baiwen.business.service.ItestService;
import com.baiwen.common.util.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TestServiceImpl implements ItestService {
    @Autowired
    private IsignMapper isignMapper;

    @Override
    public Integer getTest() {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("userId",1);
        objectObjectHashMap.put("signDate",new DateTime().toString(DateUtils.YYYYMMDD_W));
        return isignMapper.addUserSign(objectObjectHashMap);
    }
}
