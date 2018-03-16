package com.example.calculadora;

import android.support.annotation.NonNull;

/**
 * Created by vicch on 16/03/2018.
 */

public class MockExpression implements Expression {

    public boolean symbolAdded = false;
    public boolean symbolReplaced = false;
    public boolean symbolDeleted = false;

    @Override
    public String read(@NonNull String expression) {
        return null;
    }

    @Override
    public String write(@NonNull String expression) {
        return null;
    }

    @Override
    public String addSymbol(@NonNull String expression, @NonNull String symbol) {
        symbolAdded = true;
        return null;
    }

    @Override
    public String removeSymbol(@NonNull String expression) {
        symbolDeleted =  true;
        return null;
    }

    @Override
    public String replaceSymbol(@NonNull String expression, @NonNull String symbol) {
        symbolReplaced = true;
        return null;
    }

    @Override
    public String[] tokenize(@NonNull String expression) {
        return new String[0];
    }


}
