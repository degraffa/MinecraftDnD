package com.degraffa.mcdnd.util;

import java.util.ArrayList;

public class StringUtil {
    // returns true if an entire string is numeric, returns false otherwise
    public static boolean isNumeric(String s) {
        boolean isNumeric = true;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) isNumeric = false;
        }

        return isNumeric;
    }

    // transforms an object array to a string array
    public static String[] objectArrayToStringArray(Object[] objArr) {
        String[] stringArr = new String[objArr.length];

        for (int i = 0; i < objArr.length; i++) {
            stringArr[i] = objArr[i].toString();
        }

        return stringArr;
    }

    // returns an array with a single given element
    public static ArrayList<String> getSingleStringArray(String s) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(s);
        return strings;
    }

    // returns the index of the next non-numeric character in string [s] after index [idx]
    public static int getNextLetterIdx(String s, int idx) {
        int nextLetterIdx = s.length();

        for (int i = idx; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isAlphabetic(c)) {
                nextLetterIdx = i;
                break;
            }
        }

        return nextLetterIdx;
    }
}
