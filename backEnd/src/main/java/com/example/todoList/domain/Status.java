package com.example.todoList.domain;

public enum Status{
    pending(0),
    finish(1);

    Status(Integer number) {
        this.number = number;
    }

    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
