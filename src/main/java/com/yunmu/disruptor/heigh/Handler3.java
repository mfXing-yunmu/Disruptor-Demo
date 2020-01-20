package com.yunmu.disruptor.heigh;

import com.lmax.disruptor.EventHandler;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 11:45
 */
public class Handler3 implements EventHandler<Trade>{
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println("handler 3 : NAME: "
                + event.getName()
                + ", ID: "
                + event.getId()
                + ", PRICE: "
                + event.getPrice()
                + " INSTANCE : " + event.toString());
    }
}
