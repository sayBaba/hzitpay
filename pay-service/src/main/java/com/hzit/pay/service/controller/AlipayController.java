package com.hzit.pay.service.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzit.pay.service.model.PayOrder;
import com.hzit.pay.service.service.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付宝支付
 */
@RestController
@RequestMapping("/alipay")
public class AlipayController {

    private Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private AlipayUtil alipayUtil;


    @RequestMapping("/wapPay")
    public String alipayWapPay(@RequestParam String jsonParam){
        logger.info("接受到支付宝手机网站支付请求，参数：{}....",jsonParam);
        JSONObject paramObj = JSON.parseObject(jsonParam);
        PayOrder payOrder = paramObj.getObject("payOrder", PayOrder.class);

        String payUrl = alipayUtil.alipayWapPayement(payOrder);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retCode","SUCCESS");
        jsonObject.put("retMsg","处理成功");
        JSONObject data = new JSONObject();
        data.put("payUrl",payUrl);
        jsonObject.put("data",data);
        logger.info("处理完支付宝手机网站支付请求，返回参数：{}....",jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 支付查询
     * @param outTradeNo
     * @param tradeNo
     * @return
     */
    @RequestMapping("/wapPayQuery")
    public String alipayWapPay(@RequestParam String outTradeNo,@RequestParam String tradeNo){
        // 1.参数判断，
        // 2.查询支付流水表中状态
        //3.调用支付查询接口
        //4.解析支付宝回来的参数，更新交易流水表
        //5.封装返回对象。

        return alipayUtil.alipayTradeQuery(outTradeNo,tradeNo);
    }
}
