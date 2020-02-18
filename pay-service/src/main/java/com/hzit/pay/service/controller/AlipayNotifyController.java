package com.hzit.pay.service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 接收支付宝异步通知
 */
@RestController
@RequestMapping("/notify")
public class AlipayNotifyController {

    @RequestMapping("/payResult.htm")
    public String payResult(HttpServletRequest request){


        return "";
    }

}
