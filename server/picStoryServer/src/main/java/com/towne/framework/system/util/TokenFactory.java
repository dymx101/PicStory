package com.towne.framework.system.util;

public class TokenFactory {
    public static String getUserToken() {
        return java.util.UUID.randomUUID().toString();
    }
}
