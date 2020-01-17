package com.yunmu.disruptor.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-17 14:33
 */
public class OrderEventProducer {
    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer data) {
        // 1 在生产者发送消息的时候, 首先需要从我们的 RingBuffer 里面获取一个可用的序号
        long sequence = ringBuffer.next();
        try {
            // 2 根据这个序号, 找到具体的 "OrderEvent" 元素 注意:此时获取的 OrderEvent 对象是一个没有被赋值的"空对象"
            OrderEvent event = ringBuffer.get(sequence);
            // 3 进行实际的赋值处理
            event.setValue(data.getLong(0));
        } finally {
            // 4 提交发布操作
            ringBuffer.publish(sequence);
        }
    }
}