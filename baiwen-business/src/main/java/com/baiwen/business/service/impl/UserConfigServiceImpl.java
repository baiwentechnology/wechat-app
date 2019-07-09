package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IUserConfigMapper;
import com.baiwen.business.model.UserConfig;
import com.baiwen.business.service.IUserConfigService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Data
public class UserConfigServiceImpl implements IUserConfigService {

    @Autowired
    private IUserConfigMapper userConfigMapper;

    @Override
    public List<UserConfig> getUserConfig(Map params) {
        if(params == null){
            return null;
        }
        List<UserConfig> userConfig = userConfigMapper.getUserConfig(params);
        return userConfig;
    }

    @Override
    public void saveOrUpdateConfig(UserConfig config) {
        if(config == null){
            return;
        }
        if(config.getId() != 0){
            userConfigMapper.updateUserConfig(config);
        }else{
            userConfigMapper.updateUserConfig(config);
        }
    }
}
