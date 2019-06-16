package com.baiwen.business.mapper;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface IsignMapper {
    Integer addUserSign(HashMap<String, Object> objectObjectHashMap);
}
