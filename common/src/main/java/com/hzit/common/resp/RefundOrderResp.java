package com.hzit.common.resp;

import lombok.Data;
import lombok.ToString;

/**
 * 退款接口返回参数
 */
@Data
@ToString
public class RefundOrderResp<T>{

    private String retCode; //返回状态码
    private String retMsg; //返回状态码

    private T data; //返回对象信息




}
