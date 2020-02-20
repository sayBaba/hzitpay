package com.hzitpay.web.client;

import com.hzit.common.req.PayChannelReq;
import com.hzitpay.web.client.impl.PayChannelClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "PAY-SERVICE",fallback = PayChannelClientImpl.class)
public interface PayChannelClient {


    /**
     * 查询商户支付渠道
     * @param payChannelReq
     * @return
     */
    @RequestMapping(value ="/pay_channel/select",method = RequestMethod.POST)
    public String selectPayChannel(@RequestBody PayChannelReq payChannelReq);


    /**
     * 创建交易流水
     * @param jsonParam
     * @return
     */
    @RequestMapping(value ="/payOrder/create",method = RequestMethod.POST)
    public String createPayOrder(@RequestParam String jsonParam);


    /**
     * 调用支付宝wap支付
     * @param jsonParam
     * @return
     */
    @RequestMapping(value ="/alipay/wapPay",method = RequestMethod.POST)
    public String alipayWapPayment(@RequestParam String jsonParam);


    /**
     * 查询支付宝交易
     * @return
     */
    @RequestMapping(value ="/alipay/wapPayQuery",method = RequestMethod.POST)
    public String tradeQuery(@RequestParam String outTradeNo,@RequestParam String tradeNo);



}
