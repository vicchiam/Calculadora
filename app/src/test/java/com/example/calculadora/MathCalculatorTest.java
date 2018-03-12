package com.example.calculadora;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

/**
 * Created by vicch on 11/03/2018.
 */

@RunWith(JUnitParamsRunner.class)
public class MathCalculatorTest {

    private MathCalculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new MathCalculator(new MathExpression(), new MathOperation());
    }

    @Parameters(method = "containsParenthesisExpressionData")
    @Test
    public void containsParenthesisShouldReturnExpectedExpression( String expression, Boolean expected) {
        Boolean result = calculator.containsParenthesis(expression);
        assertThat(result).isEqualTo(expected);
    }

    private Object[] containsParenthesisExpressionData() {
        return $(
                $("", false),
                $("r 21", false),
                $("(", true),
                $("r ( 21", true),
                $("(3 + 2)", true),
                $("+ 2)", false)
        );
    }


    @Parameters(method = "getParenthesisStartIndexData")
    @Test
    public void getParenthesisStartIndexShouldReturnExpectedExpression( String expression, int expected) {
        int result = calculator.getParenthesisStartIndex(expression);
        assertThat(result).isEqualTo(expected);
    }

    private Object[] getParenthesisStartIndexData() {
        return $(
                $("(", 0),
                $("r ( 21", 2),
                $("(3 + 2)", 0),
                $("2 ((+ 2)", 3)
        );
    }

    @Parameters(method = "setInvalidIndexStartInput")
    @Test(expected = CalculatorException.class)
    public void setInvalidIndexStartShouldThrowWhenSymbolIsInvalid(String expression) {
        calculator.getParenthesisStartIndex(expression);
    }

    private Object[] setInvalidIndexStartInput() {
        return $(
                $(""),
                $("+2)"),
                $("12 x 54"),
                $("r 25")
        );
    }


    @Parameters(method = "getParenthesisEndIndexData")
    @Test
    public void getParenthesisEndIndexShouldReturnExpectedExpression( String expression, int expected) {
        int result = calculator.getParenthesisEndIndex(expression);
        assertThat(result).isEqualTo(expected);
    }

    private Object[] getParenthesisEndIndexData() {
        return $(
                $("", 0),
                $("r 21", 4),
                $("(", 1),
                $("r ( 21 )", 8),
                $("(3 + 2)", 7),
                $("+ 2 )", 5),
                $("(+ 2))", 5)
        );
    }

    @Parameters(method = "setInvalidIndexEndInput")
    @Test(expected = CalculatorException.class)
    public void setInvalidIndexEndShouldThrowWhenSymbolIsInvalid(String expression) {
        calculator.getParenthesisStartIndex(expression);
    }

    private Object[] setInvalidIndexEndInput() {
        return $(
                $(""),
                $("+2)"),
                $("12 x 54"),
                $("r 25")
        );
    }


    @Parameters(method = "getRightmostParenthesisData")
    @Test
    public void getRightmostParenthesisShouldReturnExpectedExpression( String expression, String expected) {
        String result = calculator.getRightmostParenthesis(expression);
        assertThat(result).isEqualTo(expected);
    }

    private Object[] getRightmostParenthesisData() {
        return $(
                $("(", "("),
                $("r ( 21 )", "( 21 )"),
                $("(3 + 2)", "(3 + 2)"),
                $("(+ 2))", "(+ 2)")
        );
    }

    @Parameters(method = "setInvalidRightmostParenthesisInput")
    @Test(expected = CalculatorException.class)
    public void setInvalidRightmostParenthesisShouldThrowWhenSymbolIsInvalid(String expression) {
        calculator.getParenthesisStartIndex(expression);
    }

    private Object[] setInvalidRightmostParenthesisInput() {
        return $(
                $(""),
                $("+2)"),
                $("12 x 54"),
                $("r 25")
        );
    }


    @Parameters(method = "getParenthesisExpressionData")
    @Test
    public void getParenthesisExpressionShouldReturnExpectedExpression( String expression, String expected) {
        String result = calculator.getParenthesisExpression(expression);
        assertThat(result).isEqualTo(expected);
    }

    private Object[] getParenthesisExpressionData() {
        return $(
                $("(", ""),
                $("r ( 21 )", "21"),
                $("(3 + 2)", "3 + 2"),
                $("((+ 2))", "+ 2"),
                $("(3 x (+ 2))", "+ 2")
        );
    }

    @Parameters(method = "setInvalidParenthesisExpressionInput")
    @Test(expected = CalculatorException.class)
    public void setInvalidParenthesisExpressionShouldThrowWhenSymbolIsInvalid(String expression) {
        calculator.getParenthesisStartIndex(expression);
    }

    private Object[] setInvalidParenthesisExpressionInput() {
        return $(
                $(""),
                $("+2)"),
                $("12 x 54"),
                $("r 25")
        );
    }


    @Parameters(method = "replaceParenthesisExpressionData")
    @Test
    public void replaceParenthesisExpressionShouldReturnExpectedExpression( String from, String with, String expected) {
        String result = calculator.replaceParenthesis(from, with);
        assertThat(result).isEqualTo(expected);
    }

    private Object[] replaceParenthesisExpressionData() {
        return $(
                $("1 + 24589", "(3 + 4)","1 + 24589"),
                $("2x(1 + 2)", "(3 + 4)","2x(3 + 4)")
        );
    }

    @Parameters(method = "setInvalidReplaceParenthesisExpressionInput")
    @Test(expected = CalculatorException.class)
    public void setInvalidreplaceParenthesisExpressionShouldThrowWhenSymbolIsInvalid(String expression) {
        calculator.getParenthesisStartIndex(expression);
    }

    private Object[] setInvalidReplaceParenthesisExpressionInput() {
        return $(
                $(""),
                $("+2)"),
                $("12 x 54"),
                $("r 25")
        );
    }


}