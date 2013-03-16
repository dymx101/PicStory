package com.towne.framework.core.ex;

public class RuntimeExceptionThreadVo {
    private static final ThreadLocal<String> ex = new ThreadLocal<String>();

    public static String getMessage() {
        return ex.get();
    }

    public static void setMessage(String message) {
        ex.set(message);
    }
    
    public static void clearMessage() {
        ex.remove();
    }

}
