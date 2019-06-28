package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IGoodsMapper;
import com.baiwen.business.mapper.IUserGoodsMapper;
import com.baiwen.business.mapper.IUserMapper;
import com.baiwen.business.model.Goods;
import com.baiwen.business.model.User;
import com.baiwen.business.model.UserGoods;
import com.baiwen.business.service.IGoodsService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Data
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private IGoodsMapper goodsMapper;

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private IUserGoodsMapper userGoodsMapper;

    @Override
    public List<Goods> getGoodsList(Map params) {
        if(params == null){
            return null;
        }
        return goodsMapper.getGoodsList(params);
    }

    @Override
    public Goods getGoods(Integer goodsId) {
        if(goodsId == null || goodsId == 0){
            return null;
        }
        return goodsMapper.getGoods(goodsId);
    }

    @Transactional
    @Override
    public void addUserGoods(User user,Goods goods, UserGoods userGoods) {
        if(user != null && goods != null && userGoods != null){
            userMapper.updateUser(user);
            goodsMapper.updateGoods(goods);
            userGoodsMapper.addUserGoods(userGoods);
        }
    }
}
