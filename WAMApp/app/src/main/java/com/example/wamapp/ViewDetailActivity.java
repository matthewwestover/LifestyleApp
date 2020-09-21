package com.example.wamapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ViewDetailActivity extends AppCompatActivity {

    private Bundle extras;
    private UserProfileFragment mUserProfileFragment;
    private GoalsFragment mGoalsFragment;
    private BmiFragment mBmiFragment;
    private BmrFragment mBmrFragment;
    private WeatherFragment mWeatherFragment;
    private HikesFragment mHikesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetail_activity);

        if(savedInstanceState != null) {
            extras = savedInstanceState.getBundle("extras");
        } else {
            extras = getIntent().getExtras();
        }

        int position = extras.getInt("click_position");

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        switch(position) {
            case 0: { // Profile
                mUserProfileFragment = new UserProfileFragment();
                fTrans.replace(R.id.fl_viewdetail, mUserProfileFragment, "frag_profiledetail");
                break;
            }
            case 1: { // BMI
                mBmiFragment = new BmiFragment();
                double mBMIValue = extras.getDouble("bmi_data");
                Bundle bmiData = new Bundle();
                bmiData.putDouble("bmi_data", mBMIValue);
                mBmiFragment.setArguments(bmiData);
                fTrans.replace(R.id.fl_viewdetail, mBmiFragment, "frag_BMIdetail");
                fTrans.commit();
                break;
            }
            case 2: { // BMR
                mBmrFragment = new BmrFragment();
                double mBMRValue = extras.getDouble("bmr_data");
                Bundle bmrData = new Bundle();
                bmrData.putDouble("bmr_data", mBMRValue);
                mBmrFragment.setArguments(bmrData);
                fTrans.replace(R.id.fl_viewdetail, mBmrFragment, "frag_BMRdetail");
                fTrans.commit();
                break;
            }
            case 3: { // Weather
                mWeatherFragment = new WeatherFragment();
                String mCity = extras.getString("userCity");
                String mCountry = extras.getString("userCountry");
                Bundle locationData = new Bundle();
                locationData.putString("userCity", mCity);
                locationData.putString("userCountry", mCountry);
                mWeatherFragment.setArguments(locationData);
                fTrans.replace(R.id.fl_viewdetail, mWeatherFragment, "frag_weatherdetail");
                fTrans.commit();
                break;
            }
            case 4: { // Hikes
                mHikesFragment = new HikesFragment();
                String mCity = extras.getString("userCity");
                String mCountry = extras.getString("userCountry");
                Bundle locationData = new Bundle();
                locationData.putString("userCity", mCity);
                locationData.putString("userCountry", mCountry);
                mHikesFragment.setArguments(locationData);
                fTrans.replace(R.id.fl_viewdetail, mHikesFragment, "frag_hikesdetail");
                fTrans.commit();
                break;
            }
            case 5: { //Goals
                mGoalsFragment = new GoalsFragment();
                int mAge = extras.getInt("userAge");
                int mHeight = extras.getInt("userHeight");
                int mWeight = extras.getInt("userWeight");
                String mSex = extras.getString("userSex");
                Bundle goalData = new Bundle();
                goalData.putInt("userAge", mAge);
                goalData.putInt("userHeight", mHeight);
                goalData.putInt("userWeight", mWeight);
                goalData.putString("userSex", mSex);
                mGoalsFragment.setArguments(goalData);
                fTrans.replace(R.id.fl_viewdetail, mGoalsFragment, "frag_goaldetail");
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
