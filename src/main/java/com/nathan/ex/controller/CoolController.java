package com.nathan.ex.controller;

import com.nathan.ex.aop.LogInvokedMethod;
import com.nathan.ex.serialize.annotaion.ExResponseBody;
import com.nathan.ex.bean.UserInfo;
import com.nathan.ex.serialize.annotaion.ExRequestBody;
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
    public UserInfo getResolver (@ExRequestBody String acct) {
        UserInfo user = new UserInfo();
        user.setName("HooKong");
        return user;
    }

    @RequestMapping(value = "/testConverter", produces = {"application/ex-serialize"})
    @ResponseBody
    @LogInvokedMethod
    public UserInfo getConverter (@RequestBody String acct) {
        UserInfo user = new UserInfo();
        user.setName("HooKong");
        user.setAge("90");
        return user;
    }

}