package com.hzit.pay.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzit.common.req.RefundOrderReq;
import com.hzit.pay.service.service.IRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退款相关接口
 */
@RestController
@RequestMapping("/refund")
public class RefundController {


    @Autowired
    private IRefundService iRefundService;

    /**
     * 创建退款流水
     * @param refundOrderReq
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createRefundOrder(@RequestBody RefundOrderReq refundOrderReq){
        iRefundService.createRefundOrder(refundOrderReq);
        JSONObject retObj = new JSONObject();
        retObj.put("retCode", "SUCCESS");
        retObj.put("retMsg", "创建成功");
        return retObj.toJSONString();
    }



}
