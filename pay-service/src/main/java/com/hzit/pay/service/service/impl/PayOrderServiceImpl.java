package com.hzit.pay.service.service.impl;

import com.hzit.pay.service.mapper.PayOrderMapper;
import com.hzit.pay.service.model.PayOrder;
import com.hzit.pay.service.model.PayOrderExample;
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

    @Override
    public int updatePayOrder(PayOrder payOrder) {

        payOrder.setStatus(payOrder.getStatus());
        payOrder.setPaySuccTime(System.currentTimeMillis());
        PayOrderExample example = new PayOrderExample();
        PayOrderExample.Criteria criteria = example.createCriteria();
        criteria.andPayOrderIdEqualTo(payOrder.getPayOrderId());
        return payOrderMapper.updateByExampleSelective(payOrder, example);

    }
}
