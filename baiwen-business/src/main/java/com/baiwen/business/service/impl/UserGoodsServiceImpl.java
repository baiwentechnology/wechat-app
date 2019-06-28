package com.baiwen.business.service.impl;

import com.baiwen.business.mapper.IGoodsMapper;
import com.baiwen.business.mapper.IUserGoodsMapper;
import com.baiwen.business.model.UserGoods;
import com.baiwen.business.service.IUserGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserGoodsServiceImpl implements IUserGoodsService {

    @Autowired
    private IUserGoodsMapper userGoodsMapper;

    @Autowired
    private IGoodsMapper goodsMapper;

    @Override
    public List<UserGoods> getUserGoodsList(Map params) {
        if(params.isEmpty()){
            return null;
        }
        return userGoodsMapper.getUserGoodsList(params);
    }

    @Override
    public UserGoods getUserGoodsById(Integer userGoodsId) {
        if(userGoodsId == null || userGoodsId == 0){
            return null;
        }
        return userGoodsMapper.getUserGoodsById(userGoodsId);
    }

    /**
     * 过期一个新增一个库存
     * @param userGoodsList
     */
    @Override
    public void expreUserGoodsList(List<UserGoods> userGoodsList){
        if(userGoodsList == null || userGoodsList.size() == 0){
            return;
        }
        for(UserGoods goods : userGoodsList){
            boolean flag;
            try {
                userGoodsMapper.updateUserGoods(goods);
                flag = true;
            }catch (Exception e){
                log.error("商品Id:"+goods.getId()+"更新失败");
                flag = false;
            }
            if(flag){
                goodsMapper.addGoodsStock(goods.getGoodsId());
            }
        }
    }

    @Override
    public void updateUserGoods(UserGoods goods) {
        if(goods == null){
            return;
        }
        userGoodsMapper.updateUserGoods(goods);
    }

    @Override
    public List<UserGoods> getMailList(Map params) {
        if(params.isEmpty() || params.get("businessId") == null){
            return null;
        }
        return userGoodsMapper.getMailList(params);
    }
}
