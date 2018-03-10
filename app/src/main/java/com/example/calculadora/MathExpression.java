package com.example.calculadora;

import android.support.annotation.NonNull;

/**
 * Created by vicch on 09/03/2018.
 */

public class MathExpression implements Expression {

    static final String ADDITION = "+";
    static final String SUBTRACTION = "-";
    static final String MULTIPLICATION = "x";
    static final String DIVISION = "/";
    static final String EXPONENTIATION = "^";
    static final String SQUARE_ROOT = "r";
    static final String SQUARE_ROOT_SCREEN = "sqrt";
    static final String FACTORIAL = "f";
    static final String FACTORIAL_SCREEN = "fact";

    @Override
    public String read(@NonNull String expression) {
        return expression.replace(SQUARE_ROOT_SCREEN, SQUARE_ROOT)
                .replace(FACTORIAL_SCREEN, FACTORIAL)
                .replaceAll("\\s", "");
    }

    @Override
    public String write(@NonNull String expression) {
        return expression.replaceAll("(?<=[-fr+x/^)])|(?=[-fr+x/^(])", "$0 ")
                .replace(SQUARE_ROOT,SQUARE_ROOT_SCREEN)
                .replace(FACTORIAL,FACTORIAL_SCREEN);
    }

    @Override
    public String addSymbol(@NonNull String expression, @NonNull String symbol) throws ExpressionException{
        throwsIfSymbolIsInvalid(symbol);
        return write(read(expression).concat(symbol));
    }

    @Override
    public String removeSymbol(@NonNull String expression) {
        if(expression==null || expression.length()==0) return "";
        expression = read(expression);
        return write(removeLastCharacter(expression));
    }

    @Override
    public String replaceSymbol(@NonNull String expression, @NonNull String symbol) throws ExpressionException{
        if(expression==null) return "";
        throwsIfSymbolIsInvalid(symbol);
        expression = removeLastCharacter(expression);
        return write(expression.concat(symbol));

    }

    @Override
    public String[] tokenize(@NonNull String expression) {
        return expression.split("(?<=[fr+x/^])|(?=[-fr+x/^])");
    }

    private void throwsIfSymbolIsInvalid(String expression) throws ExpressionException {
        for (char symbol : expression.toCharArray()) {
            if (isSymbolInvalid(String.valueOf(symbol))) {
                throw new ExpressionException( String.format("symbol %s is invalid", symbol)); }
        }
    }

    private boolean isSymbolInvalid(String symbol) {
        if(symbol==null || symbol.length()==0) return true;
        return !symbol.matches("([0-9]|[-+x/]|[.]|[()^fr])");
    }

    private String removeLastCharacter(@NonNull String expression){
        int START_INDEX = 0;
        int END_INDEX = expression.length() - 1;
        return expression.substring(START_INDEX, END_INDEX);
    }

}
