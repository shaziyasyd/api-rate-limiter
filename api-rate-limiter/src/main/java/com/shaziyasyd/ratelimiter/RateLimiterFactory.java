package com.shaziyasyd.ratelimiter;

import com.shaziyasyd.ratelimiter.impl.FixedWindowRateLimiter;

import java.util.Map;

public class RateLimiterFactory<K> {

    private RateLimiterFactory() {}
    private static RateLimiterFactory instance;

    private Map<RateLimiterStrategy, AbstractRateLimiter<K>> rateLimiters;

    public static <K> RateLimiterFactory<K> getInstance() {
        if(instance == null) {
            instance = new RateLimiterFactory<>();
        }
        return instance;
    }

    public AbstractRateLimiter<K> getRateLimiter(RateLimiterStrategy strategy, int perMinThreshold) {
        if(!rateLimiters.containsKey(strategy)) {
            rateLimiters.put(strategy, new FixedWindowRateLimiter<>(perMinThreshold));
        }
        return rateLimiters.get(strategy);
    }
}
