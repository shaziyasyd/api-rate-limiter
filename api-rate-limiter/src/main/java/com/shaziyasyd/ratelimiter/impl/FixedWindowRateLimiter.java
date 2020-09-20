package com.shaziyasyd.ratelimiter.impl;

import com.shaziyasyd.ratelimiter.AbstractRateLimiter;
import com.shaziyasyd.ratelimiter.model.Counter;
import com.shaziyasyd.ratelimiter.util.RateLimiterUtils;

import java.util.Date;
import java.util.HashMap;

public class FixedWindowRateLimiter<K> extends AbstractRateLimiter<K> {
    private HashMap<K, Counter> apiHitCounts;

    public FixedWindowRateLimiter(int perMinThreshold) {
        super(perMinThreshold);
        this.apiHitCounts = new HashMap<>();
    }

    @Override
    public boolean checkLimit(K key) {
        long currentTime = new Date().getTime();
        Counter counter = apiHitCounts.getOrDefault(key, new Counter(1, currentTime));
        long minutesSinceStart = RateLimiterUtils.millisToMins(currentTime - counter.getStartTime());
        boolean allowRequest = true;
        if(minutesSinceStart >= 1) {
            counter.reset(currentTime);
        } else if(counter.getCount() > perMinThreshold) {
            allowRequest = false;
        } else {
            counter.increment();
        }
        return allowRequest;
    }

}
