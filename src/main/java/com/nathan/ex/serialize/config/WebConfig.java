package com.nathan.ex.serialize.config;

import com.nathan.ex.serialize.ExMessageConverter;
import com.nathan.ex.serialize.ExMethodProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author nathan.yang
 * @date 2019/11/17
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ExMethodProcessor exMethodProcessor;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        System.out.println("configure invoked");
        // 设置优先级
        converters.set(0, new ExMessageConverter());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(exMethodProcessor);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(exMethodProcessor);
    }

    @Bean
    public ExMethodProcessor newExResolver() {
        return new ExMethodProcessor(Arrays.asList(new ExMessageConverter()));
    }
}
