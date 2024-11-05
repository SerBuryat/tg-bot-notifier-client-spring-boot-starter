package com.thunderbase.tg.client.springbootstarter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

/** Class to intercept requests.
 *  {@link #afterBodyRead(Object, HttpInputMessage, MethodParameter, Type, Class) afterBodyRead}
 *  uses for injecting request body into {@link RequestBodyHolder}.
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RequestBodyInjectorControllerAdvice implements RequestBodyAdvice {

    private final ObjectMapper mapper;
    private final RequestBodyHolder requestBodyHolder;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        requestBodyHolder.setRequestBody(
                mapper.valueToTree(body)
        );
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}
