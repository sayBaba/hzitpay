package com.hzit.pay.service.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzit.pay.service.model.PayOrder;
import com.hzit.pay.service.service.IPayOrderService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付流水相关接口
 */
@RestController
@RequestMapping("/payOrder")
public class PayOrderController {

    private Logger logger = LoggerFactory.getLogger(PayOrderController.class);

    @Autowired
    private IPayOrderService iPayOrderService;


    @RequestMapping(value = "/create")
    public String createPayOrder(@RequestParam String jsonParam) {
        logger.info("接收创建支付订单请求,jsonParam={}", jsonParam);

        JSONObject retObj = new JSONObject();
        retObj.put("code", "0000");
        if(StringUtils.isBlank(jsonParam)) {
            retObj.put("code", "0001");
            retObj.put("msg", "缺少参数");
            return retObj.toJSONString();
        }

        try {
            PayOrder payOrder = JSON.parseObject(jsonParam, PayOrder.class);
            int result = iPayOrderService.createPayOrder(payOrder);
            retObj.put("result", result);
        }catch (Exception e) {
            logger.error("Exception",e);
            retObj.put("code", "9999");
            retObj.put("msg", "系统错误");
            return retObj.toJSONString();
        }
        logger.info("支付订单返回参数,jsonParam={}", jsonParam);

        return retObj.toJSONString();
    }



}
