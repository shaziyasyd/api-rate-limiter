package com.shaziyasyd.ratelimiter.model;

import java.util.Date;

public class Counter {
    private int count;
    private long startTime;
    public Counter(int count, long timestamp) {
        this.count = count;
        this.startTime = timestamp;
    }
    public int getCount() {
        return count;
    }
    public long getStartTime() {
        return startTime;
    }
    public void increment() {
        this.count++;
    }
    public void reset(long startTime) {
        this.startTime = startTime;
        this.count = 1;
    }
    public void reset() {
        this.reset(new Date().getTime());
    }
}
