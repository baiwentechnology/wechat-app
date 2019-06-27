package com.baiwen.business.mapper;

import com.baiwen.business.model.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IGoodsMapper {

    List<Goods> getGoodsList(Map params);
}
