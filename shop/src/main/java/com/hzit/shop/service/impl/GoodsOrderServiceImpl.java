package com.hzit.shop.service.impl;
import com.hzit.common.utils.DateUtil;
import com.hzit.shop.constant.Constant;
import com.hzit.shop.mapper.GoodsOrderMapper;
import com.hzit.shop.model.GoodsOrder;
import com.hzit.shop.service.IGoodsOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class GoodsOrderServiceImpl implements IGoodsOrderService {

    private Logger logger = LoggerFactory.getLogger(GoodsOrderServiceImpl.class);
    private AtomicLong seq = new AtomicLong(0L);

    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    @Override
    public GoodsOrder addGoodsOrder(String goodsId, Long amount) {
        String goodsOrderId = String.format("%s%s%06d", "G", DateUtil.getSeqString(), (int) seq.getAndIncrement() % 1000000);
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setGoodsOrderId(goodsOrderId);
        goodsOrder.setGoodsId(goodsId);
        goodsOrder.setAmount(amount);
        goodsOrder.setUserId("test00001");
        goodsOrder.setGoodsName("测试商品");
        goodsOrder.setStatus(Constant.GOODS_ORDER_STATUS_INIT);
        int result = goodsOrderMapper.insertSelective(goodsOrder);
        logger.info("插入商品订单,返回:{}", result);
        return goodsOrder;
    }
}
