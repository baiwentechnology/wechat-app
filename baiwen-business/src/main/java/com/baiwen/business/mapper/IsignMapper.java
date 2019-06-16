package com.baiwen.business.mapper;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface IsignMapper {
    Integer addUserSign(HashMap<String, Object> objectObjectHashMap);

    List<String> getSignByUserId(int userId);

}
