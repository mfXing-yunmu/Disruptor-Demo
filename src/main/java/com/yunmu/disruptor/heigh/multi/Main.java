package com.yunmu.disruptor.heigh.multi;

import java.util.UUID;
import java.util.concurrent.*;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 18:13
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        // 1 创建 RingBuffer
        RingBuffer<Order> ringBuffer =
                RingBuffer.create(ProducerType.MULTI,
                        new EventFactory<Order>() {
                            @Override
                            public Order newInstance() {
                                return new Order();
                            }
                        },
                        1024*1024,
                        new YieldingWaitStrategy());

        // 2 通过 RingBuffer 创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 3 创建多个消费者数组:
        Consumer[] consumers = new Consumer[10];
        for(int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        // 4 构建多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<Order>(
                ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(),
                consumers);

        // 5 设置多个消费者的 sequence 序号 用于单独统计消费进度, 并且设置到 RingBuffer 中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        // 6 启动 workerPool
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024),
                new ThreadPoolExecutor.AbortPolicy());
        workerPool.start(executorService);

        final CountDownLatch latch = new CountDownLatch(1);

        for(int i = 0; i < 100; i++) {
            final Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for(int j = 0; j<100; j++) {
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }

        Thread.sleep(2000);
        System.err.println("----------线程创建完毕，开始生产数据----------");
        latch.countDown();

        Thread.sleep(10000);

        System.err.println("任务总数:" + consumers[2].getCount());
    }

    static class EventExceptionHandler implements ExceptionHandler<Order> {
        @Override
        public void handleEventException(Throwable ex, long sequence, Order event) {
        }

        @Override
        public void handleOnStartException(Throwable ex) {
        }

        @Override
        public void handleOnShutdownException(Throwable ex) {
        }
    }
}
