package com.example.calculadora;

/**
 * Created by vicch on 16/03/2018.
 */

public interface CalculatorPresenter {

    void addSymbol(String expression, String character);
    void removeSymbol(String expression);
    void clearScreen();
    void calculate(String expression);

}
