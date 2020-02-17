package com.hzit.shop.controller;

import org.springframework.util.DigestUtils;

/**
 *
 */
public class Test {

    public static void main(String[] args) {
        String key = "123";

        // 订单系统
        String  orderId = "123";
        String  channelId = "alipay";
        String  amount = "136";

        String xx = "orderId=120000&channelId=alipay&amount=136";

        String base = xx +"/"+key;
        String sign = DigestUtils.md5DigestAsHex(base.getBytes());

        System.out.println(sign);

        String xx1 = "orderId=120000&channelId=alipay&amount=1.36";


        String reqParams = "orderId=120000&channelId=alipay&amount=136&sign=038bea9441d3983c8d133ad3c6eb3196";


//        MD5





    }

}
