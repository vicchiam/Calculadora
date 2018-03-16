package com.example.calculadora;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by vicch on 11/03/2018.
 */

public class MathCalculator implements Calculator {

    private final String ADDITION = "+";
    private final String SUBTRACTION = "-";
    private final String MULTIPLICATION = "x";
    private final String DIVISION = "/";
    private final String EXPONENTIATION = "^";
    private final String SQUARE_ROOT = "r";
    private final String FACTORIAL = "f";
    private final String SQUARE_ROOT_SCREEN = "sqrt";
    private final String FACTORIAL_SCREEN = "fact";
    private final String PARENTHESIS_START = "(";
    private final String PARENTHESIS_END = ")";
    private final String DOT = ".";
    private final String NONE = "none";
    private final String EMPTY_STRING = "";
    private Expression expression;
    private MathOperation operation;

    public MathCalculator(Expression expression, MathOperation operation) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public String addSymbol(@NonNull String to, @NonNull String symbol) {
        if(isAnOperator(symbol)) {
            symbol = isAnUnaryOperator(symbol) ? symbol.concat(PARENTHESIS_START) : symbol;
            if (endsWithOperator(to)) {
                return expression.replaceSymbol(to, symbol);
            }
            else {
                return expression.addSymbol(to, symbol);
            }
        }
        return expression.addSymbol(to, symbol);
    }

    @Override
    public String removeSymbol(@NonNull String from) {
        return expression.removeSymbol(from);
    }

    @Override
    public String calculate(@NonNull String from) throws OperationException, ExpressionException {
        from = expression.read(from);
        while (containsParenthesis(from)) {
            String parenthesis = getParenthesisExpression(from);
            from = replaceParenthesis(from, resolve(parenthesis));
        }
        return resolve(from);
    }

    @VisibleForTesting
    boolean containsParenthesis(String expression) {
        return expression.contains(PARENTHESIS_START);
    }

    @VisibleForTesting
    String getParenthesisExpression(String from) {
        return removeParenthesis(getRightmostParenthesis(from));
    }

    @VisibleForTesting
    String getRightmostParenthesis(String from) {
        int START_INDEX = getParenthesisStartIndex(from);
        int END_INDEX = getParenthesisEndIndex(from);
        return from.substring(START_INDEX, END_INDEX);
    }

    @VisibleForTesting
    int getParenthesisStartIndex(String from) throws CalculatorException {
        int index = from.lastIndexOf(PARENTHESIS_START);
        if(index==-1) throw new CalculatorException("No contains parenthesis start");
        return index;
    }

    @VisibleForTesting
    int getParenthesisEndIndex(String from) {
        int index=-1;
        try {
            index = from.indexOf(PARENTHESIS_END, getParenthesisStartIndex(from));
        }
        catch (CalculatorException e){}
        if (index == -1) {
            return from.length();
        }
        return ++index;

    }

    private String removeParenthesis(String from) {
        return from.replace(PARENTHESIS_START, EMPTY_STRING)
                .replace(PARENTHESIS_END, EMPTY_STRING).trim();
    }

    @VisibleForTesting
    String replaceParenthesis(String from, String with) {
         try {
             with = addOperators(from, with);
             return new StringBuilder(from)
                    .replace(getParenthesisStartIndex(from), getParenthesisEndIndex(from), with)
                    .toString();
        }
        catch (CalculatorException e){
            return from;
        }

    }

    private String addOperators(String expression, String to) {
        to = addOperatorBeforeParenthesis(expression, to);
        to = addOperatorAfterParenthesis(expression, to);
        return to;
    }

    private String addOperatorBeforeParenthesis(String from, String to) {
        try {
            String previousCharacter = String.valueOf(from.charAt( getParenthesisStartIndex(from) - 1));
            if (!isAnOperator(previousCharacter) && !previousCharacter.equals(PARENTHESIS_START))
                return MULTIPLICATION.concat(to);
        }
        catch (IndexOutOfBoundsException exception) {
            return to;
        }
        return to;
    }

