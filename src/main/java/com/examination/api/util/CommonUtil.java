package com.examination.api.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Log4j2
@Component
public class CommonUtil {

    public static boolean isEmailRegex(String str) throws Exception {
        if (str.length() > 100) return false;
        String pattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{1,4}\\b";
        return Pattern.matches(pattern, str);
    }
}
