package com.example.todoList.domain;

public class ErrorResponse {
    private String message;
    private String exceptionType;

    public ErrorResponse(String message, String exceptionType) {
        this.message = message;
        this.exceptionType = exceptionType;
    }

    public String getMessage() {
        return message;
    }


    public String getExceptionType() {
        return exceptionType;
    }

}
