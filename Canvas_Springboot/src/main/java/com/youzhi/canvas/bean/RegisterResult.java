package com.youzhi.canvas.bean;

public class RegisterResult {
    private String username;
    private String email;
    private String message;
    private String result;
    public RegisterResult() {
        this.username = null;
        this.email = null;
        this.message = null;
        this.result = null;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
