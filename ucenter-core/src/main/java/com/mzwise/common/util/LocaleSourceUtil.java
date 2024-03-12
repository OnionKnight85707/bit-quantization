package com.mzwise.common.util;
import com.mzwise.common.response.ResponseMessageDecorate;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 本地语言帮助
 * @author wmf
 */
public class LocaleSourceUtil {
    public static String getMessage(String code) {
        ResponseMessageDecorate sourceService = SpringUtil.getBeanOfType(ResponseMessageDecorate.class);
        if (sourceService == null) {
            return code;
        }
        String message = sourceService.getMessage(code);
        if (message.equals("")) {
            return code;
        } else {
            return message;
        }
    }

    public static String getLanguage() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.getLanguage() + "_" + locale.getCountry();
    }
}