    private String addOperatorAfterParenthesis(String from, String to) {
        try { String nextCharacter = String.valueOf(from.charAt( getParenthesisEndIndex(from)));
            if (!isAnOperator(nextCharacter) && !nextCharacter.equals(PARENTHESIS_END))
                return to.concat(MULTIPLICATION);
        }
        catch (IndexOutOfBoundsException exception) {
            return to;
        }
        return to;
    }

    @VisibleForTesting
    String resolve(String from) throws OperationException, ExpressionException {
        if(from.isEmpty()) return "";
        double result = 0;
        String unaryOperator =NONE;
        String binaryOperator =NONE;
        String[] symbols = expression.tokenize(from);
        for (String symbol : symbols) {
            if (symbol.isEmpty())
                continue;
            if (isAnUnaryOperator(symbol)) {
                unaryOperator = symbol;
                if (binaryOperator.equals(NONE) && result != 0) binaryOperator = MULTIPLICATION;
            }
            else if (isABinaryOperator(symbol)) {
                binaryOperator = symbol;
            }
            else {
                if(unaryOperator.equals(NONE) && binaryOperator.equals(NONE))
                    result = operation.addition(result, Double.parseDouble( symbol));
                if (!unaryOperator.equals(NONE)) {
                    symbol = String.valueOf(calculateUnaryOperation(Double .parseDouble(symbol), unaryOperator));
                    result = binaryOperator.equals(NONE) ? Double.parseDouble(symbol) : result;
                    unaryOperator =NONE;
                }
                if(!binaryOperator.equals(NONE)) {
                    result = calculateBinaryOperation(result, Double.parseDouble(symbol), binaryOperator);
                    binaryOperator =NONE;
                }
            }
        }
        return getFormattedNumber(result);
    }

    private boolean isAnOperator(String symbol) {
        return isABinaryOperator(symbol) || isAnUnaryOperator(symbol);
    }

    private boolean isABinaryOperator(String symbol) {
        return symbol.equals(ADDITION) ||
                symbol.equals(SUBTRACTION) ||
                symbol.equals(MULTIPLICATION) ||
                symbol.equals(DIVISION) ||
                symbol.equals(EXPONENTIATION);
    }

    private boolean isAnUnaryOperator(String symbol) {
        return symbol.equals(SQUARE_ROOT) ||
                symbol.equals(FACTORIAL);
    }

    private boolean endsWithOperator(String expression) {
        expression = expression.trim();
        return expression.endsWith(ADDITION) ||
                expression.endsWith(SUBTRACTION) ||
                expression.endsWith(MULTIPLICATION) ||
                expression.endsWith(DIVISION) ||
                expression.endsWith(EXPONENTIATION) ||
                expression.endsWith(SQUARE_ROOT_SCREEN) ||
                expression.endsWith(FACTORIAL_SCREEN) ||
                expression.endsWith(DOT);
    }

    private double calculateUnaryOperation(double operand, String operator) {
        double result = 0;
        switch (operator) {
            case SQUARE_ROOT:
                result = operation.squareRoot(operand);
                break;
            case FACTORIAL:
                result = operation.factorial(operand);
                break;
        }
        return result;
    }

    private double calculateBinaryOperation(double accumulated, double value, String operator) {
        double result = 0;
        switch (operator) {
            case ADDITION:
                result = operation.addition(accumulated, value);
                break;
            case SUBTRACTION:
                result = operation.subtract(accumulated, value);
                break;
            case MULTIPLICATION:
                result = operation.multiply(accumulated, value);
                break;
            case DIVISION:
                result = operation.divide(accumulated, value);
                break;
            case EXPONENTIATION:
                result = operation.exponentiation(accumulated, value);
                break;
        }
        return accumulated == 0 ? value : result;
    }

    private String getFormattedNumber(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat formatter = new DecimalFormat("0", symbols);
        formatter.setMaximumFractionDigits(8);
        return formatter.format(value);
    }

}
