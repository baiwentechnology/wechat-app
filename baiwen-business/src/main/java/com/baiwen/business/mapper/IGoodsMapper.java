package com.baiwen.business.mapper;

import com.baiwen.business.model.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IGoodsMapper {

    List<Goods> getGoodsList(Map params);

    Goods getGoods(@Param("goodsId")Integer goodsId);

    void updateGoods(Goods goods);

    void addGoodsStock(@Param("goodsId")Integer goodsId);
}
