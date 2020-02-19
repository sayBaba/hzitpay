package com.hzit.pay.service.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.hzit.common.utils.AmountUtil;
import com.hzit.pay.service.model.PayOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 支付宝支付接口
 */
@Service
public class AlipayUtil {

    private Logger logger = LoggerFactory.getLogger(AlipayUtil.class);


    /**
     * 支付宝wap支付
     */
    public String alipayWapPayement(PayOrder payOrder) {

//        AlipayClient client = new DefaultAlipayClient(alipayConfig.getAlipayUrl(), alipayConfig.getAppId(),
//                alipayConfig.getPrivateKey(), "json", "utf-8", alipayConfig.getPublicKey(), "RSA2");

        String apiUrl = "https://openapi.alipaydev.com/gateway.do";
        String appid = "2016101200667654";

        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCAtYq/sHMgJ92UkHDYd9GVj8I1gZ7vbyBMrI1cutR8I4hDmG3HTwknAD53PaWGnl8ptnuc/boX9rlltqA1YulNKRZ45UXWVJrlnRRXDiQuKED50ycNgCc+O9BA/ql6ynGkHdwY9NaYNldMhctefvilNRppd2TAuDCst4ksbS6ctcLxF2XsR9EgS9puyPJHTuoLSqUEjmnAeE1l4H6SnCWIy7gRcgkFHCh05Yg4r+N3irBfkav5Nj7M8lecbm7n4BRsWw3XSirYnd3/bsHUoCZ8wIAcfFrSQaH48C+GS+lCCEXX6/mTTZFs3pEQw/5S77ndswkI6w8xHiWVrmC06VWHAgMBAAECggEALshN12D+Z4i1/IMH0Vaz4kXvlF059NOSjOwHMdZi14BezhsAg/pRXFC1Y8gPez/jXRkME0MSOhWey762utDp9fbiSS0k1kPITX9r3jywn+pXu7apMjoLiY5aItWtqokdB0cnMhcETS0rTBAxnHZQUFRd9knEYFxSrOTFQlWpJKJUdOkthLxzNfqtAclj89aAfuSlcxQGfDyBQivkvGX1EmxguaCQAA5TX80dH5K9tJalAE2cOPNJZrv6+gvqZYzVuuW1QAaP0tUf8cf0tPE6Mx5Wj/gcLeT1YiBFzKrEcoOKTy5Xt4urlkCkXyrmuOUGlhz5ppRNFpolu4fsM2pNkQKBgQC15F/1F8j9QVX6MQ5mQMKZofK08jGXdmTXRwgOuoO8OgOZT9INyPvfUKDNrh8Wz0Sedl4blsaq87zgjg/cm/kCwtzODMJD2CmyEv3/lhehB/1awuj7DrzRAsLPhHBTFqOZIXxqdoZLWxZHAJ30dsFnioGm+wW1BxjNhj0SuzlJmQKBgQC1Jhq/W6XUEg1TXelBZa9QGzktPB6foEP/3a+UFwrzh3zkC+HiiDMIxxEaDTdXBOLT1n7RflRBXcyMBdvtqTQ6Z/nQhXwM28wEux/XOSDbIEPXrejUySuPmTtQgA/0YkRDn1r4HuxyDplO6XUEoOo5fNHuq6NkTKfEkEz49CBMHwKBgHwp3jsPtnry278DN8IjWPPI8kYEwkeTYTj3Ww+60Gxv6b8BFvGkDhOeFXbx8nT+nefSA2Gv5gQSiA99ymQc8LmqzVG2sfHgIG5Xup17FrwtGQyY2rNulwPSb6t6y7ZcUOcaTbTbHcdY8Xcce7bCTjng+apD1tyBcLPze8NKnMuZAoGBAIhyxFEBop5kXp8+LNXy1yQa7W2STGrmmHHhtUByPvCDTOKlxKcS+oYRFGOwMtAcQRNaDTqV0rdOBSYvv6J8AnhAG58i/PbelQW3QdunD4We/xs1xxyIz4Qh4tjXLa1iW5MoNKiS+n1LW9Vh6p/q6aMVzzfdF30UdIaOPzyC/tT3AoGBAJ0eKElalg7+MrlS7RbuOEUi5SKai2UtlY1ZKpt8CwFxQ90Gvev+KCApHgYEl1Yrv/E/GVKsZ+2keLs2O+koVWUyokJBYfMRlJZjbKQBlTEedcn448aFtgbQEDjCE9LA5IMgdb3DzbYkxT2B9THqHoaC8qw6TRDP2lZLJ1zHnsru";
        String pubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgLWKv7BzICfdlJBw2HfRlY/CNYGe728gTKyNXLrUfCOIQ5htx08JJwA+dz2lhp5fKbZ7nP26F/a5ZbagNWLpTSkWeOVF1lSa5Z0UVw4kLihA+dMnDYAnPjvQQP6pespxpB3cGPTWmDZXTIXLXn74pTUaaXdkwLgwrLeJLG0unLXC8Rdl7EfRIEvabsjyR07qC0qlBI5pwHhNZeB+kpwliMu4EXIJBRwodOWIOK/jd4qwX5Gr+TY+zPJXnG5u5+AUbFsN10oq2J3d/27B1KAmfMCAHHxa0kGh+PAvhkvpQghF1+v5k02RbN6REMP+Uu+53bMJCOsPMR4lla5gtOlVhwIDAQAB";
        AlipayClient client = new DefaultAlipayClient(apiUrl, appid, privateKey,"json", "utf-8", pubKey, "RSA2");

        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl("http://hztest.natapp1.cc/notify/payResult.htm"); //后端异步通知，更新交易订单。
        request.setReturnUrl("https://www.baidu.com"); //支付完成，跳转此页面

        JSONObject bizContent = new JSONObject();
        bizContent.put("body", payOrder.getBody()); //可空
        bizContent.put("timeout_express", "");
        bizContent.put("time_expire", "");
        bizContent.put("auth_token", "");
        bizContent.put("goods_type", "");
        bizContent.put("passback_params", "");
        bizContent.put("promo_params", "");
        bizContent.put("extend_params", "");
        bizContent.put("merchant_order_no", "");
        bizContent.put("enable_pay_channels", "");
        bizContent.put("disable_pay_channels", "");
        bizContent.put("store_id", "");
        bizContent.put("specified_channel", "");
        bizContent.put("business_params", "");
        bizContent.put("ext_user_info", "");
        /***************必传******************/
        bizContent.put("subject", payOrder.getSubject());
        bizContent.put("out_trade_no", payOrder.getPayOrderId()); //流水
        bizContent.put("total_amount", AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));//金额
        bizContent.put("quit_url", "www.baidu.com");
        bizContent.put("product_code", "QUICK_WAP_WAY"); //手机网页wap支付
        request.setBizContent(bizContent.toJSONString());
        try {
            AlipayResponse alipayResponse = client.pageExecute(request);
            System.out.println("----------------------");
            System.out.println("code = " + alipayResponse.getCode());
            System.out.println("msg = " + alipayResponse.getMsg());
            String body = alipayResponse.getBody();
            System.out.println("body = " + alipayResponse.getBody());
            return body;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        AlipayUtil alipayUtil = new AlipayUtil();
        PayOrder payOrder = new PayOrder();
        payOrder.setAmount(100l);
        payOrder.setBody("测试");
        payOrder.setSubject("test00");
        payOrder.setPayOrderId(String.valueOf(System.currentTimeMillis()));

        alipayUtil.alipayWapPayement(payOrder);

    }




}
