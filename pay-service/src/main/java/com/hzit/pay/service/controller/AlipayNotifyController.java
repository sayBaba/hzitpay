package com.hzit.pay.service.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hzit.pay.service.config.MQConfig;
import com.hzit.pay.service.model.PayOrder;
import com.hzit.pay.service.mq.NotifyMchMessage;
import com.hzit.pay.service.service.AlipayUtil;
import com.hzit.pay.service.service.IPayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 接收支付宝异步通知
 */
@RestController
@RequestMapping("/notify")
public class AlipayNotifyController {

    private Logger logger = LoggerFactory.getLogger(AlipayNotifyController.class);

    @Autowired
    private IPayOrderService iPayOrderService;

    @Autowired
    private AmqpTemplate amqpTemplate;


    @RequestMapping("/payResult.htm")
    public void payResult(HttpServletRequest request, HttpServletResponse response){

        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            logger.error("IOException",e);
        }
        //从request中获取支付宝，回调的参数
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }



        logger.info("接受到支付宝的异步回调参数参数:params={}", params);

        //1.参数为空响应支付宝fail
        if(params.isEmpty()) {
            logger.error("{}请求参数为空");
            printWriter.println("fail");
            return;
        }

        //1.签名认证
        String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgLWKv7BzICfdlJBw2HfRlY/CNYGe728gTKyNXLrUfCOIQ5htx08JJwA+dz2lhp5fKbZ7nP26F/a5ZbagNWLpTSkWeOVF1lSa5Z0UVw4kLihA+dMnDYAnPjvQQP6pespxpB3cGPTWmDZXTIXLXn74pTUaaXdkwLgwrLeJLG0unLXC8Rdl7EfRIEvabsjyR07qC0qlBI5pwHhNZeB+kpwliMu4EXIJBRwodOWIOK/jd4qwX5Gr+TY+zPJXnG5u5+AUbFsN10oq2J3d/27B1KAmfMCAHHxa0kGh+PAvhkvpQghF1+v5k02RbN6REMP+Uu+53bMJCOsPMR4lla5gtOlVhwIDAQAB";

        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, pubKey, "gbk", "RSA2");
            logger.info("支付宝签名验证结果：{}",signVerified);

            if(signVerified  == false){ //TODO
                //响应支付宝succss，支付宝就不会通知了
                String out_trade_no = params.get("out_trade_no");
                String total_amount = params.get("total_amount");
                String trade_no = params.get("trade_no");

                //1.判断交易订单号，和金额是否存在
                if (StringUtils.isEmpty(out_trade_no)) {
                    logger.error("AliPay Notify parameter out_trade_no is empty. out_trade_no={}", out_trade_no);
                    printWriter.print("fail");

                    return ;
                }

                if (StringUtils.isEmpty(total_amount)) {
                    logger.error("AliPay Notify parameter total_amount is empty. total_fee={}", total_amount);
                    printWriter.print("fail");

                    return ;
                }

                //2.判断交易订单号是否存在

                //3.判断商户和支付方式是否存在


                //5..判断数据库的金额和支付宝的金额是否一致
                long aliPayAmt = new BigDecimal(total_amount).movePointRight(2).longValue();
                long dbPayAmt = aliPayAmt; //payOrder.getAmount().longValue();
                if (dbPayAmt != aliPayAmt) {
                    printWriter.print("fail");
                    return ;
                }

                printWriter.print("success");

                //2.更新支付流水
                String trade_status = params.get("trade_status");// 交易状态
                if (trade_status.equals("TRADE_SUCCESS") ||
                        trade_status.equals("TRADE_FINISHED")) {
                    //根据out_trade_no 更新流水状态为成功
                    PayOrder payOrder = new PayOrder();
                    payOrder.setPayOrderId(out_trade_no);
                    payOrder.setChannelOrderNo(trade_no);
                    payOrder.setUpdateTime(new Date());
                    Byte status = 2; //支付成功
                    payOrder.setStatus(status);
                    iPayOrderService.updatePayOrder(payOrder);

                    // mq生产者，放个消息到mq队列。
                    NotifyMchMessage mchMessage = new NotifyMchMessage();
                    mchMessage.setPayId(payOrder.getPayOrderId());
                    mchMessage.setPayOrder(payOrder);
                    amqpTemplate.convertAndSend(MQConfig.NOTIFY_MCH_QUEUE,mchMessage);



                }



















            }else {
                //TODO 失败
                //响应支付宝fail，支付宝会继续通知，最多通知9次。如果还没有success，支付宝也不会通知
                printWriter.print("fail");


            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }





        //3.异步通知shop业务系统更新订单状态。 mq异步通知，5次


    }

}
