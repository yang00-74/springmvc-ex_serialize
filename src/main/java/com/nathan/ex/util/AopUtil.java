package com.nathan.ex.util;

import com.nathan.ex.aop.annotation.LogInvokedMethod;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author nathan.yang
 * @date 2019/11/29
 */
public class AopUtil {

    /**
     * In order to make {@link LogInvokedMethod} works while method called inner class, we
     * should get the proxy of target class and then invoke target method by it
     *
     * Precondition:
     *    add annotation {@link EnableAspectJAutoProxy (proxyTargetClass = true, exposeProxy = true)} to SpringBoot main
     *    class
     *
     * example:{@link com.nathan.ex.controller.CoolController#testInnerCalled(String)}
     *
     * @param targetClass the target class
     * @return the aop proxy object of target class
     * */
    public static <T> T getProxy(Class<T> targetClass) {
        return (T) AopContext.currentProxy();
    }
}
