package com.hzit.common.resp;

import lombok.Data;
import lombok.ToString;

/**
 * 通用返回对象
 */
@Data
@ToString
public class BaseResp<T> {


    private String retCode; //返回状态码
    private String retMsg; //返回状态码

    private T data; //返回对象信息
}
