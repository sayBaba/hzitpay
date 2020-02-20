package com.hzit.shop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotifyController {

    private Logger logger = LoggerFactory.getLogger(NotifyController.class);

    /**
     * 支付回调 -支付成功，接受支付的结果。
     * @return
     */
    @RequestMapping("/payRlt")
    public String reciveNotice(@RequestParam String params){
        logger.info("###### 接受到支付系统回调请求 ######，请求参数：{}", params);
        // 解析 json

        //字段判断。

        //更新订单。

        return "success";
    }
}
