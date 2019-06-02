package com.tutorial

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.tutorial.domain.testutils.EspressoIdlingResource
import com.tutorial.presentation.MainActivity
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppInstrumentationTest {
    @Rule
    @JvmField
    var loginActivityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    // Unregistering Idling Resource so it can be garbage collected and does not leak any memory
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testViewLoadedWithoutIssue() {
        onView(withId(R.id.add)).check(matches(isDisplayed()))
        onView(withId(R.id.parentContainter)).check(matches(isDisplayed()))
    }

    @Test
    fun testTodosLoaded() {
        onView(withId(R.id.loading)).check(matches(not(doesNotExist())))
        onView(withId(R.id.todoList)).check(matches(isDisplayed()))
    }
}