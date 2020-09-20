package com.shaziyasyd.ratelimiter.impl;

import com.shaziyasyd.ratelimiter.AbstractRateLimiter;
import com.shaziyasyd.ratelimiter.util.RateLimiterUtils;

import java.util.HashMap;
import java.util.TreeMap;

public class RollingWindowWithCountLimiter<K> extends AbstractRateLimiter<K> {
    private HashMap<K, TreeMap<Long, Integer>> apiHitCounts;

    public RollingWindowWithCountLimiter(int perMinThreshold) {
        super(perMinThreshold);
        apiHitCounts = new HashMap<>();
    }

    @Override
    public boolean checkLimit(K key) {
        long currentTime = RateLimiterUtils.currentTimeInSeconds();
        TreeMap<Long, Integer> countMap = apiHitCounts.getOrDefault(key, new TreeMap<>());
        countMap.entrySet().removeIf(entry -> entry.getKey() - currentTime >= 1);

        boolean allowRequest = true;
        Integer totalRequests = countMap.values().stream().reduce(0, Integer::sum);
        if(totalRequests >= perMinThreshold) {
            allowRequest = false;
        } else {
            countMap.put(currentTime, countMap.getOrDefault(currentTime, 0)+1);
        }
        return allowRequest;
    }

}
