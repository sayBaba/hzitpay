package com.hzitpay.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzit.common.constant.PayConstant;
import com.hzit.common.req.PayChannelReq;
import com.hzit.common.utils.PayDigestUtil;
import com.hzitpay.web.client.MchInfoServiceClient;
import com.hzitpay.web.client.PayChannelClient;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 统一支付接口
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    private Logger logger = LoggerFactory.getLogger(PayController.class);


    @Autowired
    private MchInfoServiceClient mchInfoServiceClient;

    @Autowired
    private PayChannelClient payChannelClient;



    /**
     * 统一支付接口
     * @param params
     */
    @RequestMapping("/create_order")
    public String pay(@RequestParam String params){
        logger.info("接受到支付请求，参数为：{}",params);

        //1.参数校验 空判断。
        if (StringUtils.isEmpty(params)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg","请求params参数为空");
            return jsonObject.toJSONString();
        }

        //字符解析为json
        JSONObject po = JSONObject.parseObject(params);
        String errorMessage = null;
        // 支付参数
        String mchId = po.getString("mchId"); 			    // 商户ID
        String mchOrderNo = po.getString("mchOrderNo"); 	// 商户订单号
        String channelId = po.getString("channelId"); 	    // 渠道ID
        String amount = po.getString("amount"); 		    // 支付金额（单位分）
        String currency = po.getString("currency");         // 币种
        String clientIp = po.getString("clientIp");	        // 客户端IP
        String device = po.getString("device"); 	        // 设备
        String extra = po.getString("extra");		        // 特定渠道发起时额外参数
        String param1 = po.getString("param1"); 		    // 扩展参数1
        String param2 = po.getString("param2"); 		    // 扩展参数2
        String notifyUrl = po.getString("notifyUrl"); 		// 支付结果回调URL
        String sign = po.getString("sign"); 				// 签名
        String subject = po.getString("subject");	        // 商品主题
        String body = po.getString("body");	                // 商品描述信息

        // 验证请求参数有效性（必选项）
        if(org.apache.commons.lang.StringUtils.isBlank(mchId)) {
            errorMessage = "request params[mchId] error.";
            return errorMessage;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(mchOrderNo)) {
            errorMessage = "request params[mchOrderNo] error.";
            return errorMessage;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(channelId)) {
            errorMessage = "request params[channelId] error.";
            return errorMessage;
        }
        if(!NumberUtils.isNumber(amount)) {
            errorMessage = "request params[amount] error.";
            return errorMessage;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(currency)) {
            errorMessage = "request params[currency] error.";
            return errorMessage;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(notifyUrl)) {
            errorMessage = "request params[notifyUrl] error.";
            return errorMessage;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(subject)) {
            errorMessage = "request params[subject] error.";
            return errorMessage;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(body)) {
            errorMessage = "request params[body] error.";
            return errorMessage;
        }

        //2.签名验证
        if (org.apache.commons.lang.StringUtils.isEmpty(sign)) {
            errorMessage = "request params[sign] error.";
            return errorMessage;
        }

        //2.1 根据mchId查询商户信息
       String rlt = mchInfoServiceClient.selectMchInfo(mchId);
        logger.info("根据mchId查询商户信息，返回结果为：{}",rlt);

        if (StringUtils.isEmpty(rlt)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg","没有该商户");
            return jsonObject.toJSONString();
        }
        JSONObject object = JSONObject.parseObject(rlt);

        String code = object.getString("retCode");

        if(!"SUCCESS".equals(code)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg",object.getString("retMsg"));
            return jsonObject.toJSONString();
        }
        String dataStr = object.getString("data");

        JSONObject dataJson = JSONObject.parseObject(dataStr);

        String reqKey = dataJson.getString("reqKey");

        //2.2 判断是否支持该支付方式
        PayChannelReq payChannelReq = new PayChannelReq();
        payChannelReq.setChannelId(channelId);
        payChannelReq.setMchId(mchId);

        String payChannelRlt = payChannelClient.selectPayChannel(payChannelReq);
        logger.info("查询商户支付渠道，返回结果为：{}",payChannelRlt);


        if(!"SUCCESS".equals(code)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg",object.getString("retMsg"));
            return jsonObject.toJSONString();
        }

        JSONObject payChannelObject = JSONObject.parseObject(payChannelRlt);

        // 判断请求的sign 和 支付系统运算出来的sign是否相等
        po.remove("sign");
        String paySign = PayDigestUtil.getSign(po, reqKey);
        logger.info("订单号：{}：支付系统，运算出来的sign：{}",mchOrderNo,paySign);
        if(!sign.equals(paySign)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg","请求非法");
            return jsonObject.toJSONString();
        }

        //3.生成支付流水
        // 验证参数通过,返回JSONObject对象
        JSONObject payOrder = new JSONObject();
        payOrder.put("payOrderId", UUID.randomUUID().toString().replaceAll("-",""));  // 生成唯一id， 1.jdk的 uuid，2.雪花算法。3.数据的库的sequence
        payOrder.put("mchId", mchId);
        payOrder.put("mchOrderNo", mchOrderNo);
        payOrder.put("channelId", channelId);
        payOrder.put("amount", Long.parseLong(amount));
        payOrder.put("currency", currency);
        payOrder.put("clientIp", clientIp);
        payOrder.put("device", device);
        payOrder.put("subject", subject);
        payOrder.put("body", body);
        payOrder.put("extra", extra);
        String channelMchId  = payChannelObject.getString("channelMchId") ==null?"":payChannelObject.getString("channelMchId");
        payOrder.put("channelMchId",channelMchId);
        payOrder.put("param1", param1);
        payOrder.put("param2", param2);
        payOrder.put("notifyUrl", notifyUrl);

        //调用pay-service服务 生成流水
        String payCreateRlt = payChannelClient.createPayOrder(payOrder.toJSONString());
        //TODO


        //6.调用对应的支付接口。

        switch (channelId) {
            case PayConstant.PAY_CHANNEL_ALIPAY_WAP :
                return payChannelClient.alipayWapPayment(getJsonParam("payOrder", payOrder));
            default:
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("retCode","FAIL");
                String msg = "不支持的支付渠道类型"+"[channelId="+channelId+"]" ;
                jsonObject.put("retMsg",msg);
                return msg;
//              return XXPayUtil.makeRetFail(XXPayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "不支持的支付渠道类型[channelId="+channelId+"]", null, null));
        }


    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retCode","FAIL");
        jsonObject.put("sign","sign");

        System.out.println();

    }

    /**
     * 转成json字符串
     * @param name
     * @param value
     * @return
     */
    private  String getJsonParam(String name, Object value) {
        JSONObject jsonParam = new JSONObject();
        jsonParam.put(name, value);
        return jsonParam.toJSONString();
    }

}
