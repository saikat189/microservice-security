package com.practice.auth.constant;

public class SecurityConstants {
    public static final String SECRET = "j3H5Ld5nYm%%$ULy6xwpOgfSH++NgKXq20vpfd+8=t";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}