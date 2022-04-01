package com.denesgarda.ShipGame.util;

public class Printer {
    public static String dot(String left, String right, int length) {
        String s = left;
        for (int i = 0; i < length; i++) {
            s += ".";
        }
        s = s.substring(0, s.length() - (left.length()));
        s = s.substring(0, s.length() - (right.length()));
        s += right;
        return s;
    }
}
