package com.mzwise.intercept;

import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.LocaleSourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class InterceptResponse implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(body instanceof CommonResult) {
            CommonResult result = (CommonResult) body;
            if (StringUtils.isNotEmpty(result.getMessage())) {
                result.setMessage(LocaleSourceUtil.getMessage(result.getMessage()));
            }
        }
        return body;
    }
}