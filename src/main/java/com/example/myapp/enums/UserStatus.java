package com.example.myapp.enums;

public enum UserStatus {
    USER_CREATE("USER_CREATE"),
    ERROR_USER_LOGIN_EXIST("ERROR_USER_LOGIN_EXIST"),
    ERROR_CREATE_USER("ERROR_CREATE_USER"),
    ERROR_IN_LOGIN_PASSWORD("ERROR_IN_LOGIN_PASSWORD"),
    ERROR_TOKEN_TIMEOVER("ERROR_TOKEN_TIMEOVER"),
    ERROR_TOKEN_SIGNATURE("ERROR_TOKEN_SIGNATURE"),
    ERROR_TOKEN_INCORRECT_OR_NULL("ERROR_TOKEN_INCORRECT_OR_NULL"),
    ERROR_FALSE_TOKEN("ERROR_FALSE_TOKEN");

    private final String text;

    UserStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
