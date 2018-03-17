package com.example.calculadora;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vicch on 16/03/2018.
 */

public class CalculatorPresenterTest {

    private CalculatorPresenterImp presenter;
    @Mock
    private CalculatorView mockedView;
    @Mock
    private Calculator mockedCalculator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new CalculatorPresenterImp(mockedView, mockedCalculator);
    }

    @Test
    public void addSymbolShouldCallShowOperationsWhenInputIsValid() {
        when(mockedCalculator.addSymbol(anyString(), anyString())) .thenReturn("+");
        presenter.addSymbol(anyString(), anyString());
        verify(mockedView, times(1)).showOperations(anyString());
        verify(mockedView, times(0)).showError();
    }

    @Test
    public void addSymbolShouldCallShowErrorWhenCalculatorThrowsAnOperationException() {
        when(mockedCalculator.addSymbol(anyString(),anyString())).thenThrow(OperationException.class);
        presenter.addSymbol(anyString(), anyString());
        verify(mockedView, times(0)).showOperations(anyString());
        verify(mockedView, times(1)).showError();
    }

    @Test
    public void addSymbolShouldCallShowErrorWhenCalculatorThrowsAnExpressionException() {
        when(mockedCalculator.addSymbol(anyString(), anyString())).thenThrow(ExpressionException.class);
        presenter.addSymbol(anyString(), anyString());
        verify(mockedView, times(0)).showOperations(anyString());
        verify(mockedView, times(1)).showError();
    }

    @Test(expected = CalculatorPresenterException.class)
    public void addSymbolShouldCallShowErrorWhenCalculatorWhenInputNull() {
        presenter.addSymbol(null, null);
    }

    @Test
    public void removeSymbolShouldCallShowOperationsWhenInputIsValid() {
        when(mockedCalculator.removeSymbol(anyString())) .thenReturn("+");
        presenter.removeSymbol(anyString());
        verify(mockedView, times(1)).showOperations(anyString());
        verify(mockedView, times(0)).showError();
    }

    @Test(expected = CalculatorPresenterException.class)
    public void removeSymbolShouldCallShowErrorWhenCalculatorWhenInputNull() {
        presenter.removeSymbol(null);
    }

    @Test
    public void clearScreenShouldCallShowOperationsWhenInputIsValid() {
        presenter.clearScreen();
        verify(mockedView, times(1)).showOperations(anyString());
        verify(mockedView, times(0)).showError();
    }


    @Test
    public void calculateShouldCallShowOperationsWhenInputIsValid() {
        when(mockedCalculator.calculate(anyString())) .thenReturn("5");
        presenter.calculate(anyString());
        verify(mockedView, times(1)).showResult(anyString());
        verify(mockedView, times(0)).showError();
    }

    @Test
    public void calculateShouldCallShowOperationsWhenInputIsEmpty() {
        when(mockedCalculator.calculate(anyString())) .thenReturn("5");
        presenter.calculate("");
        verify(mockedView, times(1)).showResult(anyString());
        verify(mockedView, times(0)).showError();
    }

    @Test
    public void calculateShouldCallShowErrorWhenCalculatorThrowsAnOperationException() {
        when(mockedCalculator.calculate(anyString())).thenThrow(OperationException.class);
        presenter.calculate(anyString()+" ");
        verify(mockedView, times(0)).showOperations(anyString());
        verify(mockedView, times(1)).showError();
    }

    @Test
    public void calculateShouldCallShowErrorWhenCalculatorThrowsAnExpressionException() {
        when(mockedCalculator.calculate(anyString())).thenThrow(ExpressionException.class);
        presenter.calculate(anyString()+" ");
        verify(mockedView, times(0)).showOperations(anyString());
        verify(mockedView, times(1)).showError();
    }

    @Test(expected = CalculatorPresenterException.class)
    public void calculateShouldCallShowErrorWhenCalculatorWhenInputNull() {
        presenter.calculate(null);
    }

}
