package com.hzit.shop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模拟订单
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    private Logger logger = LoggerFactory.getLogger(ShopController.class);


    /**
     * 加载二维码
     * @return
     */
    @RequestMapping("/showCode")
    public String showCode(){
        logger.debug("加载二维码。。。。。。。。。。");
        return "openQrPay";
    }


    /**
     * 扫码入口
     * @return
     */
    @RequestMapping("/recPay")
    public String recPay(){
        logger.debug("接受到扫码请求");

        return null;
    }



}
