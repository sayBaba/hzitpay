package com.hzit.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notify")
public class NotifyController {

    /**
     * 支付回调 -支付成功，接受支付的结果。
     * @return
     */
    @RequestMapping("/pay")
    public String reciveNotice(){
        return null;
    }
}
