package com.yunmu.disruptor.quickstart;

import com.lmax.disruptor.EventHandler;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-17 12:07
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费者 ：" + orderEvent.getValue());
    }
}
