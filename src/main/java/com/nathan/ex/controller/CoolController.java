package com.nathan.ex.controller;

import com.nathan.ex.aop.annotation.LogInvokedMethod;
import com.nathan.ex.aop.annotation.TraceId;
import com.nathan.ex.serialize.annotaion.ExResponseBody;
import com.nathan.ex.bean.UserInfo;
import com.nathan.ex.serialize.annotaion.ExRequestBody;
import com.nathan.ex.util.AopUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author nathan.yang
 */
@Controller
@Slf4j
public class CoolController {

    @RequestMapping(value = "/testEx")
    @ExResponseBody
    @LogInvokedMethod
    @TraceId
    public UserInfo getResolver (@ExRequestBody String acct) {
        UserInfo user = new UserInfo();
        user.setName("HooKong");
        log.warn("Hello, world");
        return user;
    }

    @RequestMapping(value = "/testConverter", produces = {"application/ex-serialize"})
    @ResponseBody
    @LogInvokedMethod
    @TraceId
    public UserInfo getConverter (@RequestBody String acct) {
        UserInfo user = new UserInfo();
        user.setName("HooKong");
        user.setAge("90");
        AopUtil.getProxy(this.getClass()).testInnerCalled("Dog");
        testInnerCalled("Dog");
        return user;
    }

    @LogInvokedMethod
    public String testInnerCalled(String s) {
        return "God";
    }
}