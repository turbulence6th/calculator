package com.turbulence6th.calculator;

public class Numeric implements Symbol {

    private int value;

    public Numeric(int value) {
        this.value = value;
    }

    @Override
    public String getSign() {
        return String.valueOf(value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
