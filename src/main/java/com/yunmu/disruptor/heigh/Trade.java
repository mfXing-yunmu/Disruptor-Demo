package com.yunmu.disruptor.heigh;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: mfXing
 * @CreateDate: 2020-01-20 11:41
 */
@Data
public class Trade {
    private String id;
    private String name;
    private double price;
    private AtomicInteger count = new AtomicInteger(0);
}
