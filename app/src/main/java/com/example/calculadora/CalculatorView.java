package com.example.calculadora;

/**
 * Created by vicch on 16/03/2018.
 */

public interface CalculatorView {

    void setPresenter(CalculatorPresenter presenter);
    void showOperations(String operations);
    void showResult(String result); void showError();

}
