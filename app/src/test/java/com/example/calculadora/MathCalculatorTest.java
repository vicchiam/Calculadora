package com.example.calculadora;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static com.google.common.truth.Truth.assertThat;
import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vicch on 11/03/2018.
 */

@RunWith(JUnitParamsRunner.class)
public class MathCalculatorTest {

    private MathCalculator calculator;
    @Mock
    private Expression mockedExpression;
    @Mock
    private MathOperation mockedOperation;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        //mockedExpression = Mockito.mock(Expression.class);
        //mockedOperation = Mockito.mock(MathOperation.class);
        calculator = new MathCalculator(mockedExpression, mockedOperation);
    }

    //Crear mock

    @Test
    @Parameters(method = "addSymbolData")
    public void addSymbolShouldCallAddSymbolInExpression( String to, String symbol) {
        //Arrange
        MockExpression mockedExpression = new MockExpression();
        MathOperation dummyOperation = null;
        MathCalculator calculator = new MathCalculator(mockedExpression, dummyOperation);
        //Act
        calculator.addSymbol(to, symbol);
        //Assert
        assertThat(mockedExpression.symbolAdded).isTrue();
        assertThat(mockedExpression.symbolReplaced).isFalse();
        assertThat(mockedExpression.symbolRemove).isFalse();
    }

    private Object[] addSymbolData() {
        return $(
                $("", "-"),
                $("2", "+"),
                $("5", "."),
                $("4.3", "f")
        );
    }

    @Test
    @Parameters(method = "replaceSymbolData")
    public void addSymbolShouldCallReplaceSymbolInExpression( String to, String symbol) {
        //Arrange
        MockExpression mockedExpression = new MockExpression();
        MathOperation dummyOperation = null;
        MathCalculator calculator = new MathCalculator(mockedExpression, dummyOperation);
        //Act
        calculator.addSymbol(to, symbol);
        //Assert
        assertThat(mockedExpression.symbolAdded).isFalse();
        assertThat(mockedExpression.symbolReplaced).isTrue();
        assertThat(mockedExpression.symbolRemove).isFalse();
    }

    private Object[] replaceSymbolData() {
        return $(
                $("-", "+"),
                $("-5+", "x"),
                $("3x4/", "f"),
                $("3sqrt", "x"),
                $("3.", "/")
        );
    }

    @Test
    @Parameters(method = "removeSymbolData")
    public void removeSymbolShouldCallRemoveSymbolInExpression( String expression) {
        //Arrange
        MockExpression mockedExpression = new MockExpression();
        MathOperation dummyOperation = null;
        MathCalculator calculator = new MathCalculator(mockedExpression, dummyOperation);
        //Act
        calculator.removeSymbol(expression);
        //Assert
        assertThat(mockedExpression.symbolAdded).isFalse();
        assertThat(mockedExpression.symbolReplaced).isFalse();
        assertThat(mockedExpression.symbolRemove).isTrue();
    }

    private Object[] removeSymbolData() {
        return $(
                $("-"),
                $("-5+"),
                $("3x4/"),
                $("3sqrt"),
                $("3.")
        );
    }

    //Test doble con mokito

    @Test
    @Parameters(method = "addSymbolData")
    public void addSymbolShouldCallAddSymbol(String to, String symbol) {
        calculator.addSymbol(to, symbol);
        verify(mockedExpression, times(1)).addSymbol(anyString(), anyString());
        verify(mockedExpression, times(0)).replaceSymbol(anyString(), anyString());
    }

    @Test
    @Parameters(method = "replaceSymbolData")
    public void addSymbolShouldCallReplaceSymbol(String to, String symbol) {
        calculator.addSymbol(to, symbol);
        verify(mockedExpression, times(1)).replaceSymbol(anyString(), anyString());
        verify(mockedExpression, times(0)).addSymbol(anyString(), anyString());
    }

    @Test
    @Parameters(method = "resolveData")
    public void resolveShouldReturnExpectedExpression( String from, String[] tokens, String expected) {
        MathOperation operation = new MathOperation();
        MathCalculator calculator = new MathCalculator(mockedExpression, operation);
        when(mockedExpression.tokenize(from)).thenReturn(tokens);
        assertThat(calculator.resolve(from)).isEqualTo(expected);
    }

    private Object[] resolveData() {
        return $(
                $("", new String[] {""}, ""),
                $("-", new String[] {"-"}, "0"),
                $("-5", new String[] {"-5"}, "-5"),
                $("3.4", new String[] {"3.4"}, "3.4"),
                $("3-2", new String[] {"3", "-2"}, "1"),
                $("3+2", new String[] {"3", "+", "2"}, "5"),
                $("f3", new String[] {"f", "3"}, "6"),
                $("2f3", new String[] {"2", "f", "3"}, "12"),
                $("9/r9", new String[] {"9", "/", "r", "9"}, "3"),
                $("3^f3", new String[]{"3", "^", "f", "3"}, "729"),
                $("3--4", new String[] {"3", "-", "-4"}, "7")
        );
    }

    @Test
    @Parameters(method = "resolveAdditionData")
    public void resolveShouldCallXTimesAdditionMethod( String from, String[] tokens, int times) {
        when(mockedExpression.tokenize(from)).thenReturn(tokens);
        calculator.resolve(from);
        verify(mockedOperation,times(times)).addition(anyDouble(),anyDouble());
    }
    private Object[] resolveAdditionData() {
        return $(
                $("2+2", new String[] {"2", "+", "2"}, 2),
                $("", new String[] {""}, 0),
                $("2", new String[] {"2"}, 1),
                $("2+2x3-5", new String[] {"2", "+", "2", "x", "3", "-5"},3)
        );
    }

    //Test para math calculator

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