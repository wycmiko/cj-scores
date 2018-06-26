package com.cj.shop.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author yuchuanWeng(wycmiko @ foxmail.com)
 * @date 2018/4/4
 * @since 1.0
 */
public class ValidatorUtil {

    /**
     * 判断非空集合 如果为null 返回[]
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> checkNotEmptyList(List<T> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public static Integer checkInteger(Integer param) {
        if (param == null) {
            param = 0;
        }
        return param;
    }

    public static Long checkLong(Long param) {
        if (param == null) {
            param = 0L;
        }
        return param;
    }


    public static String filterUtf8mb4(String str) {
        final int LAST_BMP = 0xFFFF;
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            int codePoint = str.codePointAt(i);
            if (codePoint < LAST_BMP) {
                sb.appendCodePoint(codePoint);
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    // 过滤特殊字符
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字 // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("\\x").trim();
    }


}
