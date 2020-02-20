package com.hzitpay.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzitpay.web.client.PayChannelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付查询接口
 */
@RestController
@RequestMapping("/query")
public class PayQueryController {

    private Logger logger = LoggerFactory.getLogger(PayQueryController.class);

    @Autowired
    private PayChannelClient payChannelClient;

    /**
     * @param params
     */
    @RequestMapping("/pay")
    public String pay(@RequestParam String params){
        logger.info("接受到支付查询请求，参数为：{}",params);


        //1.参数校验 空判断。
        //字符解析为json
     // 判断请求的sign 和 支付系统运算出来的sign是否相等


        JSONObject po = JSONObject.parseObject(params);
        String tradeNo =  po.getString("tradeNo");
        String outTradeNo = po.getString("outTradeNo");

        return payChannelClient.tradeQuery(outTradeNo,tradeNo);

    }



}
