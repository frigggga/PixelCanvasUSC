package com.youzhi.canvas.bean;

public class LoginResult {
    private String message;
    private String token;
    private String username;
    private String email;
    private String result;
    public LoginResult() {
        this.message = null;
        this.token = null;
        this.username = null;
        this.email = null;
        this.result = null;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

}
