package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IUserGoodsMapper;
import com.baiwen.business.model.UserGoods;
import com.baiwen.business.service.IUserGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserGoodsServiceImpl implements IUserGoodsService {

    @Autowired
    private IUserGoodsMapper userGoodsMapper;

    @Override
    public List<UserGoods> getUserGoodsList(Map params) {
        if(params.isEmpty()){
            return null;
        }
        return userGoodsMapper.getUserGoodsList(params);
    }
}
