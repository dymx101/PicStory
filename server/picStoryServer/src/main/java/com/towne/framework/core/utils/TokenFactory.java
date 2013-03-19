package com.towne.framework.core.utils;

public class TokenFactory {
    public static String getUserToken() {
        return java.util.UUID.randomUUID().toString();
    }
}
