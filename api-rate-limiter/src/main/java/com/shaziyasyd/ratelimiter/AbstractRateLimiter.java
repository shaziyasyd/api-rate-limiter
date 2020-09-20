package com.shaziyasyd.ratelimiter;

public abstract class AbstractRateLimiter<K> {
    protected final int perMinThreshold;
    public AbstractRateLimiter(int perMinThreshold) {
        this.perMinThreshold = perMinThreshold;
    }

    abstract public boolean checkLimit(K key);
}
