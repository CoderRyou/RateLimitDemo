package com.ryou.ratelimitdemo.error.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @author ryou
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        e.printStackTrace();
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("code", 500);
        map.put("msg", e.getCause().getMessage());
        return map;
    }

}
