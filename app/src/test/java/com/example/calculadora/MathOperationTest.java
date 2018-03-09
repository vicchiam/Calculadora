package com.example.calculadora;

import com.example.calculadora.MathOperation;
import com.example.calculadora.OperationException;

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
public class MathOperationTest {

    private MathOperation mathOperation;

    @Before
    public void setUp() {
        mathOperation = new MathOperation();
    }

    /***ADDITION*****************************************************/

    @Parameters(method = "getValidAdditionInput")
    @Test
    public void additionShouldReturnExpectedValueWhenOperandsAreValid (double operand1, double operand2, double expectedValue) {
        //Arrange
        //Act
        double result = mathOperation.addition(operand1, operand2);
        //Assert
        assertEquals(String.format("Resultado %1s ha de ser igual a %2s", result, expectedValue), expectedValue, result, 0.0);
    }

    private Object[] getValidAdditionInput() {
        return new Object[]{
                new Object[]{1, 1, 2},
                new Object[]{2, -2, 0},
                new Object[]{-3.5, -2.7, -6.2}};
    }

    @Parameters(method = "getInvalidAdditionInput")
    @Test(expected = OperationException.class)
    public void additionShouldThrowsWhenValuesAreInvalid(double operand1, double operand2) {
        // ARRANGE
        // ACT
        double result = mathOperation.addition(operand1, operand2);
    }

    private Object[] getInvalidAdditionInput() {
        return new Object[]{
                new Object[]{12, Double.MAX_VALUE},
                new Object[]{Double.POSITIVE_INFINITY, 1},
                new Object[]{-12.3, Double.NEGATIVE_INFINITY},
                new Object[]{Double.NaN, 12},
                new Object[]{Math.pow(2, 1024), -1}
        };
    }

    /***SUBTRACT*****************************************************/

    @Parameters(method = "getValidSubtractInput")
    @Test
    public void subtractShouldReturnExpectedValueWhenOperandsAreValid (double operand1, double operand2, double expectedValue) {
        //Arrange
        //Act
        double result = mathOperation.subtract(operand1, operand2);
        //Assert
        assertEquals(String.format("Resultado %1s ha de ser igual a %2s", result, expectedValue), expectedValue, result, 1E-15);
    }

    private Object[] getValidSubtractInput() {
        return new Object[]{
                new Object[]{1, 1, 0},
                new Object[]{5, 2, 3},
                new Object[]{-5, 2, -7},
                new Object[]{-5, -2, -3},
                new Object[]{5, -2, 7},
                new Object[]{-3.5, -2.7, -0.8},
                new Object[]{Double.MAX_VALUE, 0, Double.MAX_VALUE},
                new Object[]{Double.MAX_VALUE, 1, (Double.MAX_VALUE-1)},
                new Object[]{0, Double.MAX_VALUE, -Double.MAX_VALUE},
                new Object[]{1, Double.MAX_VALUE, (-Double.MAX_VALUE+1)}
        };
    }

    @Parameters(method = "getInvalidSubtractInput")
    @Test(expected = OperationException.class)
    public void subtractShouldThrowsWhenValuesAreInvalid(double operand1, double operand2) {
        // ARRANGE
        // ACT
        double result = mathOperation.subtract(operand1, operand2);
    }

    private Object[] getInvalidSubtractInput() {
        return new Object[]{
                new Object[]{12, -Double.MAX_VALUE},
                new Object[]{-12, Double.MAX_VALUE},
                new Object[]{-Double.MAX_VALUE,1},
                new Object[]{Double.MAX_VALUE,-1},
                new Object[]{Double.POSITIVE_INFINITY, 1},
                new Object[]{-12.3, Double.NEGATIVE_INFINITY},
                new Object[]{Double.NaN, 12},
                new Object[]{Math.pow(2, 1024), -1}
        };
    }

    /***MULTIPLY*****************************************************/

