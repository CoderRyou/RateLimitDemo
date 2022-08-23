package com.ryou.ratelimitdemo.aspect;

import com.ryou.ratelimitdemo.annotation.RateLimit;
import com.ryou.ratelimitdemo.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author ryou
 */
@Component
@Aspect
@RequiredArgsConstructor
public class RateLimitAspect {
    
    private final RedisUtil redisUtil;

    @Before("@annotation(rateLimit)")
    public void before(RateLimit rateLimit) throws Exception {
        String key = rateLimit.key();
        int ttl = rateLimit.ttl();
        int limit = rateLimit.limit();
        boolean b = redisUtil.rateLimit(key, limit, ttl);
        if(!b) {
            throw new Exception(rateLimit.message());
        }
    }

}
