package com.hzit.shop.mapper;


import com.hzit.shop.model.GoodsOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsOrderMapper {

    int insertSelective(GoodsOrder record);

    GoodsOrder selectByPrimaryKey(String goodsOrderId);

//  int updateByExampleSelective(@Param("record") GoodsOrder record, @Param("example") GoodsOrderExample example);

    int updateByPrimaryKeySelective(GoodsOrder record);

}
