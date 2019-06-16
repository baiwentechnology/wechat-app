package com.baiwen.business.mapper;

import com.baiwen.business.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserMapper {

    User getUserByOpenId(String openId);

    void addUser(User user);
}
