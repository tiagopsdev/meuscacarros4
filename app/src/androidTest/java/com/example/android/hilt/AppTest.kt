/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.CarLocalDataSource
import com.example.android.hilt.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @After
    fun tearDown() {
        // Remove logs after the test finishes

    }

    @Test
    fun happyPath() {
        //ActivityScenario.launch(MainActivity::class.java)
        ActivityScenario.launch(MainActivity::class.java)


        // Check Screen home was showed on start
        onView(withId(R.id.txvTitle)).check(matches(isDisplayed()))


        // Perform a click on Add Car Button and navigate to Check Car Management Fragment
        onView(withId(R.id.btnAddCar)).perform(click())

        // Check Car Management Fragment was showed
        onView(withId(R.id.txvManangeCarTittle)).check(matches(isDisplayed()))

        // Fill in the car name EditText
        onView(withId(R.id.extCarName)).perform(typeText("CarNameTest"))

        // Fill in the actual KM EditText
        onView(withId(R.id.extActualKM)).perform(typeText("10001"))

        // Fill in the oil change EditText
        onView(withId(R.id.extOilChange)).perform(typeText("10003"))

        // Fill in the wheels EditText
        onView(withId(R.id.extWheels)).perform(scrollTo(),typeText("10004"))

        // Fill in the brakes EditText
        onView(withId(R.id.extBreakes)).perform(scrollTo(),typeText("10005"))

        // Fill in the filters EditText
        onView(withId(R.id.extFilters)).perform(scrollTo(),typeText("10006"))

        // Perform a click on Save Car Button to Save Car on data Base
        onView(withId(R.id.save_car)).perform(scrollTo(), click())

        // You can then close the soft keyboard if needed
        closeSoftKeyboard()

        // Perform a click on Back Button to return to Home Screen
        pressBack()


        // Navigate to List Cars screen
        onView(withId(R.id.btnListCarsGarage)).perform(click())

        Thread.sleep(3000)

        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText(containsString("CarNameTest")))))

        // Perform a click on Back Button to return to Home Screen
        pressBack()

        // Perform a click on delete_cars to remove all cars from the data base
        onView(withId(R.id.delete_cars)).perform(click())




    }
}
