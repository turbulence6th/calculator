package com.turbulence6th.calculator;

import java.util.Arrays;

public enum Parenthesis implements Symbol {

    OPEN_PARENTHESIS("("),
    CLOSE_PARENTHESIS(")");

    private String sign;

    Parenthesis(String sign) {
        this.sign = sign;
    }

    public static Parenthesis getBySign(String sign) {
        return Arrays.stream(values())
                .filter(p -> p.getSign().equals(sign))
                .findAny()
                .orElse(null);
    }

    @Override
    public String getSign() {
        return sign;
    }
}
