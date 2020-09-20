package com.shaziyasyd.ratelimiter.util;

import java.util.Date;

public class RateLimiterUtils {
    public static int millisToMins(long millis) {
        return millisToSeconds(millis) /60;
    }
    public static int millisToSeconds(long millis) {
        return (int) (millis/1000);
    }
    public static long currentTime() {
        return new Date().getTime();
    }
    public static int currentTimeInSeconds() {
        return (int)millisToSeconds(currentTime());
    }
}
