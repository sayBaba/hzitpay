package com.hzitpay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一支付接口
 */
@Controller
@RequestMapping("/api/pay")
public class PayController {

    @RequestMapping("create_order")
    public void pay(HttpServletRequest request){



    }
}
