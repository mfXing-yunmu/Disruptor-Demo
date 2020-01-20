package com.yunmu.disruptor.heigh;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 11:47
 */
public class TradePushlisher implements Runnable{
    private Disruptor<Trade> disruptor;
    private CountDownLatch latch;

    private static int PUBLISH_COUNT = 1;

    public TradePushlisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {

        TradeEventTranslator eventTranslator = new TradeEventTranslator();
        for(int i =0; i < PUBLISH_COUNT; i ++){
            //新的提交任务的方式
            disruptor.publishEvent(eventTranslator);
        }
        latch.countDown();
    }
}

class TradeEventTranslator implements EventTranslator<Trade> {

    private Random random = new Random();

    @Override
    public void translateTo(Trade event, long sequence) {
        this.generateTrade(event);
    }

    private void generateTrade(Trade event) {
        event.setPrice(random.nextDouble() * 9999);
    }

}
