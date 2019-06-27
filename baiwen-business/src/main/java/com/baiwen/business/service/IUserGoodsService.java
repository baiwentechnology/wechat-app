package com.baiwen.business.service;

import com.baiwen.business.model.UserGoods;

import java.util.List;
import java.util.Map;

public interface IUserGoodsService {

    List<UserGoods> getUserGoodsList(Map params);
}
