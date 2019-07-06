package com.turbulence6th.controller;

import com.turbulence6th.calculator.CalculatorException;
import com.turbulence6th.calculator.CalculatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private CalculatorManager calculatorManager;

    @Autowired
    public IndexController(CalculatorManager calculatorManager) {
        this.calculatorManager = calculatorManager;
    }

    @PostMapping("/calculate")
    public String calculate(String exp) {
        try {
            return String.valueOf(calculatorManager.calculate(exp));
        } catch (CalculatorException e) {
            return e.getMessage();
        }
    }
}
