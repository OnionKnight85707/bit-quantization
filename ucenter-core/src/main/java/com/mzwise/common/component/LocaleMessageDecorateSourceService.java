package com.mzwise.common.component;

import com.mzwise.common.response.ResponseMessageDecorate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author wmf
 * @date 2020年01月30日
 */
@Component
public class LocaleMessageDecorateSourceService implements ResponseMessageDecorate {
    @Autowired
    private MessageSource messageSource;

    /**
     * @param code ：对应messages配置的key.
     * @return
     */
    @Override
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }


    /**
     * @param code           ：对应messages配置的key.
     * @param args           : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    public String getSystemMessage(String code) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return getSystemMessage(code, request.getHeader("lang"));
    }

    public String getSystemMessage(String code, String languageCountry) {
        if (StringUtils.isBlank(languageCountry)) {
            languageCountry = "zh_HK";
        }
        String[] split = languageCountry.split("_");
        Locale locale = new Locale(split[0], split[1]);
        return messageSource.getMessage(code, null, null, locale);
    }

}
