package com.yunmu.disruptor.heigh.multi;

import com.lmax.disruptor.RingBuffer;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 18:12
 */
public class Producer {
    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(String uuid) {
        long sequence = ringBuffer.next();
        try {
            Order order = ringBuffer.get(sequence);
            order.setId(uuid);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
