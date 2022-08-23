package com.ryou.ratelimitdemo.annotation;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.*;

/**
 * @author ryou
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    String key();

    int limit() default 10;

    int ttl() default 1;

    String message() default "访问太频繁了，请稍后再试~";

}
