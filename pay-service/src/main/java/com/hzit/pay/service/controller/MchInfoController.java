package com.hzit.pay.service.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mchInfo")
public class MchInfoController {

    private Logger logger = LoggerFactory.getLogger(MchInfoController.class);

    /**
     * 根据商户id 查询商户信息
     * @param mchId
     * @return
     */
    @RequestMapping("/select")
    public String getMchInfo(@RequestParam String mchId){
        logger.info("接受查询商户信息请求，参数mchId：{}",mchId);

        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isEmpty(mchId)){
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg","mchId为空");
            return jsonObject.toJSONString();
        }

        if("10000".equals(mchId)){
            jsonObject.put("retCode","SUCCESS");
            jsonObject.put("retMsg","查询成功");
            JSONObject data = new JSONObject();

            data.put("mchId","10000");
            data.put("reqKey","qwe123");
            data.put("resKey","qwe123");
            jsonObject.put("data",data);
            return jsonObject.toJSONString();
        }else{
            jsonObject.put("retCode","FAIL");
            jsonObject.put("retMsg","商户id不存在");
            return jsonObject.toJSONString();
        }
    }




}
