package com.yunmu.disruptor.heigh;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 11:43
 */
public class Handler1 implements EventHandler<Trade>, WorkHandler<Trade>{
    /**
     * EventHandler
     */
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    /**
     * WorkHandler
     */
    @Override
    public void onEvent(Trade event) throws Exception {
        System.err.println("handler 1 : SET NAME");
        Thread.sleep(1000);
        event.setName("H1");
    }
}
