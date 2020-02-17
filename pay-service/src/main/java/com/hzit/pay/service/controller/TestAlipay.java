package com.hzit.pay.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;

public class TestAlipay {

    public static void main(String[] args) {

        String url  = "https://openapi.alipaydev.com/gateway.do"; //TODO 支付宝支付地址

        String appId = "2016101200667654";

        String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCmVQ8PMyUwFoO2/ahkFqZ5+vKKC+t2CBYhWRSbA2PndiR9aAzt+f4BnygAxNBGWrMNYvHKlk2WX7LMR1r+adrUbdS/3meDYWbSm/VkthfKIIQABE0ePekqtFHS+Rj3lm4V/vxmeWLuRMqPy/0T5pIsgoarDHwgBthp4hBaHQ74iNOemKzZz38uirz5uMlc7kYMrAq/3YbU4qoyLZAwFGvvL6esF5VGmr3Qqd8aFSVbsYrXs7PledGjrdj0MtmvM83RJjxEOj+G555SOYIpsM4FLMB6cB9w2mVtfzeemLFxF1Jvn2oXNVCnNoBA97xFc9t9pk5FcyT/1NbHMpY5C3vZAgMBAAECggEBAIRI/dMe4CKgpVyx3GzNvtUgiijUVg3nXIJlMIggaCI7ycWbLT1PJ37ur4f8/5AUrnalDJwMceR4c+EXlvzj/1AXka6PGVBZ9MbnloDkeYMd3R2OTi8RGhIGm1TpG7w9fYws4Vw2ikHpoBhBKiPbqsGPhb8gDNAD4bZEPrEMj02FbF2JdypM0CQQmeOJO1dCQ9Iq+YY+6GIIsGo9PSG1Irg9viZTpQlsRGi4nscmSRu+zVsW5O144uwfEn0/OPX6GptjXex3gmQ86iqesSTxpVhvnsW+/vQXoaCzTcOkn5JfuOFBGGmEzDSaY7or2PgQbqZxE9stKUu7P84hg8o9DqkCgYEAzncefw4BiX6BfuaReZmsfypCTszWz4zAnGwS7e0kuTD441zBb6C5G9767wpZVI6TSxMjlQKktOLjeLAUMmkpYaJxBK0DL4QIq6mfYLXPnJOZXn98i0djS7Q4DBaJY9BzcOK8tCJXoYRR6JdkdjWHzthPRuU3+dvQb4aZ8+OKvdsCgYEAzj0CpdrzraQCTDqvoeGpUSR9OYghCe3D1pLAIvqT98ruveyvHjntyNu+SK1FfDbdRg4d/uZ1RrFbw81Uj8VVSBhIJ5HsWAGJ0aAy+DUMFFPbJ3yeHUH7GE696NPzgfhOFvauOkBIJfgo1Pm5XzW0ZeJuYv6pvC8u8xFSKd1XrVsCgYEAqRLbImix0YqLOQRPdSjnHWvZyPXtoyE8Sdwm1DgyroTwDCBVN7iCXdSH4WC0Lo5jCEOwC8KSZp62cIqgv3dmr9F6g726shqJjynkninEzhAAtGXp85SON9xpWVzLPbXiEztaFJla8aWN/c6cMkE76aMxqHjlEfN0CSBMynA3makCgYEAvR6DTBXguI0aMk7qeXbWbfsY/eXP7ivjFuPRXwILKAImNXFSEFSssTCYaErD2ijxdelCJlmsl1vSO1YRwhkBoC/sEqUkZntapyjRNHxojEdtQuNqlofHur2SNmwN81QAP9yLXg1OW8sZnls1WpKDz534/bxrZ46Y7fonLw6y9NsCgYBflMbONSsLfVihJnFLjwuEH8THz6NQC+4SiHVGn25O9PtaiycF1UKys98lwElVzHxi7+mZ6tyQKQ5nhphGFW9/25IflipqvLEfIzGHHJvjBKRttgu+qsSABJowNPQy6luVmXpODnf3sHw4JD3yN2UflW4seSc3Rlp9N7gj07r3Kg==";

        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAplUPDzMlMBaDtv2oZBamefryigvrdggWIVkUmwNj53YkfWgM7fn+AZ8oAMTQRlqzDWLxypZNll+yzEda/mna1G3Uv95ng2Fm0pv1ZLYXyiCEAARNHj3pKrRR0vkY95ZuFf78Znli7kTKj8v9E+aSLIKGqwx8IAbYaeIQWh0O+IjTnpis2c9/Loq8+bjJXO5GDKwKv92G1OKqMi2QMBRr7y+nrBeVRpq90KnfGhUlW7GK17Oz5XnRo63Y9DLZrzPN0SY8RDo/hueeUjmCKbDOBSzAenAfcNplbX83npixcRdSb59qFzVQpzaAQPe8RXPbfaZORXMk/9TWxzKWOQt72QIDAQAB";

        AlipayClient alipayClient = new DefaultAlipayClient(url,appId,privateKey,"json","GBK",publicKey,"RSA2");
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        JSONObject object = new JSONObject();

//        object.put("body","Iphone6 16G");
        object.put("subject","大乐透");
        object.put("out_trade_no", System.currentTimeMillis());
        object.put("total_amount","1");
        object.put("quit_url","https://docs.open.alipay.com/api_1/alipay.trade.wap.pay/");
        object.put("product_code","QUICK_WAP_WAY");

        request.setBizContent(object.toJSONString());


//        request.setBizContent("{" +
//                "\"body\":\"Iphone6 16G\"," +
//                "\"subject\":\"大乐透\"," +
//                "\"out_trade_no\":\"70501111111S001111119\"," +
//                "\"timeout_express\":\"90m\"," +
//                "\"time_expire\":\"2016-12-31 10:05\"," +
//                "\"total_amount\":9.00," +
//                "\"seller_id\":\"2088102147948060\"," +
//                "\"auth_token\":\"appopenBb64d181d0146481ab6a762c00714cC27\"," +
//                "\"goods_type\":\"0\"," +
//                "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
//                "\"quit_url\":\"http://www.taobao.com/product/113714.html\"," +
//                "\"product_code\":\"QUICK_WAP_WAY\"," +
//                "\"promo_params\":\"{\\\"storeIdType\\\":\\\"1\\\"}\"," +
//                "\"royalty_info\":{" +
//                "\"royalty_type\":\"ROYALTY\"," +
//                "        \"royalty_detail_infos\":[{" +
//                "          \"serial_no\":1," +
//                "\"trans_in_type\":\"userId\"," +
//                "\"batch_no\":\"123\"," +
//                "\"out_relation_id\":\"20131124001\"," +
//                "\"trans_out_type\":\"userId\"," +
//                "\"trans_out\":\"2088101126765726\"," +
//                "\"trans_in\":\"2088101126708402\"," +
//                "\"amount\":0.1," +
//                "\"desc\":\"分账测试1\"," +
//                "\"amount_percentage\":\"100\"" +
//                "          }]" +
//                "    }," +
//                "\"extend_params\":{" +
//                "\"sys_service_provider_id\":\"2088511833207846\"," +
//                "\"hb_fq_num\":\"3\"," +
//                "\"hb_fq_seller_percent\":\"100\"," +
//                "\"industry_reflux_info\":\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\"," +
//                "\"card_type\":\"S0JP0000\"" +
//                "    }," +
//                "\"sub_merchant\":{" +
//                "\"merchant_id\":\"19023454\"," +
//                "\"merchant_type\":\"alipay: 支付宝分配的间连商户编号, merchant: 商户端的间连商户编号\"" +
//                "    }," +
//                "\"merchant_order_no\":\"20161008001\"," +
//                "\"enable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
//                "\"disable_pay_channels\":\"pcredit,moneyFund,debitCardExpress\"," +
//                "\"store_id\":\"NJ_001\"," +
//                "\"settle_info\":{" +
//                "        \"settle_detail_infos\":[{" +
//                "          \"trans_in_type\":\"cardAliasNo\"," +
//                "\"trans_in\":\"A0001\"," +
//                "\"summary_dimension\":\"A0001\"," +
//                "\"settle_entity_id\":\"2088xxxxx;ST_0001\"," +
//                "\"settle_entity_type\":\"SecondMerchant、Store\"," +
//                "\"amount\":0.1" +
//                "          }]" +
//                "    }," +
//                "\"invoice_info\":{" +
//                "\"key_info\":{" +
//                "\"is_support_invoice\":true," +
//                "\"invoice_merchant_name\":\"ABC|003\"," +
//                "\"tax_num\":\"1464888883494\"" +
//                "      }," +
//                "\"details\":\"[{\\\"code\\\":\\\"100294400\\\",\\\"name\\\":\\\"服饰\\\",\\\"num\\\":\\\"2\\\",\\\"sumPrice\\\":\\\"200.00\\\",\\\"taxRate\\\":\\\"6%\\\"}]\"" +
//                "    }," +
//                "\"specified_channel\":\"pcredit\"," +
//                "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"," +
//                "\"ext_user_info\":{" +
//                "\"name\":\"李明\"," +
//                "\"mobile\":\"16587658765\"," +
//                "\"cert_type\":\"IDENTITY_CARD\"," +
//                "\"cert_no\":\"362334768769238881\"," +
//                "\"min_age\":\"18\"," +
//                "\"fix_buyer\":\"F\"," +
//                "\"need_check_info\":\"F\"" +
//                "    }" +
//                "  }");
        AlipayTradeWapPayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }



    }


}
