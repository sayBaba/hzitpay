package com.hzit.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzit.common.utils.AmountUtil;
import com.hzit.common.utils.PayDigestUtil;
import com.hzit.common.utils.XXPayUtil;
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
import java.util.HashMap;
import java.util.Map;

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

        //2.模拟生成-订单
        String goodsId = "G_0001";
        GoodsOrder goodsOrder = iGoodsOrderService.addGoodsOrder(goodsId,Long.parseLong(amount));
        //3.调用支付接口
        String  reqKey = "qwe123";
        String  mchid = "10000";


        //3.1封装支付接口参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mchId",mchid);
        jsonObject.put("mchOrderNo", goodsOrder.getGoodsOrderId());  // 商户订单号
        jsonObject.put("channelId", channelId);             // 支付渠道ID, WX_NATIVE,ALIPAY_WAP
        jsonObject.put("amount", goodsOrder.getAmount());   // 支付金额,单位分
        jsonObject.put("currency", "cny");                    // 币种, cny-人民币
        jsonObject.put("notifyUrl", "http://127.0.0.1:8089/notify/pay");     // 回调URL

        jsonObject.put("clientIp", "");        // 用户地址,IP或手机号
        jsonObject.put("device", "");                      // 设备
        jsonObject.put("subject", goodsOrder.getGoodsName());
        jsonObject.put("body", goodsOrder.getGoodsName());
        jsonObject.put("param1", "");                         // 扩展参数1
        jsonObject.put("param2", "");                         // 扩展参数2

        String reqSign = PayDigestUtil.getSign(jsonObject, reqKey);

        logger.debug("请求sign：{}",reqSign);
        jsonObject.put("sign", reqSign);                         // 签名串

        String reqData = "params=" + jsonObject.toJSONString();
        logger.info("请求支付中心下单接口,请求数据:{}" ,reqData);

        //3.2 发送请求到支付系统
        //TODO 1.同步接口：快捷支付，签约，2.异步需要跳转页面。

         String baseUrl = "http://127.0.0.1:1111/pay-service"; //zuul网关地址

        String result = XXPayUtil.call4Post(baseUrl+"/pay/create_order?" + reqData); //TODO
        JSONObject object = JSONObject.parseObject(result);

        JSONObject data =  JSONObject.parseObject(object.getString("data"));



        //返回的数据要做签名认证 TODO
        logger.info("返回的result:{}" ,result);
//        modelMap.addAttribute("resCode","SUCCESS");
//
//        modelMap.addAttribute("resMsg",object.getString("retMsg"));

        Map params = new HashMap<>();
        params.put("resCode", "SUCCESS");



        modelMap.addAttribute("client",client);
        modelMap.put("result", "success");

        modelMap.addAttribute("orderMap",params);
        modelMap.put("goodsOrder", goodsOrder);
        modelMap.put("amount", AmountUtil.convertCent2Dollar(goodsOrder.getAmount()+""));
        modelMap.addAttribute("payUrl",data.getString("payUrl"));















        //4.更新订单状态
        return view;
    }



}
