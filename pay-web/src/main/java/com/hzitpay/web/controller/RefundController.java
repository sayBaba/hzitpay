package com.hzitpay.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzit.common.req.RefundOrderReq;
import com.hzit.common.resp.BaseResp;
import com.hzit.common.resp.PayOrderData;
import com.hzit.common.resp.RefundOrderResp;
import com.hzitpay.web.client.MchInfoServiceClient;
import com.hzitpay.web.client.PayChannelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退款接口
 */
@RestController
@RequestMapping("/refund")
public class RefundController {

    private Logger logger = LoggerFactory.getLogger(RefundController.class);

    @Autowired
    private MchInfoServiceClient mchInfoServiceClient;

    @Autowired
    private PayChannelClient payChannelClient;

    /**
     * 统一退款接口
     * @param refundOrderReq
     */
    @RequestMapping("/create_refund")
    public RefundOrderResp pay(@RequestBody RefundOrderReq refundOrderReq){
        logger.info("接受到业务系统的退款请求，参数为：{}",refundOrderReq);

        //判断商户否存在
        String mchRlt = mchInfoServiceClient.selectMchInfo(refundOrderReq.getMchId());

        RefundOrderResp refundOrderResp = new RefundOrderResp();

        if (StringUtils.isEmpty(mchRlt)){
            logger.info("商户订单号：{}商户id不存在",refundOrderReq.getMchId());
            refundOrderResp.setRetCode("fail");
            refundOrderResp.setRetMsg("商户id不存在");
            return  refundOrderResp;
        }

        //根据商户订单号查询交易订单
        JSONObject jsonObject = new JSONObject();
//        String payQueryResp = payOrderServiceClient.queryPayOrder(jsonObject.toJSONString());.

        BaseResp baseResp = payChannelClient.queryPayOrder(refundOrderReq.getMchOrderNo());

            if(!"".equals(baseResp.getRetCode())){
                logger.info("商户订单号：{}商户id不存在",refundOrderReq.getMchId());
                refundOrderResp.setRetCode("fail");
                refundOrderResp.setRetMsg("没有找到交易订单");
                return refundOrderResp;
            }

            PayOrderData payOrder = (PayOrderData)baseResp.getData();

        long refundAmt = Long.parseLong(refundOrderReq.getAmount()); //退款金额
        if(refundAmt> payOrder.getAmount()){
            logger.info("商户号：{}退款失败：退款金额超过支付金额",refundOrderReq.getMchId());
            refundOrderResp.setRetCode("fail");
            refundOrderResp.setRetMsg("退款失败");
            return refundOrderResp;
        }





//        5.生成退款流水: 如何避免重复退款,接口幂等处理

        String str = payChannelClient.createRefundOrder(refundOrderReq);
        //解析json字符串。
        logger.info("创建退款流水返回结果：{}",str);
        refundOrderResp.setRetCode("SUCCESS");
        refundOrderResp.setRetMsg("接受到退款请求");
        return  refundOrderResp;


        //6. 调用支付宝的退款接口.

        //7.更新退款流水状态.


        //  RefundOrderId 退款流水，支付系统生成
        //  PayOrderId    支付流水，支付系统生成的  ： out_trade_no
        // ChannelPayOrderNo  支付宝返的支付流水 ：  trade_no
        // MchRefundNo     业务系统（订单）生成的
        //PayAmount  z支付金额









        return null;
    }


}
