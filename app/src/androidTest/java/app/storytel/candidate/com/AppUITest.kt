package app.storytel.candidate.com

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.storytel.candidate.com.util.waitUntilViewIsDisplayed
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AppUITest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun appUIFlow() {
        // Loading Spinner
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))

        // Wait Until mock data fetch is completed
        waitUntilViewIsDisplayed(withId(R.id.postsList))

        // Check if Loading spinner is not visible
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))

        // Check if Recyclerview is displayed
        onView(withId(R.id.postsList)).check(matches(isDisplayed()))

        // Click in the first item in the list
        onView(withId(R.id.postsList))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // Check if Loading spinner is not visible
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))

        // Wait Until mock data fetch is completed
        waitUntilViewIsDisplayed(withId(R.id.root_layout_cardviews))

        // Check if Loading spinner is not visible
        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
    }
}