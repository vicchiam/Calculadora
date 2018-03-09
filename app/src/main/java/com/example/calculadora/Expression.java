package com.example.calculadora;

import android.support.annotation.NonNull;

/**
 * Created by vicch on 09/03/2018.
 */

public interface Expression {
    String read(@NonNull String expression);
    String write(@NonNull String expression);
    String addSymbol(@NonNull String expression, @NonNull String symbol);
    String removeSymbol(@NonNull String expression);
    String replaceSymbol(@NonNull String expression, @NonNull String symbol);
    String[] tokenize(@NonNull String expression);
}
