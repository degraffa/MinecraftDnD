package com.degraffa.mcdnd.util;

public class StringUtil {
    public static boolean isNumeric(String s) {
        boolean isNumeric = true;
        for (char c : s.toCharArray()) {
            if (Character.isAlphabetic(c)) isNumeric = false;
        }

        return isNumeric;
    }
}
