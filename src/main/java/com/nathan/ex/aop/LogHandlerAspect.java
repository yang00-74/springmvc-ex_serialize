package com.nathan.ex.aop;

import com.nathan.ex.util.Log;
import com.nathan.ex.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author nathan.yang
 * @date 2019/11/28
 */
@Slf4j
@Aspect
@Component
public class LogHandlerAspect {

    @Around(value = "@annotation(com.nathan.ex.aop.LogInvokedMethod)")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {

        InvokedMethodInfo methodInfo = getInvokedMethodInfo(pjp);
        HashMap map = new HashMap(16);

        methodInfo.getArgumentInfoList()
                .forEach(argumentInfo -> map.put(argumentInfo.getName(), getParamJson(argumentInfo.getValue())));
        String token = TokenUtil.getMethodToken();
        Log.i(token, ": {}.{}, InputArguments: {}", methodInfo.getInvokedClass(), methodInfo.getInvokedMethod(),
                map.toString());

        Object res = pjp.proceed();
        Log.i(token, ": {}.{}, Return: {}", methodInfo.getInvokedClass(), methodInfo.getInvokedMethod(),
                getParamJson(res));

        return res;
    }

    private Object getParamJson(Object value) {
        if (value == null) {
            return "null";
        } else {
            return value.toString();
        }
    }

    private InvokedMethodInfo getInvokedMethodInfo(ProceedingJoinPoint pjp) {
        List<ArgumentInfo> argumentInfos = new ArrayList<>();
        Object[] args = pjp.getArgs();
        Class<?>[] paramsClass = new Class[args.length];

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                paramsClass[i] = args[i].getClass();
            } else {
                paramsClass[i] = null;
            }
            ArgumentInfo p = new ArgumentInfo(paramsClass[i], parameterNames[i], args[i]);
            argumentInfos.add(p);
        }
        String classPath = pjp.getThis().getClass().getName();
        String className = classPath.substring(classPath.lastIndexOf(".") + 1, classPath.indexOf("$"));
        return new InvokedMethodInfo(className, methodSignature.getName(), argumentInfos);
    }

    @Data
    @AllArgsConstructor
    private static class ArgumentInfo {
        private Class clz;
        private String name;
        private Object value;
    }

    @Data
    @AllArgsConstructor
    private static class InvokedMethodInfo {
        private String invokedClass;
        private String invokedMethod;
        private List<ArgumentInfo> argumentInfoList;
    }

}
