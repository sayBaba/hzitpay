package com.hzit.pay.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzit.common.req.PayChannelReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 支付渠道接口
 */
@RestController
@RequestMapping("/pay_channel")
public class PayChannelServiceController {

    private Logger logger = LoggerFactory.getLogger(PayChannelServiceController.class);

    @RequestMapping("/select")
    public String selectPayChannel(@RequestBody PayChannelReq payChannelReq) {
        logger.info("查询商户支付渠道请求参数为：{},start", payChannelReq);
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isEmpty(payChannelReq.getMchId()) ||StringUtils.isEmpty(payChannelReq.getChannelId())){
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg","mchId为空");
            return jsonObject.toJSONString();
        }

        if("10000".equals(payChannelReq.getMchId()) && "ALIPAY_WAP".equals(payChannelReq.getChannelId())){
            jsonObject.put("retCode","SUCCESS");
            jsonObject.put("retMsg","查询成功");
            JSONObject data = new JSONObject();

            data.put("mchId","10000");
            jsonObject.put("data",data);
            return jsonObject.toJSONString();
        }else{
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg","商户id不存在");
            return jsonObject.toJSONString();
        }

    }


}
