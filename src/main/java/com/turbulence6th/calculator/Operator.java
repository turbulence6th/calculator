package com.turbulence6th.calculator;

import java.util.Arrays;

public enum Operator implements Symbol {

    ADDITION("+", 0),
    SUBTRACTION("-", 0),
    MULTIPLICATION("*", 1),
    DIVISION("/", 1),
    MODULUS("%", 1),
    EXPONENTIAL("^", 2);

    private String sign;
    private int precision;

    Operator(String sign, int precision) {
        this.sign = sign;
        this.precision = precision;
    }

    public static Operator getBySign(String sign) {
        return Arrays.stream(values())
                .filter(s -> s.getSign().equals(sign))
                .findAny()
                .orElse(null);
    }

    @Override
    public String getSign() {
        return sign;
    }

    public int getPrecision() {
        return precision;
    }
}
