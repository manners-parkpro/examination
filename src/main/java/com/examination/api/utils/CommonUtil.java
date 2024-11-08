package com.examination.api.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class CommonUtil {

    public static String markTime(LocalTime startDate, LocalTime endDate) {
        if (startDate == null || endDate == null)
            return "항시";

        String startTime = startDate.format(DateTimeFormatter.ofPattern("HH:mm"));
        String endTime = endDate.format(DateTimeFormatter.ofPattern("HH:mm"));

        return startTime + " ~ " + endTime;
    }

    public static String markDate(LocalDate startDate, LocalDate endDate) {
        String start = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String end = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return start + " ~ " + end;
    }

    public static boolean isEmailRegex(String str) {
        if (str.length() > 100) return false;
        String pattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{1,4}\\b";
        return Pattern.matches(pattern, str);
    }

    public static String convertContactNumber(String contactNumber) {
        if (contactNumber.length() == 8)
            return contactNumber.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
        else if (contactNumber.length() == 12)
            return contactNumber.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");

        return contactNumber.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    }

    public static String convertCurrency(int value) {

        if (value >= 100000000) {
            int eok = value / 100000000;
            int man = (value % 100000000) / 10000;

            if (man > 0)
                return eok + "억 " + String.format("%,d만원", man);
            else
                return eok + "억원";
        } else if (value >= 10000) {
            if ((value % 10000) > 1000)
                return String.format("%,d만 ", (value / 10000)) + String.format("%,d원", (value % 10000));
            else
                return String.format("%,d만원", (value / 10000));
        } else
            return String.format("%,d원", value);
    }

    public static <T extends Enum<T>> List<T> convertToEnumList(String typeStr, Class<T> clazz) {

        if (!StringUtils.hasText(typeStr) || clazz == null) return null;

        List<String> keywords = Arrays.asList(typeStr.split(","));

        return keywords.stream()
                .map(name -> Enum.valueOf(clazz, name))
                .toList();
    }
}
