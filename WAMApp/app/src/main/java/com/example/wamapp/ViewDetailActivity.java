package com.example.wamapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ViewDetailActivity extends AppCompatActivity{

    private Bundle extras;
    private UserProfileFragment mUserProfileFragment;
    private GoalsFragment mGoalsFragment;
    private BmiFragment mBmiFragment;
    private BmrFragment mBmrFragment;
    private WeatherFragment mWeatherFragment;
    private HikesFragment mHikesFragment;
    private StepCounterFragment mStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetail_activity);

        if (savedInstanceState != null) {
            extras = savedInstanceState.getBundle("extras");
        } else {
            extras = getIntent().getExtras();
        }

        int position = extras.getInt("click_position");

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0: { // Profile
                mUserProfileFragment = new UserProfileFragment();
                fTrans.replace(R.id.fl_viewdetail, mUserProfileFragment, "frag_profiledetail");
                fTrans.commit();
                break;
            }
            case 1: { // BMI
                mBmiFragment = new BmiFragment();
                fTrans.replace(R.id.fl_viewdetail, mBmiFragment, "frag_BMIdetail");
                fTrans.commit();
                break;
            }
            case 2: { // BMR
                mBmrFragment = new BmrFragment();
                fTrans.replace(R.id.fl_viewdetail, mBmrFragment, "frag_BMRdetail");
                fTrans.commit();
                break;
            }
            case 3: { // Weather
                mWeatherFragment = new WeatherFragment();
                fTrans.replace(R.id.fl_viewdetail, mWeatherFragment, "frag_weatherdetail");
                fTrans.commit();
                break;
            }
            case 4: { // Hikes
                mHikesFragment = new HikesFragment();
                fTrans.replace(R.id.fl_viewdetail, mHikesFragment, "frag_hikesdetail");
                fTrans.commit();
                break;
            }
            case 5: { // Goals
                mGoalsFragment = new GoalsFragment();
                fTrans.replace(R.id.fl_viewdetail, mGoalsFragment, "frag_goaldetail");
                fTrans.commit();
                break;
            }
            case 6: { // Steps
                mStepFragment = new StepCounterFragment();
                fTrans.replace(R.id.fl_viewdetail, mStepFragment, "frag_stepdetail");
                fTrans.commit();
                break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putBundle("extras", extras);
    }
}
