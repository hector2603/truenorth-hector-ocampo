package com.truenorth.truenorth.application.exception;

public enum ErrorCode {
    EC0001("Error: Email is already in use!"),
    EC0002("don't have permission to update information for another user"),
    EC0003("user not found"),
    EC0004("There was an error, please contact the administrator."),
    EC0005("The credentials are incorrect or the user is not active."),
    EC0006("Operation non found"),
    EC0007("your balance is no enough"),
    EC0008("for this operations is mandatory both operators"),
    EC0009("for this operations is mandatory one operators");

    public final String message;
    ErrorCode(String message) {
        this.message = message;
    }

}
