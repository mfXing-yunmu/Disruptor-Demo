package com.yunmu.disruptor.heigh;

import com.lmax.disruptor.EventHandler;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 11:45
 */
public class Handler4 implements EventHandler<Trade>{
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 4 : SET PRICE");
        Thread.sleep(1000);
        event.setPrice(17.0);
    }
}
