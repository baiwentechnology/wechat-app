package com.baiwen.business.mapper;

import com.baiwen.business.model.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGoodsMapper {

    List<Goods> getGoodsList();
}
