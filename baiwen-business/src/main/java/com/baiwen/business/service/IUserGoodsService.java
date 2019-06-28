package com.baiwen.business.service;

import com.baiwen.business.model.UserGoods;

import java.util.List;
import java.util.Map;

public interface IUserGoodsService {

    List<UserGoods> getUserGoodsList(Map params);

    UserGoods getUserGoodsById(Integer userGoodsId);

    void expreUserGoodsList(List<UserGoods> userGoodsList);

    void updateUserGoods(UserGoods goods);

    List<UserGoods> getMailList(Map params);
}
