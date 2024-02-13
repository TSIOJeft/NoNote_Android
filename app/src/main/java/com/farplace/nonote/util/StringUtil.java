package com.farplace.nonote.util;

import org.apache.commons.text.StringEscapeUtils;

public class StringUtil {
    public static String escape(String content) {

        return StringEscapeUtils.escapeJava(content);
    }
}
