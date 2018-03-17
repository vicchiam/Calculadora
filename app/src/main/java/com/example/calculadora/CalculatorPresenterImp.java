package com.example.calculadora;

/**
 * Created by vicch on 16/03/2018.
 */

public class CalculatorPresenterImp implements CalculatorPresenter{

    private CalculatorView view;
    private Calculator calculator;

    public CalculatorPresenterImp(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
    }

    @Override
    public void addSymbol(String expression, String symbol) {
        throwsIsValidInput(expression);
        throwsIsValidInput(symbol);
        try {
            view.showOperations(calculator.addSymbol(expression, symbol));
        }
        catch (RuntimeException exception) {
            view.showError();
        }
    }

    @Override
    public void removeSymbol(String expression) {
        throwsIsValidInput(expression);
        view.showOperations(calculator.removeSymbol(expression));
    }

    @Override
    public void clearScreen() {
        view.showOperations("");
    }

    @Override
    public void calculate(String expression) {
        throwsIsValidInput(expression);
        if(expression.isEmpty()) {
            view.showResult(expression);
            return;
        }
        try{
            view.showResult(calculator.calculate(expression));
        }
        catch (RuntimeException exception) {
            view.showError();
        }
    }

    private void throwsIsValidInput(String param) throws CalculatorPresenterException{
        if(param==null) throw  new CalculatorPresenterException("No se puede introducir un valor nulo");
    }

}
