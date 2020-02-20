package com.hzit.shop.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单退款请求
 */

@RestController
@RequestMapping("/order")
public class RefundController {

    /**
     * @return
     */
    @RequestMapping("/toRefund")
    public String toRefund(HttpServletRequest request, ModelMap modelMap){

        //


        return null;
    }
}


