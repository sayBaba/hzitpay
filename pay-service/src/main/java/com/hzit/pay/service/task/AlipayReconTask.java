package com.hzit.pay.service.task;

import com.hzit.pay.service.service.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 支付宝对账任务
 */
@Component
@EnableScheduling
public class AlipayReconTask {

    private Logger logger = LoggerFactory.getLogger(AlipayReconTask.class);

    @Autowired
    private AlipayUtil alipayUtil;


    @Scheduled(cron = "0 */5 * * * ?") //凌晨，等银行或者支付公司生成对账文件后执行
    public void recon(){
        logger.info("*******定时任务-支付宝对账任务开始执行*****");

        //1.1文件获取，主动去支付宝下载对账文件
       String url = alipayUtil.alipayRecon("2020-02-20");

       //1.2生成对账文件
        try {
            alipayUtil.createReconFile(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //2.解析对账文件，支付宝 csv格式
        //2.1使用opencsv 解析支付宝的对账文件。

        //2.2 支付宝对账文件入库，放入对账临时表 t_recon_temp

        //3.数据对比

        //的交易笔数是相等，交易总金额是否相等。


        // 3.1以我方流水号为基准对比两个表的 交易数量，总金额。
        // 3.2以对方流水号为基准对比两个表的 交易数量，总金额。

        //使用redis来对比。


        //3.3将对出的差役数据，存入对账差异表


        //4.处理差役数据

        //5.清掉临时表数据


        //6.生成对账文件。




    }
}
