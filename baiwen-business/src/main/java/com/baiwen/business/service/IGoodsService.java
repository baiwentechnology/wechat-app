package com.baiwen.business.service;

import com.baiwen.business.model.Goods;
import com.baiwen.business.model.User;
import com.baiwen.business.model.UserGoods;

import java.util.List;
import java.util.Map;

public interface IGoodsService {

    List<Goods> getGoodsList(Map params);

    Goods getGoods(Long goodsId);

    void addUserGoods(User user, UserGoods userGoods);
}
