package com.baiwen.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baiwen.business.mapper.IsysMapper;
import com.baiwen.business.model.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysServiceImpl implements IsysService {
    @Autowired
    private IsysMapper isysMapper;
    @Override
    public JSONObject getSysConfigById(int sysId) {
        JSONObject jsonObject = new JSONObject();
        SysConfig sysConfig=isysMapper.getSysConfigById(sysId);
        switch (sysConfig.getConfigType()){
            case 1:
                jsonObject.put("announcement",sysConfig.getConfigValue());
                break;
        }
        return jsonObject;
    }
}
