package com.github.super_mall.controller.globalExceptionHanderController;

import com.github.super_mall.exceptions.LoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = LoginException.class)
    public Map<String, String> handlerLoginException (LoginException e) {
        Map<String, String> map = new HashMap<>();
        map.put("errMsg", e.getMessage());
        return map;
    }

}
