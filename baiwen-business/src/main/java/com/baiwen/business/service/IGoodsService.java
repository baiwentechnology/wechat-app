package com.baiwen.business.service;

import com.baiwen.business.model.Goods;

import java.util.List;
import java.util.Map;

public interface IGoodsService {

    List<Goods> getGoodsList(Map params);

}
