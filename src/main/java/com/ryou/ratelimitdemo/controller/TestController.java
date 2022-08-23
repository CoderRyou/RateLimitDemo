package com.ryou.ratelimitdemo.controller;

import com.ryou.ratelimitdemo.annotation.RateLimit;
import com.ryou.ratelimitdemo.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author ryou
 */
@RestController
@RequiredArgsConstructor
public class TestController {

    private final RedisUtil redisUtil;

    @GetMapping("/test1")
    public Object test1() {
        boolean b = redisUtil.rateLimit("count1", 5, 2);
        HashMap<String, Object> map = new HashMap<>(2);
        if(b) {
            // TODO 执行业务
            // do something
            map.put("code", 200);
            map.put("msg", "success");
        } else {
            map.put("code", 500);
            map.put("msg", "访问太频繁了，请稍后再试~");
        }
        return map;
    }

    @RateLimit(key = "count2", ttl = 60, limit = 10)
    @GetMapping("/test2")
    public Object test2() {
        HashMap<String, Object> map = new HashMap<>(2);
        // TODO 执行业务
        // do something
        map.put("code", 200);
        map.put("msg", "success");

        return map;
    }

}
