package com.hzit.common.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class PayChannelReq {

    @NotNull(message ="商户id为空")
    private String mchId; //商户id

    @NotNull(message ="渠道id为空")
    private String channelId;
}
