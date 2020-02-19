package com.hzit.pay.service.controller;

import com.hzit.pay.service.service.AlipayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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


    @RequestMapping("/payResult.htm")
    public String payResult(HttpServletRequest request, HttpServletResponse response){

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




        return "";
    }

}
