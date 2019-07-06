package com.turbulence6th.calculator;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalculatorManager {

    private static final String SYNTAX_ERROR = "Syntax Error";

    public int calculate(String exp) {
        List<Symbol> symbolList = getSymbolList(exp);
        List<Symbol> postfixList = getPostfixList(symbolList);
        return calculate(postfixList);
    }

    private List<Symbol> getSymbolList(String exp) {
        List<Symbol> symbolList = new ArrayList<>();
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (Character.isWhitespace(c)) {

            } else if (Character.isDigit(c)) {
                addDigit(symbolList, c);
            } else {
                addSign(symbolList, c);
            }
        }

        return symbolList;
    }

    private void addDigit(List<Symbol> symbolList, char c) {
        int value = c - '0';
        if (!symbolList.isEmpty()) {
            Symbol last = symbolList.get(symbolList.size() - 1);
            if (last instanceof Numeric) {
                Numeric numeric = (Numeric) last;
                numeric.setValue(numeric.getValue() * 10 + value);
            } else {
                symbolList.add(new Numeric(value));
            }
        } else {
            symbolList.add(new Numeric(value));
        }
    }

    private void addSign(List<Symbol> symbolList, char c) {
        String str = String.valueOf(c);
        Operator operator = Operator.getBySign(str);
        if (operator != null) {
            addOperator(symbolList, operator);
        } else {
            addParenthesis(symbolList, str);
        }
    }

    private void addOperator(List<Symbol> symbolList, Operator operator) {
        if (symbolList.isEmpty()) {
            throw new CalculatorException(SYNTAX_ERROR);
        }

        Symbol last = symbolList.get(symbolList.size() - 1);
        if (last instanceof Numeric || last == Parenthesis.CLOSE_PARENTHESIS) {
            symbolList.add(operator);
        } else {
            throw new CalculatorException(SYNTAX_ERROR);
        }
    }

    private void addParenthesis(List<Symbol> symbolList, String str) {
        Parenthesis parenthesis = Parenthesis.getBySign(str);
        if (parenthesis != null) {
            symbolList.add(parenthesis);
        } else {
            throw new CalculatorException(SYNTAX_ERROR);
        }
    }

    private List<Symbol> getPostfixList(List<Symbol> symbolList) {
        List<Symbol> postfixList = new LinkedList<>();
        Deque<Symbol> stack = new ArrayDeque<>();
        for (Symbol symbol : symbolList) {
            if (symbol == Parenthesis.OPEN_PARENTHESIS) {
                stack.push(symbol);
            } else if (symbol == Parenthesis.CLOSE_PARENTHESIS) {
                while (true) {
                    if (stack.isEmpty()) {
                        throw new CalculatorException(SYNTAX_ERROR);
                    }

                    Symbol top = stack.pop();
                    if (top == Parenthesis.OPEN_PARENTHESIS) {
                        break;
                    }

                    postfixList.add(top);
                }
            } else if (symbol instanceof Operator) {
                Operator operator = (Operator) symbol;
                while (true) {
                    Symbol peek = stack.peek();
                    if (peek instanceof Operator) {
                        Operator peekOperator = (Operator) peek;
                        if (peekOperator.getPrecision() >= operator.getPrecision()) {
                            postfixList.add(stack.pop());
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }

                stack.push(operator);
            } else if (symbol instanceof Numeric) {
                postfixList.add(symbol);
            }
        }

        while (!stack.isEmpty()) {
            postfixList.add(stack.pop());
        }

        return postfixList;
    }

    private int calculate(List<Symbol> postfixList) {
        Deque<Numeric> stack = new ArrayDeque<>();
        for (Symbol symbol : postfixList) {
            if (symbol instanceof Numeric) {
                stack.push((Numeric) symbol);
            } else if (symbol instanceof Operator) {
                Operator operator = (Operator) symbol;
                Numeric rhs = stack.pop();
                Numeric lhs = stack.peek();

                if (lhs == null) {
                    throw new CalculatorException(SYNTAX_ERROR);
                } else if (operator == Operator.ADDITION) {
                    lhs.setValue(lhs.getValue() + rhs.getValue());
                } else if (operator == Operator.SUBTRACTION) {
                    lhs.setValue(lhs.getValue() - rhs.getValue());
                } else if (operator == Operator.MULTIPLICATION) {
                    lhs.setValue(lhs.getValue() * rhs.getValue());
                } else if (operator == Operator.DIVISION) {
                    lhs.setValue(lhs.getValue() / rhs.getValue());
                } else if (operator == Operator.MODULUS) {
                    lhs.setValue(lhs.getValue() % rhs.getValue());
                } else if (operator == Operator.EXPONENTIAL) {
                    lhs.setValue((int) Math.pow(lhs.getValue(), rhs.getValue()));
                }
            } else {
                throw new CalculatorException(SYNTAX_ERROR);
            }
        }

        if (stack.size() != 1) {
            throw new CalculatorException(SYNTAX_ERROR);
        }

        return stack.pop().getValue();
    }
}
