package com.hzit.common.resp;

import lombok.Data;

import java.util.Date;

@Data
public class PayOrderData {

    /**
     * 支付订单号
     *
     * @mbggenerated
     */
    private String payOrderId;

    /**
     * 商户ID
     *
     * @mbggenerated
     */
    private String mchId;

    /**
     * 商户订单号
     *
     * @mbggenerated
     */
    private String mchOrderNo;

    /**
     * 渠道ID
     *
     * @mbggenerated
     */
    private String channelId;

    /**
     * 支付金额,单位分
     *
     * @mbggenerated
     */
    private Long amount;

    /**
     * 三位货币代码,人民币:cny
     *
     * @mbggenerated
     */
    private String currency;

    /**
     * 支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成
     *
     * @mbggenerated
     */
    private Byte status;

    /**
     * 客户端IP
     *
     * @mbggenerated
     */
    private String clientIp;

    /**
     * 设备
     *
     * @mbggenerated
     */
    private String device;

    /**
     * 商品标题
     *
     * @mbggenerated
     */
    private String subject;

    /**
     * 商品描述信息
     *
     * @mbggenerated
     */
    private String body;

    /**
     * 特定渠道发起时额外参数
     *
     * @mbggenerated
     */
    private String extra;

    /**
     * 渠道商户ID
     *
     * @mbggenerated
     */
    private String channelMchId;

    /**
     * 渠道订单号
     *
     * @mbggenerated
     */
    private String channelOrderNo;

    /**
     * 渠道支付错误码
     *
     * @mbggenerated
     */
    private String errCode;

    /**
     * 渠道支付错误描述
     *
     * @mbggenerated
     */
    private String errMsg;

    /**
     * 扩展参数1
     *
     * @mbggenerated
     */
    private String param1;

    /**
     * 扩展参数2
     *
     * @mbggenerated
     */
    private String param2;

    /**
     * 通知地址
     *
     * @mbggenerated
     */
    private String notifyUrl;

    /**
     * 通知次数
     *
     * @mbggenerated
     */
    private Byte notifyCount;

    /**
     * 最后一次通知时间
     *
     * @mbggenerated
     */
    private Long lastNotifyTime;

    /**
     * 订单失效时间
     *
     * @mbggenerated
     */
    private Long expireTime;

    /**
     * 订单支付成功时间
     *
     * @mbggenerated
     */
    private Long paySuccTime;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;
}
