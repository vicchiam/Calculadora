package com.example.calculadora;

import org.junit.Before;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

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



}