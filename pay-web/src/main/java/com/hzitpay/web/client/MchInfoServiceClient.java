package com.hzitpay.web.client;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @author dingzhiwei jmdhappy@126.com
 * @date 2017-07-05
 * @version V1.0
 * @Copyright: www.xxpay.org
 */
@Service
public class MchInfoServiceClient {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "selectMchInfoFallback")
    public String selectMchInfo(String jsonParam) {
        return restTemplate.getForEntity("http://PAY-SERVICE/mchInfo/select?mchId=" +jsonParam, String.class).getBody();
    }

    /**
     * 服务调用出错，执行此方法
     * @param jsonParam
     * @return
     */
    public String selectMchInfoFallback(String jsonParam) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("retCode","error");
        jsonObject.put("retMsg","服务暂不可用");
        return jsonObject.toJSONString();
    }

}