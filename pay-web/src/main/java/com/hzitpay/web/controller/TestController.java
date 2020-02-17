package com.hzitpay.web.controller;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public class TestController {

    /**     * 支付系统
     * @param request
     */
    @RequestMapping("/test")
    public void test(HttpServletRequest request){
        String key = "123";


        String  orderId = request.getParameter("orderId"); //订单系统的订单号
        String  channelId = request.getParameter("channelId");  //支付方式
        String  amount = request.getParameter("amount");        //支付金额

        //订单系统请求的sign
        String  sign = request.getParameter("sign");



        String xx = "orderId=120000&channelId=alipay&amount=1.36";

        String base = xx +"/"+key;
        //支付系统，算出来的
        String sign1 = DigestUtils.md5DigestAsHex(base.getBytes());

        if(sign.equals(sign1)){
            System.out.println("正常");

        }else{
            System.out.println("请求非法");

        }


    }

}