    @Parameters(method = "getValidMultiplyInput")
    @Test
    public void multiplyShouldReturnExpectedValueWhenOperandsAreValid (double operand1, double operand2, double expectedValue) {
        //Arrange
        //Act
        double result = mathOperation.multiply(operand1, operand2);
        //Assert
        assertEquals(String.format("Resultado %1s ha de ser igual a %2s", result, expectedValue), expectedValue, result, 1E-14);
    }

    private Object[] getValidMultiplyInput() {
        return new Object[]{
                new Object[]{1, 0, 0},
                new Object[]{5, 1, 5},
                new Object[]{5, 2, 10},
                new Object[]{5, -2, -10},
                new Object[]{-5, 2, -10},
                new Object[]{-5, -2, 10},
                new Object[]{-3.5, 2.7, -9.45},
                new Object[]{Double.MAX_VALUE, 1, Double.MAX_VALUE},
                new Object[]{Double.MAX_VALUE, 0.5, (Double.MAX_VALUE*0.5)},
                new Object[]{Double.MAX_VALUE, -0.5, (-Double.MAX_VALUE*0.5)},
                new Object[]{Double.MAX_VALUE, Double.MIN_VALUE, (Double.MAX_VALUE*Double.MIN_VALUE)}
        };
    }

    @Parameters(method = "getInvalidMultiplyInput")
    @Test(expected = OperationException.class)
    public void multiplyShouldThrowsWhenValuesAreInvalid(double operand1, double operand2) {
        // ARRANGE
        // ACT
        double result = mathOperation.multiply(operand1, operand2);
    }

    private Object[] getInvalidMultiplyInput() {
        return new Object[]{
                new Object[]{2, Double.MAX_VALUE},
                new Object[]{-2, Double.MAX_VALUE},
                new Object[]{Double.POSITIVE_INFINITY, 1},
                new Object[]{-12.3, Double.NEGATIVE_INFINITY},
                new Object[]{Double.NaN, 12},
                new Object[]{Math.pow(2, 1024), -1}
        };
    }

    /***DIVIDE*****************************************************/

    @Parameters(method = "getValidDivideInput")
    @Test
    public void divideShouldReturnExpectedValueWhenOperandsAreValid (double operand1, double operand2, double expectedValue) {
        //Arrange
        //Act
        double result = mathOperation.divide(operand1, operand2);
        //Assert
        assertEquals(String.format("Resultado %1s ha de ser igual a %2s", result, expectedValue), expectedValue, result, 1E-14);
    }

    private Object[] getValidDivideInput() {
        return new Object[]{
                new Object[]{0, 1, 0},
                new Object[]{5, 1, 5},
                new Object[]{5, 0.5, 10},
                new Object[]{5, -2, -2.5},
                new Object[]{-5, 2, -2.5},
                new Object[]{-5, -2, 2.5},
                new Object[]{-3.5, 2.7, -1.29629629629629629},
                new Object[]{Double.MAX_VALUE, 1, Double.MAX_VALUE},
                new Object[]{Double.MAX_VALUE, 1.5, (Double.MAX_VALUE/1.5)},
                new Object[]{Double.MAX_VALUE, Double.MAX_VALUE, 1},
                new Object[]{Double.MIN_VALUE, Double.MIN_VALUE, 1},
                new Object[]{Double.MIN_VALUE, 0.5, (Double.MIN_VALUE/0.5)}
        };
    }

    @Parameters(method = "getInvalidDivideInput")
    @Test(expected = OperationException.class)
    public void divideShouldThrowsWhenValuesAreInvalid(double operand1, double operand2) {
        // ARRANGE
        // ACT
        double result = mathOperation.divide(operand1, operand2);
    }

