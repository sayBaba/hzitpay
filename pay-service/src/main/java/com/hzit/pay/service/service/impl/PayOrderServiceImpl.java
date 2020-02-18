package com.hzit.pay.service.service.impl;

import com.hzit.pay.service.mapper.PayOrderMapper;
import com.hzit.pay.service.model.PayOrder;
import com.hzit.pay.service.service.IPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayOrderServiceImpl implements IPayOrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
    public int createPayOrder(PayOrder payOrder) {
        return payOrderMapper.insertSelective(payOrder);
    }
}
