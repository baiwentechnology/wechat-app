package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IGoodsMapper;
import com.baiwen.business.model.Goods;
import com.baiwen.business.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private IGoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.getGoodsList();
    }
}
