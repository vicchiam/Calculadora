package com.example.calculadora;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by vicch on 17/03/2018.
 */
/*
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void onClickButtonOneShouldAddOneToOperationsView() {
        //Clic en el botón 1
        onView(withId(R.id.bt1)) .perform(click());
        //Comprueba si la pantalla de operaciones muestra el 1
        onView(withId(R.id.operations)) .check(matches(withText("1")));
    }

}
*/

@RunWith(JUnitParamsRunner.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    public static ViewAction setText(final String value) {
        return new ViewAction() {
            @SuppressWarnings("unchecked")

            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() { return "replace text"; }
        };
    }

    @Test
    @Parameters(method = "getValidOperandButtonData")
    public void onClickButtonShouldAddExpectedValueToOperationsViews(int buttonId, String expectedValue) {
        //Click en el botón
        onView(withId(buttonId)).perform(click());
        //Comprobamos si la pantalla de operaciones muestra el valor esperado
        onView(withId(R.id.operations)).check(matches(withText(expectedValue)));
    }

    private static Object[] getValidOperandButtonData() {
        return new Object[]{
                new Object[]{R.id.bt1, "1"},
                new Object[]{R.id.bt2, "2"},
                new Object[]{R.id.bt3, "3"},
                new Object[]{R.id.bt4, "4"},
                new Object[]{R.id.bt5, "5"},
                new Object[]{R.id.bt6, "6"},
                new Object[]{R.id.bt7, "7"},
                new Object[]{R.id.bt8, "8"},
                new Object[]{R.id.bt9, "9"},
                new Object[]{R.id.bt0, "0"},
                new Object[]{R.id.bt00, "00"},
                new Object[]{R.id.bt_addition, " + "},
                new Object[]{R.id.bt_subtraction, " - "},
                new Object[]{R.id.bt_multiplication, " x "},
                new Object[]{R.id.bt_division, " / "},
                new Object[]{R.id.bt_exponentiation, " ^ "},
                new Object[]{R.id.bt_factorial, " fact ("},
                new Object[]{R.id.bt_square_root, " sqrt ("},
                new Object[]{R.id.bt_dot, "."},
                new Object[]{R.id.bt_parenthesis_start, " ("},
                new Object[]{R.id.bt_parenthesis_end, ") "}};
    }

    @Test
    @Parameters(method = "getValidOperationsData")
    public void onOperationsViewChangedShouldUpdateResultsView( String operations, String result) {
        onView(withId(R.id.operations)) .perform(setText(operations));
        onView(allOf(withParent(withId(R.id.result)), isCompletelyDisplayed())) .check(matches(withText(result)));
    }

    private static Object[] getValidOperationsData() {
        return new Object[]{
                new Object[]{"2+2", "4"},
                new Object[]{"sqrt(9)", "3"},
                new Object[]{"5/2", "2.5"},
                new Object[] {"3+(4x3)", "15"},
                new Object[] {"3+(4x3)-5", "10"},
                new Object[] {"3-(-4)", "7"}
        };
    }

    @Test
    @Parameters(method = "getValidButtonCEData")
    public void onClickButtonCEShouldRemoveLastSymbolToOperationsViews( String operations, String result) {
        onView(withId(R.id.operations)) .perform(setText(operations));
        onView(withId(R.id.bt_remove_last)).perform(click());
        onView(withId(R.id.operations)) .check(matches(withText(result)));
    }

    private static Object[] getValidButtonCEData() {
        return new Object[]{
                new Object[]{"2+2", "2 + "},
                new Object[]{"sqrt(9)", " sqrt (9"},
                new Object[]{"5/2", "5 / "},
                new Object[] {"3+(4x3)", "3 + (4 x 3"},
                new Object[] {"3+(4x3)-5", "3 + (4 x 3) - "},
                new Object[] {"3-(-4)", "3 - ( - 4"}
        };
    }

    @Test
    @Parameters(method = "getValidButtonClearData")
    public void onClickButtonClearShouldRemoveTextToOperationsAndResultViews( String operations, String result) {
        onView(withId(R.id.operations)) .perform(setText(operations));
        onView(withId(R.id.bt_clear)).perform(click());
        onView(withId(R.id.operations)) .check(matches(withText(result)));
        onView(allOf(withParent(withId(R.id.result)), isCompletelyDisplayed())) .check(matches(withText(result)));

    }

    private static Object[] getValidButtonClearData() {
        return new Object[]{
                new Object[]{"2+2", ""},
                new Object[]{"sqrt(9)", ""},
                new Object[]{"5/2", ""},
                new Object[] {"3+(4x3)", ""},
                new Object[] {"3+(4x3)-5", ""},
                new Object[] {"3-(-4)", ""}
        };
    }


}