package org.karthik.skillstore.querybuilder;

public class CheckDataType {
    public static boolean isLong(Object value) {
        return value instanceof Long;
    }

    public static boolean isInt(Object value) {
        return value instanceof Integer;
    }

    public static boolean isFloat(Object value) {
       return value instanceof Float;
    }
}
