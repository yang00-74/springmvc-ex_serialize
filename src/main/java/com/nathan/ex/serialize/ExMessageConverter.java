package com.nathan.ex.serialize;

import com.nathan.ex.util.Log;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author nathan.yang
 * @date 2019/11/17
 */
public class ExMessageConverter extends AbstractGenericHttpMessageConverter<Object> {

    private static final MediaType EX_STRING = MediaType.valueOf("application/ex-serialize");

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        MediaType[] typeArr = {EX_STRING};
        return Arrays.asList(typeArr);
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        byte[] bytes = StreamUtils.copyToByteArray(inputMessage.getBody());
        String json = new String(bytes);
        Log.trace();
        return json + "-hello";
    }

    @Override
    protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        outputMessage.getHeaders().setContentType(EX_STRING);
        Log.trace();
        String json = "hello:" + o.toString();
        outputMessage.getBody().write(json.getBytes());
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        return readInternal(contextClass, inputMessage);
    }
}
