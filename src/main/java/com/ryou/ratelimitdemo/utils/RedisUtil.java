package com.ryou.ratelimitdemo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate redisTemplate;

    private static final String RATE_LIMIT_SCRIPT =
            "local isExsits = redis.call('exists', KEYS[1])\n" +
            "if isExsits == 1 then\n" +
            "\tlocal count = redis.call('get', KEYS[1])\n" +
            "\tif tonumber(count) >= tonumber(ARGV[1]) then\n" +
            "\t\treturn 0\n" +
            "\telse \n" +
            "\t\tredis.call('incr', KEYS[1])\n" +
            "\t\treturn 1\n" +
            "\tend\n" +
            "else\n" +
            "\tredis.call('set', KEYS[1], 1)\n" +
            "\tredis.call('expire', KEYS[1], ARGV[2])\n" +
            "\treturn 1\n" +
            "end";

    public boolean rateLimit(String key, int limit, int ttl) {
        Boolean res = (Boolean) redisTemplate.execute(
                RedisScript.of(RATE_LIMIT_SCRIPT, Boolean.class),
                Arrays.asList(key), String.valueOf(limit), String.valueOf(ttl));
        return res;
    }

}
