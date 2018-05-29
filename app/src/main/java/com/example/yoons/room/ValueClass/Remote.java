package com.example.yoons.room.ValueClass;

public class Remote {
    private int uniqueValue;
    private int horizontal;
    private int vertical;
    private int menu;
    private int mode;
    private int ok;
    private int power;
    private int source;

    public Remote() {}

    public Remote(int uniqueValue, int horizontal, int vertical, int menu, int mode, int ok, int power, int source) {
        this.uniqueValue = uniqueValue;
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.menu = menu;
        this.mode = mode;
        this.ok = ok;
        this.power = power;
        this.source = source;
    }

    public int getUniqueValue() {
        return uniqueValue;
    }

    public void setUniqueValue(int uniqueValue) {
        this.uniqueValue = uniqueValue;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
