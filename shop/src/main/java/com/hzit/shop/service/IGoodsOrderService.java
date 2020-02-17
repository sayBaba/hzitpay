package com.hzit.shop.service;

import com.hzit.shop.model.GoodsOrder;

/**
 * 模拟商城订单接口
 */
public interface IGoodsOrderService {

    /**
     * 添加商品订单
     */
    public GoodsOrder addGoodsOrder(String goodsId, Long amount);

}
