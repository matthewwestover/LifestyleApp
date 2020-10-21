package com.example.wamapp;

import android.content.Intent;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class robolectricTest {

    @Test
    public void clickingSubmitOnUserPage_shouldStartSubmit() {
//        final UserProfileFragment fragment = Robolectric. (UserProfileFragment.class);
//        startFragment(fragment);  
//        List<Toast> toasts = shadowOf(application).getShownToasts();

        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.sub_button).performClick();

        Intent expectedIntent = new Intent(activity, MainActivity.class);
        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
        
//        WelcomeActivity activity = Robolectric.setupActivity(WelcomeActivity.class);
//        UserProfileFragment fragment = Robolectric.startFragment(UserProfileFragment.class);
//        activity.findViewById(R.id.login).performClick();

//        Intent expectedIntent = new Intent(activity, LoginActivity.class);
//        Intent actual = shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
//        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}
