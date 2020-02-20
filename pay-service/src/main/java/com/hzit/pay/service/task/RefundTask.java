package com.hzit.pay.service.task;

import com.hzit.pay.service.service.AlipayUtil;
import com.hzit.pay.service.service.IRefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 退款定时任务
 */
@Component
@EnableScheduling
public class RefundTask {

    private Logger logger = LoggerFactory.getLogger(RefundTask.class);

    @Autowired
    private IRefundService iRefundService;

    @Autowired
    private AlipayUtil alipayUtil;


    @Scheduled(cron = "0 */5 * * * ?")
    public void query(){
        logger.info("*******定时任务-支付宝退款开始执行*****");
        //1.查询退款中的数据
//        iRefundService.

        String str =  alipayUtil.alipayTradeRefund(null,null,null,null);

        //异步通知 订单系统


    }
}
