package com.hzit.shop.task;

import com.alibaba.fastjson.JSONObject;
import com.hzit.common.utils.XXPayUtil;
import com.hzit.shop.controller.NotifyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务-查询支付结果
 */
@Component
@EnableScheduling
public class TradeQueryTask {

    private Logger logger = LoggerFactory.getLogger(TradeQueryTask.class);

    @Scheduled(cron = "0 */20 * * * ?")
    public void query(){
        logger.info("*******定时任务-支付查询开始执行*****");

        // 查询需要确认交易状态的数据。

        JSONObject reqData = new JSONObject();

        reqData.put("outTradeNo","092aa9c6ae2a4d3796dccdd3a7c98b43");
        reqData.put("tradeNo","");

        String baseUrl = "http://127.0.0.1:1111/pay-service"; //zuul网关地址

        String result = XXPayUtil.call4Post(baseUrl+"/query/pay?params=" + reqData.toJSONString()); //TODO
        logger.info("返回的result:{}" ,result);

        JSONObject object = JSONObject.parseObject(result);

        JSONObject data =  JSONObject.parseObject(object.getString("data"));

        //返回的数据要做签名认证 TODO

        //更新订单表

    }

}
