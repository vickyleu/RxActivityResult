package app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import io.victoralbertos.app.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class OnPreResultTest {
    @Rule public ActivityTestRule<OnPreResultActivity> activityRule = new ActivityTestRule<>(OnPreResultActivity.class);

    @Test public void CheckHasBothResults() {
        onView(withId(R.id.start_pre_for_result)).perform(click());
        onView(withId(R.id.pre_result)).check(matches(withText("Do whatever you want with the data, but not with the UI")));
        onView(withId(R.id.result)).check(matches(withText("Well done first")));
    }

}