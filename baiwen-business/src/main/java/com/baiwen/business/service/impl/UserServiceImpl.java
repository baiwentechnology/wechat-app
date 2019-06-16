package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IUserMapper;
import com.baiwen.business.model.User;
import com.baiwen.business.service.IUserService;
import com.baiwen.common.util.StringUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public void addUser(User user) {
        if(user == null){
            return;
        }
        userMapper.addUser(user);
    }

    @Override
    public User getUserByOpenId(String openId) {
        if(StringUtils.isEmpty(openId)){
            return null;
        }
        User user = userMapper.getUserByOpenId(openId);
        return user;
    }
}