    private Object[] getInvalidDivideInput() {
        return new Object[]{
                new Object[]{2, 0},
                new Object[]{Double.MAX_VALUE,0.9},
                new Object[]{Double.MIN_VALUE,1.1},
                new Object[]{Double.POSITIVE_INFINITY, 1},
                new Object[]{-12.3, Double.NEGATIVE_INFINITY},
                new Object[]{Double.NaN, 12},
                new Object[]{Math.pow(2, 1024), -1}
        };
    }

    /***EXPONENTATIONAL*****************************************************/

    @Parameters(method = "getValidExponentiationInput")
    @Test (timeout = 100)
    public void exponentiationShouldReturnExpectedValueWhenInputIsValid( double base, double exponent, double expectedValue) {
        double result = mathOperation.exponentiation(base, exponent);
        assertThat(result).isWithin(1E-11).of(expectedValue);
    }

    private Object[] getValidExponentiationInput() {
        return $(
                $(0, 0, 1),
                $(2, 0, 1),
                $(2, 1, 2),
                $(2.3, 5, 64.36343),
                $(-3, 4, 81),
                $(-3, 3, -27),
                $(2, -2, 0.25),
                $(-3, -5, -0.00411522633),
                $(0, 10, 0),
                $(1, 10000000, 1)
        );
    }

    @Parameters(method = "getInvalidExponentiationInput")
    @Test(expected = OperationException.class, timeout = 100)
    public void exponentiationShouldThrowWhenInputIsInvalid( double base, double exponent) {
        mathOperation.exponentiation(base, exponent);
    }

    private Object[] getInvalidExponentiationInput() {
        return $(
                $(Double.NEGATIVE_INFINITY, 2),
                $(-3, Double.POSITIVE_INFINITY),
                $(Double.NaN, -1),
                $(2, Double.MAX_VALUE),
                $(2, 1024),
                $(5, 3.3),
                $(-3, -1.2)
        );
    }

    /***SQUARE ROOT*****************************************************/

    @Parameters(method = "getValidSquareRootInput")
    @Test (timeout = 100)
    public void squareRootShouldReturnExpectedValueWhenInputIsValid( double operator, double expectedValue) {
        double result = mathOperation.squareRoot(operator);
        assertThat(result).isWithin(1E-11).of(expectedValue);
    }

    private Object[] getValidSquareRootInput() {
        return $(
                $(4, 2),
                $(9, 3),
                $(0.5, 0.7071067811865475),
                $(2.5, 1.58113883008419),
                $(1000000, 1000)

        );
    }

    @Parameters(method = "getInvalidSquareRootInput")
    @Test(expected = OperationException.class, timeout = 100)
    public void squareRootShouldThrowWhenInputIsInvalid( double operator) {
        mathOperation.squareRoot(operator);
    }

    private Object[] getInvalidSquareRootInput() {
        return $(
                $(-1),
                new Object[]{Double.POSITIVE_INFINITY},
                new Object[]{Double.NEGATIVE_INFINITY},
                new Object[]{Double.NaN}
        );
    }

    /***FACTORIAL*****************************************************/

    @Parameters(method = "getValidFactorialInput")
    @Test (timeout = 100)
    public void factorialShouldReturnExpectedValueWhenInputIsValid( double operator, double expectedValue) {
        double result = mathOperation.factorial(operator);
        assertThat(result).isWithin(1E-15).of(expectedValue);
    }

    private Object[] getValidFactorialInput() {
        return $(
                $(0, 1),
                $(4, 24),
                $(9, 362880),
                $(0.5, 0.5),
                $(2.5, 1.875)

        );
    }

    @Parameters(method = "getInvalidFactorialInput")
    @Test(expected = OperationException.class, timeout = 100)
    public void factorialShouldThrowWhenInputIsInvalid( double operator) {
        mathOperation.factorial(operator);
    }

    private Object[] getInvalidFactorialInput() {
        return $(
                $(-1),
                $(1000000),
                new Object[]{Double.POSITIVE_INFINITY},
                new Object[]{Double.NEGATIVE_INFINITY},
                new Object[]{Double.NaN}
        );
    }

}