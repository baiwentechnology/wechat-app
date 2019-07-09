package com.baiwen.business.mapper;

import com.baiwen.business.model.UserGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IUserGoodsMapper {

    List<UserGoods> getUserGoodsList(Map params);

    void addUserGoods(UserGoods userGoods);

    void updateUserGoods(UserGoods userGoods);

    UserGoods getUserGoodsById(@Param("userGoodsId") Integer userGoodsId);

    List<UserGoods> getMailList(Map params);
}
