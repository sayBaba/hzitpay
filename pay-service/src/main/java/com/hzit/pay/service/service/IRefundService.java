package com.hzit.pay.service.service;


import com.hzit.common.req.RefundOrderReq;
import com.hzit.pay.service.model.RefundOrder;

/**
 * 退款接口
 */
public interface IRefundService {

    /**
     * 创建退款流水
     * @param refundOrderReq
     * @return
     */
    public RefundOrder createRefundOrder(RefundOrderReq refundOrderReq);
}
