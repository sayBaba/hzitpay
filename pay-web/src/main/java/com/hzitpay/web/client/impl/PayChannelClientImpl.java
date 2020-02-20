package com.hzitpay.web.client.impl;

import com.hzit.common.req.PayChannelReq;
import com.hzitpay.web.client.PayChannelClient;
import org.springframework.stereotype.Service;

@Service
public class PayChannelClientImpl implements PayChannelClient {


    @Override
    public String selectPayChannel(PayChannelReq refundOrderReq) {
        return "error";
    }

    @Override
    public String createPayOrder(String jsonParam) {
        return "error";
    }

    @Override
    public String alipayWapPayment(String jsonParam) {
        return "error";
    }

    @Override
    public String tradeQuery(String outTradeNo, String tradeNo) {
        return "error";
    }

    @Override
    public String queryPayOrder(String mchOrderNo) {
        return "error";
    }


}
