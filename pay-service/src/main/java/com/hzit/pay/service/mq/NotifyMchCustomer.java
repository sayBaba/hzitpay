package com.hzit.pay.service.mq;

import com.alibaba.fastjson.JSONObject;
import com.hzit.common.utils.MyBase64;
import com.hzit.common.utils.PayDigestUtil;
import com.hzit.common.utils.XXPayUtil;
import com.hzit.pay.service.config.MQConfig;
import com.hzit.pay.service.mapper.MchNotifyMapper;
import com.hzit.pay.service.model.MchNotify;
import com.hzit.pay.service.model.PayOrder;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Date;

/**
 * mq支付异步通知消费者
 */
@Component
public class NotifyMchCustomer {

    private static final Logger logger = LoggerFactory.getLogger(NotifyMchCustomer.class);

    String mechNotifyUrl = "http://uwyyqb.natappfree.cc/notify/payRlt?params=";


    @Autowired
    private RabbitTemplate rabbitTemplate;


   @Autowired
    private MchNotifyMapper mchNotifyMapper;

    @RabbitListener(queues= MQConfig.NOTIFY_MCH_QUEUE)
    public void receive(Message message, NotifyMchMessage msg, Channel channel){
        logger.info("消息队列id:{}消费者开始....",msg.getPayId());

        PayOrder payOrder = msg.getPayOrder();
        if(ObjectUtils.isEmpty(payOrder)){
            logger.info("消息队列:{}队列中payOrder为空....",msg.getPayId());
            return;
        }

        //判断是否内部系统，在发送http通知。
//        MchInfo mchInfo = iMchInfoService.selectMchInfo(payOrder.getMchId());
//        if(ObjectUtils.isEmpty(mchInfo)){
//            logger.info("消息队列id:{}，没有查找到对应的商户:{}信息",msg.getPayId(),payOrder.getMchId());
//            return;
//        }

        //封装返回的参数
        JSONObject paramMap = new JSONObject();
        paramMap.put("goodsOrderId",payOrder.getMchOrderNo());
        paramMap.put("status",payOrder.getStatus());
        paramMap.put("amount",payOrder.getAmount());
        paramMap.put("mchId",payOrder.getMchId());
        paramMap.put("channelId",payOrder.getChannelId());

        String respSign = PayDigestUtil.getSign(paramMap, "qwe123");
        logger.info("订单号：{},异步通知商户生成的加密密文：{}",payOrder.getPayOrderId(),respSign);
        paramMap.put("sign", respSign);// 签名

        String respData = "params=" + paramMap.toJSONString();
        logger.info("订单号：{},respData：{}",payOrder.getPayOrderId(),respData);


        //发送通知
        respData = MyBase64.encode(respData.getBytes());

        logger.info("respData：{}",payOrder.getPayOrderId(),respData);

        String result = XXPayUtil.call4Post(mechNotifyUrl + respData);
        logger.info("订单号：{},异步通知返回的结果：{}",payOrder.getPayOrderId(),result);

        // 通知5次--5次没有收到结果,那就通知失败：3 否则就是通知中：1, 成功就是：2
        MchNotify mchNotify = mchNotifyMapper.selectByPrimaryKey(payOrder.getPayOrderId());

        if("success".equals(result)){
            //更新status 为 2,
            Date date = new Date();

            if(!ObjectUtils.isEmpty(mchNotify)){
                mchNotify = new MchNotify();
                mchNotify.setLastNotifyTime(date);
                mchNotify.setUpdateTime(date);
                String dbRlt = result+":"+respData;
                mchNotify.setResult(dbRlt);
                Integer count = mchNotify.getNotifyCount().intValue()+1;
                mchNotify.setNotifyCount(count.byteValue());
                Byte status = 2;
                mchNotify.setStatus(status);
                mchNotifyMapper.updateByPrimaryKeySelective(mchNotify);
            }else{
                mchNotify = new MchNotify();
                mchNotify.setLastNotifyTime(date);
                mchNotify.setUpdateTime(date);
                String dbRlt = result+":"+respData;
                mchNotify.setResult(dbRlt);
                Integer count = mchNotify.getNotifyCount().intValue()+1;
                mchNotify.setNotifyCount(count.byteValue());
                Byte status = 2;
                mchNotify.setStatus(status);
                mchNotifyMapper.insertSelective(mchNotify);
            }




            //手动确认
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException e) {
                logger.error("IOException",e);
            }
            return;
        }else{
            //没有收到结果，继续通知。每隔15分钟发一次通知
            //解决重复发送。每隔15分钟发一次。
            int count = mchNotify.getNotifyCount().intValue();

            if(ObjectUtils.isEmpty(mchNotify)){
                count = 0;
            }else{
                 count = mchNotify.getNotifyCount().intValue();
            }

            if(count<5){
                // 通过广播模式发布延时消息 延时30分钟 持久化消息 消费后销毁 这里无需指定路由，会广播至每个绑定此交换机的队列
                rabbitTemplate.convertAndSend(MQConfig.Delay_Exchange_Name, "", payOrder, message1 -> {
                    message1.getMessageProperties().setExpiration("60000");
                    return message1;
                });
                //TODO ,更新 mchNotify表


            }else {
                Date date = new Date();
                mchNotify.setLastNotifyTime(date);
                mchNotify.setUpdateTime(date);
                Byte status = 3;
                mchNotify.setStatus(status);
                String dbRlt = result+"已通知5次:"+respData;
                mchNotify.setResult(dbRlt);
                mchNotifyMapper.updateByPrimaryKeySelective(mchNotify);
            }
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException e) {
                logger.error("IOException",e);
            }


        }














    }

    public static void main(String[] args) {
        String mechNotifyUrl = "http://uwyyqb.natappfree.cc/notify/payRlt?params=";

        String result = XXPayUtil.call4Post(mechNotifyUrl + "1111");
        System.out.println(result);

    }

}
