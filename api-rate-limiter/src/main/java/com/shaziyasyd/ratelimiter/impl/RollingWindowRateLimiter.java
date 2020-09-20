package com.shaziyasyd.ratelimiter.impl;

import com.shaziyasyd.ratelimiter.AbstractRateLimiter;
import com.shaziyasyd.ratelimiter.util.RateLimiterUtils;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class RollingWindowRateLimiter<K> extends AbstractRateLimiter<K> {
    private HashMap<K, SortedSet<Long>> apiLogsMap;

    public RollingWindowRateLimiter(int perMinThreshold) {
        super(perMinThreshold);
        this.apiLogsMap = new HashMap<>();
    }

    @Override
    public boolean checkLimit(K key) {
        SortedSet<Long> apiLog = apiLogsMap.getOrDefault(key, new TreeSet<>());
        long currentTime = RateLimiterUtils.currentTime();
        while (!apiLog.isEmpty() && RateLimiterUtils.millisToMins(currentTime-apiLog.first()) >= 1) {
            apiLog.remove(apiLog.first());
        }
        boolean allowRequest = true;
        if(apiLog.size() > perMinThreshold) {
            allowRequest = false;
        } else {
            apiLog.add(currentTime);
        }
        return allowRequest;
    }
}
