package be.felixdeswaef.reminder;


import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;

import be.felixdeswaef.reminder.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class esspresoHompage {

    private String stringToBetyped;
    private String stringToBetyped2;
    private String stringToBetyped3;

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // make it random so no previous tes can fuck it up

        String rand =(String) (SystemClock.elapsedRealtime() + "time");


        stringToBetyped = "Espresso" + rand;
        stringToBetyped2 = "description "+rand;
        stringToBetyped3 = "altered "+rand;
    }

    @Test
    public void changeText_sameActivity() {


        //eresa all enties cuz i cant scroll to specific item with recyclervieuw
        //to open overflow menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Click the item in the overflow menu
        onView(withText("DONTCLICKTHIS"))
                .perform(click());



        onView(withId(R.id.fab)).perform(click());//click add new

        onView(withId(R.id.name)).perform(typeText(stringToBetyped)); //fill in the necesary fields
        onView(withId(R.id.description)).perform(typeText(stringToBetyped2));//watch out for autofill chacged desc to description
        onView(withId(R.id.savechangesbutton)).perform(click());//save
        onView(withId(R.id.pullToRefresh)).perform(swipeDown()); //refresh list


        onView(withText(stringToBetyped)).check(matches(isDisplayed())); //is there an item with the name we used

        onView(withText(stringToBetyped)).perform(click());
        onView(withText(stringToBetyped2)).check(matches(isDisplayed())); //is there an item with the desc we used
        onView(withId(R.id.edittaskbuton)).perform(click());
        onView(withId(R.id.description)).perform(clearText(),typeText(stringToBetyped3));
        onView(withId(R.id.savechangesbutton)).perform(click());//save


        onView(withText(stringToBetyped3)).check(matches(isDisplayed())); //is there an item with the altered desc we used





    }
}