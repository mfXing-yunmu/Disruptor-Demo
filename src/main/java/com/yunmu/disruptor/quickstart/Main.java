package com.yunmu.disruptor.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-17 12:11
 */
public class Main {
    public static void main(String[] args) {
        // 参数准备工作
        OrderEventFactory orderEventFactory = new OrderEventFactory();
        int ringBufferSize = 4;
        ExecutorService exService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
                0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024),
                new ThreadPoolExecutor.AbortPolicy());

        /**
         * 1 eventFactory: 消息(event)工厂对象
         * 2 ringBufferSize: 容器的长度
         * 3 executor: 线程池(建议使用自定义线程池) RejectedExecutionHandler
         * 4 ProducerType: 单生产者 还是 多生产者
         * 5 waitStrategy: 等待策略
         */
        // 1. 实例化 Disruptor 对象
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(orderEventFactory,
                ringBufferSize,
                exService,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        // 2. 添加消费者的监听 (构建 Disruptor 与 消费者的一个关联关系)
        disruptor.handleEventsWith(new OrderEventHandler());

        // 3. 启动 Disruptor
        disruptor.start();

        // 4. 获取实际存储数据的容器: RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);

        for(long i = 0 ; i < 5; i ++){
            bb.putLong(0, i);
            producer.sendData(bb);
        }

        disruptor.shutdown();
        exService.shutdown();
    }
}
