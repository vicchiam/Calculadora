package com.example.calculadora;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by vicch on 09/03/2018.
 */
@RunWith(JUnitParamsRunner.class)
public class MathExpressionTest {

    private MathExpression expression;

    @Before
    public void setUp() throws Exception {
        expression = new MathExpression();
    }

    @Parameters(method = "readExpressionData")
    @Test
    public void readShouldReturnExpectedExpression( String original, String expected) {
        String result = expression.read(original);
        assertThat(result).isEqualTo(expected); //Librería Truth
    }

    private Object[] readExpressionData() {
        return $(
                $(" ", ""),
                $("", ""),
                $("fact", "f"),
                $("sqrt", "r"),
                $("(3 + 2)", "(3+2)"),
                $("fact (3 + 2) / 3 + 2", "f(3+2)/3+2")
        );
    }

    @Parameters(method = "writeExpressionData")
    @Test
    public void writeShouldReturnExpectedExpression( String input, String output) {
        String result = expression.write(input);
        assertThat(result).isEqualTo(output);
    }

    private Object[] writeExpressionData() {
        return $(
                $("f", " fact "),
                $("r", " sqrt "),
                $("3.2", "3.2"),
                $("3+2.5", "3 + 2.5"),
                $("3-2.5", "3 - 2.5"),
                $("3^2.5", "3 ^ 2.5"),
                $("3x2.5", "3 x 2.5"),
                $("3/2.5", "3 / 2.5"),
                $("3f(2.5", "3 fact (2.5"),
                $("3r(2.5", "3 sqrt (2.5"),
                $("2.5(3", "2.5 (3"),
                $("3)2.5", "3) 2.5")
        );
    }

    @Parameters(method = "addSymbolExpressionData")
    @Test
    public void addSymbolShouldReturnExpectedExpression( String original, String symbol, String expected) {
        String result = expression.addSymbol(original, symbol);
        assertThat(result).isEqualTo(expected);
    }

    private Object[] addSymbolExpressionData() {
        return $(
                $("3", "2", "32"),
                $("3.", "2", "3.2"),
                $("3", "+", "3 + "),
                $("3-", "3", "3 - 3"),
                $("4(", "2", "4 (2"),
                $("3.2)", "/", "3.2) / "),
                $("f(", "7", " fact (7"),
                $("r(3x", "5", " sqrt (3 x 5")
        );
    }

    @Parameters(method = "addInvalidSymbolInput")
    @Test(expected = ExpressionException.class)
    public void addSymbolShouldThrowWhenSymbolIsInvalid(String symbol) {
        expression.addSymbol("", symbol);
    }

    private Object[] addInvalidSymbolInput() {
        return $(
                $("&"),
                $("E"),
                $("e"),
                $("*"),
                $("{"),
                $("X")
        );
    }

}