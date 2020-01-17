package com.yunmu.disruptor.quickstart;

import com.lmax.disruptor.EventFactory;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-17 10:47
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        // 这个方法就是为了返回空的数据对象（Event）
        return new OrderEvent();
    }
}
