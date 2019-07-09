package com.baiwen.business.mapper;

import com.baiwen.business.model.UserConfig;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IUserConfigMapper {

    List<UserConfig> getUserConfig(Map params);

    void addUserConfig(UserConfig config);

    void updateUserConfig(UserConfig config);
}
