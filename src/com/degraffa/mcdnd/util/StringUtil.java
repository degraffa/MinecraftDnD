package com.degraffa.mcdnd.util;

public class StringUtil {
    public static boolean isNumeric(String s) {
        boolean isNumeric = true;
        for (char c : s.toCharArray()) {
            if (Character.isAlphabetic(c)) isNumeric = false;
        }

        return isNumeric;
    }

    public static String[] objectArrayToStringArray(Object[] objArr) {
        String[] stringArr = new String[objArr.length];

        for (int i = 0; i < objArr.length; i++) {
            stringArr[i] = objArr[i].toString();
        }

        return stringArr;
    }
}
