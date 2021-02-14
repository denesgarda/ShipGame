package sample;

import java.util.Arrays;

public class ArrayModification {
    public static String[] appendString(String[] array, String value) {
        String[] result = Arrays.copyOf(array, array.length + 1);
        result[result.length - 1] = value;
        return result;
    }
    public static String[] toStringArray(String string) {
        String[] result = {};
        String e = "";
        for(int i = 0; i < string.length(); i++) {
            switch(string.charAt(i)) {
                case '[':
                    e = "";
                    break;
                case ']':
                    result = appendString(result, e);
                    break;
                case ',':
                    result = appendString(result, e);
                    e = "";
                    while(string.charAt(i + 1) == ' ') {
                        i++;
                    }
                    break;
                default:
                    e += string.charAt(i);
                    break;
            }
        }
        return result;
    }
}
