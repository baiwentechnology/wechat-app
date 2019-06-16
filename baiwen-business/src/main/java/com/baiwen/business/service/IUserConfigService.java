package com.baiwen.business.service;

import com.baiwen.business.model.UserConfig;

import java.util.List;
import java.util.Map;

public interface IUserConfigService {

    List<UserConfig> getUserConfig(Map params);

    void saveOrUpdateConfig(UserConfig config);

}
