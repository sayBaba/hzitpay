package com.hzit.pay.service.service;

import com.hzit.pay.service.model.PayOrder;

/**
 * 支付流水相关
 */
public interface IPayOrderService {


    /**
     * 创建支付流水
     * @param payOrder
     * @return
     */
    public int createPayOrder(PayOrder payOrder);


    /**
     * 更新支付流水
     * @param payOrder
     * @return
     */
    public int updatePayOrder(PayOrder payOrder);


    /**
     * 根据商户订单号查询
     * @param mchOrderId
     * @return
     */
    public PayOrder getPayOrderBymchOrderId(String mchId,String mchOrderId);
}
