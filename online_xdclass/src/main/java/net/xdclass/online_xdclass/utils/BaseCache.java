package net.xdclass.online_xdclass.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BaseCache {

    private Cache<String,Object> tenMinuteCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(100)
            .concurrencyLevel(5)
            .expireAfterWrite(600, TimeUnit.SECONDS)
            .recordStats()
            .build();

    private Cache<String,Object> oneHourCache = CacheBuilder.newBuilder()
            .initialCapacity(10)
            .maximumSize(100)
            .concurrencyLevel(5)
            .expireAfterWrite(600*6, TimeUnit.SECONDS)
            .recordStats()
            .build();

    public Cache<String, Object> getOneHourCache() {
        return oneHourCache;
    }

    public void setOneHourCache(Cache<String, Object> oneHourCache) {
        this.oneHourCache = oneHourCache;
    }

    public Cache<String, Object> getTenMinuteCache() {
        return tenMinuteCache;
    }

    public void setTenMinuteCache(Cache<String, Object> tenMinuteCache) {
        this.tenMinuteCache = tenMinuteCache;
    }
}
