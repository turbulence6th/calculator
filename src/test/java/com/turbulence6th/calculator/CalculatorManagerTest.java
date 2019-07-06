package com.turbulence6th.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatorManagerTest {

    private CalculatorManager calculatorManager;

    @Before
    public void setUp() {
        calculatorManager = new CalculatorManager();
    }

    @Test
    public void one() {
        String exp = "3 * 5";
        int result = calculatorManager.calculate(exp);
        assertThat(result, equalTo(15));
    }

    @Test
    public void two() {
        String exp = "(3 + 5) * 4";
        int result = calculatorManager.calculate(exp);
        assertThat(result, equalTo(32));
    }

    @Test
    public void three() {
        String exp = "((2 + 4) * (4 ^ 2 - 1)) + 1";
        int result = calculatorManager.calculate(exp);
        assertThat(result, equalTo(91));
    }

    @Test
    public void four() {
        String exp = "1 + 2 ^ 4 * 3";
        int result = calculatorManager.calculate(exp);
        assertThat(result, equalTo(49));
    }
}
