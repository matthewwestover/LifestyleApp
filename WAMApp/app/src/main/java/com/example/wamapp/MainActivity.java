package com.example.wamapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Database;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity implements EditUserFragment.OnDataPass, MyRVAdapter.DataPasser, AppHeadFragment.OnDataPass {
    private Boolean isEditUser = false;
    private Boolean userExists = false;
    private int containerBody;
    private int containerHeader;
    private UserViewModel mUserViewModel;

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("WAMAPP", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("WAMAPP", "Could not initialize Amplify", error);
        }

        setContentView(R.layout.activity_main);
        containerBody = isTablet() ? R.id.fl_list_tablet : R.id.fl_main_container_phone;
        containerHeader = isTablet() ? R.id.fl_header_tablet : R.id.fl_header_phone;
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        if (mUserViewModel.getUser().getValue() == null) {
            User newUser = new User(0);
            mUserViewModel.insert(newUser);
            mUserViewModel.setUser(newUser);
        }

        VoidAsyncTask task = mUserViewModel.getNumberOfUserInDatabase();
        task.execute();

        int numUsersInDB = 0;

        try {
            numUsersInDB = (int) task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (numUsersInDB > 0) {
            userExists = true;
        }
        if (savedInstanceState == null) {
            isEditUser = true;
        } else {
            isEditUser = savedInstanceState.getBoolean("editUserBoolean");
        }


        changeDisplay();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fManager = getSupportFragmentManager();
        Fragment editUserFragment = fManager.findFragmentByTag("editUser_frag");
        if (editUserFragment != null && editUserFragment.isAdded()) {
            isEditUser = true;
        }
        outState.putBoolean("editUserBoolean", isEditUser);
    }

    public void changeDisplay() {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTrans = fManager.beginTransaction();
        if (isEditUser) {
            fTrans.replace(containerBody, new EditUserFragment(), "editUser_frag");
            fTrans.replace(containerHeader, new BlankHeadFragment());
        } else {
            fTrans.replace(containerBody, new MasterListFragment(), "masterList_frag");
            fTrans.replace(containerHeader, new AppHeadFragment());
        }
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    // From EditUserFragment
    @Override
    public void onEditUserSubmit() {
        isEditUser = false;
        changeDisplay();
    }

    // From UserProfileFragment
    @Override
    public void onEditUserClick() {
        isEditUser = true;
        changeDisplay();
        if (isTablet()) {
            FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fl_detail_tablet);
            if (f != null) {
                fTrans.hide(f);
            }
            fTrans.commit();
        }
    }

    // For the different Modules
    @Override
    public void passData(int position) {
        Bundle positionBundle = new Bundle();
        positionBundle.putInt("click_position", position);
        // Switch tells which fragment to pass data to
        // Tablets load the fragment next to the list of modules and has to be handled seperately
        // Data should now come from the room for each fragment to get and update user data
        switch (position) {

            case 0: { // Profile Page
                if(isTablet()){
                    UserProfileFragment profileFrag = new UserProfileFragment();
                    profileFrag.setArguments(positionBundle);
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_detail_tablet, profileFrag, "profile_frag_tablet");
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 1: { // BMI Page
                if(isTablet()){
                    BmiFragment bmiFrag = new BmiFragment();
                    bmiFrag.setArguments(positionBundle);
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_detail_tablet, bmiFrag, "bmi_frag_tablet");
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 2: { // BMR Page
                if(isTablet()){
                    BmrFragment bmrFrag = new BmrFragment();
                    bmrFrag.setArguments(positionBundle);
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_detail_tablet, bmrFrag, "bmr_frag_tablet");
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 3: { // Weather Page
                mUserViewModel.dumpInDB(mUserViewModel.getUser().getValue());
                if(isTablet()){
                    WeatherFragment weatherFrag = new WeatherFragment();
                    weatherFrag.setArguments(positionBundle);
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_detail_tablet, weatherFrag, "weather_frag_tablet");
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 4: { // Hikes Page
                if(isTablet()){
                    HikesFragment hikeFrag = new HikesFragment();
                    hikeFrag.setArguments(positionBundle);
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_detail_tablet, hikeFrag, "hike_frag_tablet");
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 5: { // Goals Page
                if(isTablet()){
                    GoalsFragment goalFrag = new GoalsFragment();
                    goalFrag.setArguments(positionBundle);
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_detail_tablet, goalFrag, "goal_frag_tablet");
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
            case 6: { // Steps Page
                if(isTablet()){
                    StepCounterFragment stepFrag = new StepCounterFragment();
                    stepFrag.setArguments(positionBundle);
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_detail_tablet, stepFrag, "step_frag_tablet");
                    fTrans.commit();
                    break;
                } else {
                    Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                    sendIntent.putExtras(positionBundle);
                    startActivity(sendIntent);
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fManager = getSupportFragmentManager();
        Fragment editUserFragment = fManager.findFragmentByTag("editUserFragment");
        if (editUserFragment != null && editUserFragment.isAdded()) {
            return;
        } else {
            super.onBackPressed();
        }
    }




//    // For EditUserData
//    @Override
//    public void onDataPass(String firstName, String lastName, int age, int height, int weight, String city, String country, Bundle thumbnailImage, String sex) {
//        mUserFName = firstName;
//        mUserLName = lastName;
//        mUserAge = age;
//        mUserHeight = height;
//        mUserWeight = weight;
//        mUserCity = city;
//        mUserCountry = country;
//        mUserSex = sex;
//        mUserProfilePic = thumbnailImage;
//        isEditUser = false;
//
//        Bitmap thumbnail = (Bitmap) thumbnailImage.get("data");
//        mUser = new User(1, firstName, lastName, age, sex, thumbnail, city, country, height, weight);
//        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
//
//
//
//        mMasterListFrag = new MasterListFragment();
//        if(isTablet()){
//            fTrans.hide(mUserEditFrag);
//            fTrans.replace(R.id.fl_list_tablet, mMasterListFrag, "frag_masterlist");
//        } else {
//            fTrans.replace(R.id.fl_main_container_phone, mMasterListFrag, "frag_masterlist");
//            Bundle headerBundle = new Bundle();
//            headerBundle.putString("userFirstName", firstName);
//            headerBundle.putBundle("userPic", thumbnailImage);
//            mAppHeadFrag = new AppHeadFragment();
//            mAppHeadFrag.setArguments(headerBundle);
//            fTrans.replace(R.id.fl_header_phone, mAppHeadFrag, "app_header_frag");
//        }
//        fTrans.commit();
//    }

}