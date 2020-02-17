package com.hzit.shop.controller;

import com.hzit.shop.model.GoodsOrder;
import com.hzit.shop.service.IGoodsOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟订单
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    private Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private IGoodsOrderService iGoodsOrderService;


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
    public String recPay(HttpServletRequest request,ModelMap modelMap){
        logger.debug("接受到扫码请求");
        String view = "qrPay";

        // 1.区分，支付宝，还是微信

        //1.1 获取 ua
        String ua = request.getHeader("User-Agent");
        String amount = request.getParameter("amount");


        logger.info("ua = {}",ua);

        //1.2 判断ua为空
        if(StringUtils.isEmpty(ua)|| StringUtils.isEmpty(amount)) {
            String errorMessage = "User-Agent为空！";
            logger.info("{}信息：{}", logger, errorMessage);
            modelMap.put("result", "failed");
            modelMap.put("resMsg", errorMessage);
            return view;
        }


        String client = null; //支付渠道， 支付宝，微信
        String channelId = null;  //支付宝扫码支付，支付wap支付。

        // 1.3 判断支付方式，这里默认用支付宝 wap支付
        if (ua.contains("AlipayClient")) {
            logger.debug("支付宝支付..........");
            client = "alipay";       //支付方式
            channelId = "ALIPAY_WAP"; //支付宝wap支付

        }else if (ua.contains("MicroMessenger")) {
            logger.debug("微信支付..........");

        }else {
            //TODO
            logger.debug("未知..........");

        }

        //2.生成订单流水
        //3.模拟生成-订单
        String goodsId = "G_0001";
        GoodsOrder goodsOrder = iGoodsOrderService.addGoodsOrder(goodsId,Long.parseLong(amount));
        //3.调用支付接口


        //4.更新订单状态
        return null;
    }



}
