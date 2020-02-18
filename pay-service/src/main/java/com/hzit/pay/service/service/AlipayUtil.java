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

/**
 * 支付宝支付接口
 */
@Service
public class AlipayUtil {

    private Logger logger = LoggerFactory.getLogger(AlipayUtil.class);


    /**
     * alipay.trade.wap.pay 支付宝手机支付2.0
     * @return
     */
    public String alipayWapPayement(PayOrder payOrder){
        //配置文件 读 配置中心

        String url = "https://openapi.alipaydev.com/gateway.do"; //沙箱环境
        String appId = "2016101200667654"; //沙箱环境
        String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCmVQ8PMyUwFoO2/ahkFqZ5+vKKC+t2CBYhWRSbA2PndiR9aAzt+f4BnygAxNBGWrMNYvHKlk2WX7LMR1r+adrUbdS/3meDYWbSm/VkthfKIIQABE0ePekqtFHS+Rj3lm4V/vxmeWLuRMqPy/0T5pIsgoarDHwgBthp4hBaHQ74iNOemKzZz38uirz5uMlc7kYMrAq/3YbU4qoyLZAwFGvvL6esF5VGmr3Qqd8aFSVbsYrXs7PledGjrdj0MtmvM83RJjxEOj+G555SOYIpsM4FLMB6cB9w2mVtfzeemLFxF1Jvn2oXNVCnNoBA97xFc9t9pk5FcyT/1NbHMpY5C3vZAgMBAAECggEBAIRI/dMe4CKgpVyx3GzNvtUgiijUVg3nXIJlMIggaCI7ycWbLT1PJ37ur4f8/5AUrnalDJwMceR4c+EXlvzj/1AXka6PGVBZ9MbnloDkeYMd3R2OTi8RGhIGm1TpG7w9fYws4Vw2ikHpoBhBKiPbqsGPhb8gDNAD4bZEPrEMj02FbF2JdypM0CQQmeOJO1dCQ9Iq+YY+6GIIsGo9PSG1Irg9viZTpQlsRGi4nscmSRu+zVsW5O144uwfEn0/OPX6GptjXex3gmQ86iqesSTxpVhvnsW+/vQXoaCzTcOkn5JfuOFBGGmEzDSaY7or2PgQbqZxE9stKUu7P84hg8o9DqkCgYEAzncefw4BiX6BfuaReZmsfypCTszWz4zAnGwS7e0kuTD441zBb6C5G9767wpZVI6TSxMjlQKktOLjeLAUMmkpYaJxBK0DL4QIq6mfYLXPnJOZXn98i0djS7Q4DBaJY9BzcOK8tCJXoYRR6JdkdjWHzthPRuU3+dvQb4aZ8+OKvdsCgYEAzj0CpdrzraQCTDqvoeGpUSR9OYghCe3D1pLAIvqT98ruveyvHjntyNu+SK1FfDbdRg4d/uZ1RrFbw81Uj8VVSBhIJ5HsWAGJ0aAy+DUMFFPbJ3yeHUH7GE696NPzgfhOFvauOkBIJfgo1Pm5XzW0ZeJuYv6pvC8u8xFSKd1XrVsCgYEAqRLbImix0YqLOQRPdSjnHWvZyPXtoyE8Sdwm1DgyroTwDCBVN7iCXdSH4WC0Lo5jCEOwC8KSZp62cIqgv3dmr9F6g726shqJjynkninEzhAAtGXp85SON9xpWVzLPbXiEztaFJla8aWN/c6cMkE76aMxqHjlEfN0CSBMynA3makCgYEAvR6DTBXguI0aMk7qeXbWbfsY/eXP7ivjFuPRXwILKAImNXFSEFSssTCYaErD2ijxdelCJlmsl1vSO1YRwhkBoC/sEqUkZntapyjRNHxojEdtQuNqlofHur2SNmwN81QAP9yLXg1OW8sZnls1WpKDz534/bxrZ46Y7fonLw6y9NsCgYBflMbONSsLfVihJnFLjwuEH8THz6NQC+4SiHVGn25O9PtaiycF1UKys98lwElVzHxi7+mZ6tyQKQ5nhphGFW9/25IflipqvLEfIzGHHJvjBKRttgu+qsSABJowNPQy6luVmXpODnf3sHw4JD3yN2UflW4seSc3Rlp9N7gj07r3Kg==";
        String alipayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";

        AlipayClient alipayClient = new DefaultAlipayClient(url,appId,privateKey,"json","GBK",alipayPublicKey,"RSA2");
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl("http://127.0.0.1:5000/notify/payResult.htm"); //后端异步通知，更新交易订单。
        request.setReturnUrl("http://127.0.0.1:5000/shop/showCode"); //支付完成，跳转此页面

        JSONObject bizContent = new JSONObject();

        /***************必传******************/
        bizContent.put("subject", payOrder.getSubject());
        bizContent.put("out_trade_no", payOrder.getPayOrderId()); //流水
        bizContent.put("total_amount", AmountUtil.convertCent2Dollar(payOrder.getAmount().toString()));//金额
        bizContent.put("quit_url", "www.baidu.com"); //支付中断返回的页面
        bizContent.put("product_code", "QUICK_WAP_WAY"); //手机网页wap支付

        bizContent.put("body", payOrder.getBody()); //可空

        request.setBizContent(bizContent.toJSONString());

        try {
            AlipayResponse alipayResponse = alipayClient.pageExecute(request);
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
