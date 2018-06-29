package com.acme.a3csci3130;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Rule;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import static android.content.ContentValues.TAG;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.setFailureHandler;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;


/**
 * This class validate the operation of Create, Edit and Delete a business from the
 *app using Espresso.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);


    /**
     * This method will create a new business and find it from home page, also re-validate all
     * its fields to test all the information is correct.
     * @throws InterruptedException is to handle the sleep timer exception
     */
    @Test
    public void A_CreateBusinessTest() throws InterruptedException {
        onView(withId(R.id.submitButton)).perform(click());

        //type business number
        onView(withId(R.id.businessNum)).perform(typeText("1234567890"));
        closeSoftKeyboard();
        //type name
        onView(withId(R.id.Name)).perform(typeText("Tester"));
        closeSoftKeyboard();
        //Click primary business
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        //type address
        onView(withId(R.id.address_field)).perform(typeText("Test Address"));
        closeSoftKeyboard();
        //Click province
        onView(withId(R.id.spinner2)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());
        closeSoftKeyboard();
        //submit
        onView(withId(R.id.createButton)).perform(click());

        Thread.sleep(1000);
        //check data
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.businessNum2)).check(matches(withText("1234567890")));
        onView(withId(R.id.Name2)).check(matches(withText("Tester")));
        onView(withId(R.id.spinner3)).check(matches(withSpinnerText(containsString("Fisher"))));
        onView(withId(R.id.address_field2)).check(matches(withText("Test Address")));
        onView(withId(R.id.spinner4)).check(matches(withSpinnerText(containsString("AB"))));

        pressBack();
    }

    /**
     * This method will click into a business that has been created, and change all its fields and
     * re-validate its information to make sure the information is indeed changed and stored.
     * @throws InterruptedException is to handle the sleep timer exception
     */
    @Test
    public void B_EditBusinessTest() throws InterruptedException {
        Thread.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());

        //Change business number
        onView(withId(R.id.businessNum2)).perform(clearText());
        onView(withId(R.id.businessNum2)).perform(typeText("0987654321"));
        closeSoftKeyboard();
        //Change name
        onView(withId(R.id.Name2)).perform(clearText());
        Thread.sleep(1000);
        onView(withId(R.id.Name2)).perform(typeText("Change Name"));
        closeSoftKeyboard();
        //Change primary business
        onView(withId(R.id.spinner3)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());
        //Change address
        onView(withId(R.id.address_field2)).perform(clearText());
        onView(withId(R.id.address_field2)).perform(typeText("Test Address 2"));
        closeSoftKeyboard();
        //Change province
        onView(withId(R.id.spinner4)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());
        //Click update
        onView(withId(R.id.updateButton)).perform(click());

        // Check date is been changed
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.businessNum2)).check(matches(withText("0987654321")));
        onView(withId(R.id.Name2)).check(matches(withText("Change Name")));
        onView(withId(R.id.spinner3)).check(matches(withSpinnerText(containsString("Distributor"))));
        onView(withId(R.id.address_field2)).check(matches(withText("Test Address 2")));
        onView(withId(R.id.spinner4)).check(matches(withSpinnerText(containsString("BC"))));

        pressBack();
    }

    /**
     * This method will select a created business and delete it from database, then validate from
     * home page to make sure it is been deleted.
     * @throws InterruptedException is to handle the sleep timer exception
     */
    @Test
    public void C_DeleteBusinessTest() throws InterruptedException {
        Thread.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());
        try {
            onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        } catch (Exception e) {
            Log.d(TAG, "********" + e.getMessage());
            if (!e.getMessage().equals("Error performing 'load adapter data' on view 'with id: com.acme.a3csci3130:id/listView'."))
                fail();
        }
    }


}
