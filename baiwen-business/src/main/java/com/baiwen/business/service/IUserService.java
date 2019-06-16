package com.baiwen.business.service;

import com.baiwen.business.model.User;

public interface IUserService {

    void addUser(User user);

    User getUserByOpenId(String openId);
}
