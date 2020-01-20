package com.yunmu.disruptor.heigh;

import com.lmax.disruptor.EventHandler;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 11:46
 */
public class Handler5 implements EventHandler<Trade>{
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 5 : GET PRICE: " +  event.getPrice());
        Thread.sleep(1000);
        event.setPrice(event.getPrice() + 3.0);
    }
}
