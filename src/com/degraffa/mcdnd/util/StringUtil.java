package com.degraffa.mcdnd.util;

import java.util.ArrayList;

public class StringUtil {
    public static boolean isNumeric(String s) {
        boolean isNumeric = true;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) isNumeric = false;
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

    public static ArrayList<String> getSingleStringArray(String s) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(s);
        return strings;
    }
}
