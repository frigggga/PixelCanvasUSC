package com.youzhi.canvas.bean;

public class ColorInfo {
    public String x_coordinate;
    public String y_coordinate;
    public String color;
    public String email;

    public ColorInfo() {
        this.email = null;
        this.x_coordinate = null;
        this.y_coordinate = null;
        this.color = null;
    }

    public String getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(String x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public String getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(String y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
