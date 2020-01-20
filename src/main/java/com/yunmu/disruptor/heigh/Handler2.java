package com.yunmu.disruptor.heigh;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;


/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 11:44
 */
public class Handler2 implements EventHandler<Trade>{
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 2 : SET ID");
        Thread.sleep(2000);
        event.setId(UUID.randomUUID().toString());
    }
}
