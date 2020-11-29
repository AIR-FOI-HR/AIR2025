package hr.foi.air.visualbrickfinder;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CropActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void cropActivityEspressoTest() {

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.activity_main_imgbtn_take_photo), withContentDescription("Wienerberger logo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.crop_image_menu_crop), withText("CROP"),
                        withParent(withParent(withId(R.id.action_bar))),
                        isDisplayed()));
        textView2.check(matches(withText("CROP")));

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.crop_image_menu_crop), withText("Crop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.image_cropped), withContentDescription("Photo"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.button_accept_cropped), withContentDescription("Accept cropped image"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.button_crop_again), withContentDescription("ReCrop image"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_crop_again), withContentDescription("ReCrop image"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.crop_image_menu_crop), withText("CROP"),
                        withParent(withParent(withId(R.id.action_bar))),
                        isDisplayed()));
        textView3.check(matches(withText("CROP")));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.ImageView_image),
                        withParent(allOf(withId(R.id.cropImageView),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.crop_image_menu_crop), withText("Crop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.button_accept_cropped), withContentDescription("Accept cropped image"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.activity_main_txt_tap), withText("Tap to find !"),
                        withParent(withParent(withId(R.id.nav_host_fragment))),
                        isDisplayed()));
        textView4.check(matches(withText("Tap to find !")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
