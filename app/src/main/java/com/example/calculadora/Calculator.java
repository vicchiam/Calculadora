package com.example.calculadora;

import android.support.annotation.NonNull;

/**
 * Created by vicch on 11/03/2018.
 */

public interface Calculator {

    String addSymbol(@NonNull String to, @NonNull String symbol);
    String removeSymbol(@NonNull String from);
    String calculate(@NonNull String from);

}