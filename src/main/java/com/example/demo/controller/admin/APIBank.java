package com.example.demo.controller.admin;

public class APIBank<T> {
    private int status;
    private String message;
    private String error;
    private T transactions;

    public int getCode() {
        return status;
    }

    public void setCode(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getTransactions() {
        return transactions;
    }

    public void setTransactions(T transactions) {
        this.transactions = transactions;
    }
}
