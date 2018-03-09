package com.example.calculadora;

/**
 * Created by vicch on 09/03/2018.
 */

public class MathOperation {

    public double addition(double operand1, double operand2) throws OperationException{
        additionThrowsIfValuesAreInvalid(operand1, operand2);
        return operand1+operand2;
    }

    private void additionThrowsIfValuesAreInvalid(double... values) throws OperationException {
        throwsIfValuesAreInvalid(values);
    }

    public double subtract(double operand1, double operand2) throws OperationException{
        subtractThrowsIfValuesAreInvalid(operand1, operand2);
        return operand1-operand2;
    }

    private void subtractThrowsIfValuesAreInvalid(double operand1, double operand2) throws OperationException {
        if(Double.isInfinite(operand1) || Double.isInfinite(operand2) || Double.isNaN(operand1) || Double.isNaN(operand2)) throw new OperationException();
        if(operand1==Double.MAX_VALUE && operand2<0 || operand1==-Double.MAX_VALUE && operand2>0) throw new OperationException();
        if(operand1<0 && operand2==Double.MAX_VALUE || operand1>0 && operand2==-Double.MAX_VALUE) throw new OperationException();
    }

    public double multiply(double operand1, double operand2) throws OperationException{
        multiplyThrowsIfValuesAreInvalid(operand1, operand2);
        return operand1*operand2;
    }

    private void multiplyThrowsIfValuesAreInvalid(double operand1, double operand2) throws OperationException {
        if(Double.isInfinite(operand1) || Double.isInfinite(operand2) || Double.isNaN(operand1) || Double.isNaN(operand2)) throw new OperationException();
        if(Math.abs(operand1)==Double.MAX_VALUE && Math.abs(operand2)>1) throw new OperationException();
        if(Math.abs(operand1)>1 && Math.abs(operand2)==Double.MAX_VALUE) throw new OperationException();
    }

    public double divide(double operand1, double operand2) throws OperationException{
        divideThrowsIfValuesAreInvalid(operand1, operand2);
        return operand1/operand2;
    }

    private void divideThrowsIfValuesAreInvalid(double operand1, double operand2) throws OperationException {
        if(Double.isInfinite(operand1) || Double.isInfinite(operand2) || Double.isNaN(operand1) || Double.isNaN(operand2)) throw new OperationException();
        if(operand2==0) throw new OperationException();
        if(Math.abs(operand1)==Double.MAX_VALUE && Math.abs(operand2)<1)  throw new OperationException();
        if(Math.abs(operand1)==Double.MIN_VALUE && Math.abs(operand2)>1)  throw new OperationException();
    }


    public double exponentiation(double base, double exponent) throws OperationException{
        if(base==0 && exponent==0) return 1;
        else if(base==0) return 0;
        else if(base==1 && exponent!=0) return 1;
        double result = 1;
        boolean expoNegative = exponent < 0;
        if (expoNegative) exponent = -exponent;
        while (exponent != 0) {
            result = multiply(result, base);
            exponent--;
            throwsIfValuesAreInvalid(result);
        }
        return expoNegative ? (1 / result) : result;
    }

    public double squareRoot(double radicand) {
        squareRootThrowsIfValuesAreInvalid(radicand);
        double aux;
        double squareRoot = radicand / 2;
        do {
            aux = squareRoot;
            squareRoot = (aux + (radicand / aux)) / 2;
        } while (aux != squareRoot);
        return squareRoot;
    }

    private void squareRootThrowsIfValuesAreInvalid(double operand) throws OperationException {
        if(Double.isInfinite(operand) || Double.isNaN(operand)) throw new OperationException();
        if(operand<0) throw new OperationException();
    }

    public double factorial(double operand) {
        factorialThrowsIfValuesAreInvalid(operand);
        if(operand>0 && operand<1) return operand;
        double result = 1;
        while (operand > 0) {
            result *= operand;
            operand--;
            factorialThrowsIfValuesAreInvalid(result);
        }
        return result;
    }

    private void factorialThrowsIfValuesAreInvalid(double operand) throws OperationException {
        squareRootThrowsIfValuesAreInvalid(operand);
    }

    private void throwsIfValuesAreInvalid(double... values) throws OperationException {
        for (Double value : values) {
            if (value == Double.MAX_VALUE || Double.isInfinite(value) || Double.isNaN(value)) throw new OperationException();
        }
    }









}
