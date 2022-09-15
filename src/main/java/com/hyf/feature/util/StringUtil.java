package com.hyf.feature.util;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author baB_hyf
 * @date 2022/04/29
 */
public class StringUtil {

    public static boolean isBlank(String str) {
        return str == null || "".equals(str) || "".equals(str.trim());
    }

    public static String join(Object[] arr, String split) {
        return join(Arrays.asList(arr), split);
    }

    public static String join(Collection<?> collection, String split) {
        if (collection == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        collection.forEach((c) -> {
            if (sb.length() > 0) {
                sb.append(split);
            }
            sb.append(c);
        });
        return sb.toString();
    }
}
