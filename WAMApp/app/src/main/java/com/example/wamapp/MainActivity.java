package com.example.wamapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity implements EditUserFragment.OnDataPass, MyRVAdapter.DataPasser {
    private Fragment mMasterListFrag, mAppHeadFrag, mUserEditFrag, mBlankHeadFrag;
    private User mUser;
    Boolean isEditUser = false;
    String mUserSex, mUserFName, mUserLName, mUserCity, mUserCountry;
    int mUserHeight, mUserAge, mUserWeight;
    double mUserBMI, mUserBMR;
    Bundle mUserProfilePic;

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mUserEditFrag = new EditUserFragment();
            isEditUser = true;
        } else {
            mUserSex = savedInstanceState.getString("userSex");
            mMasterListFrag = getSupportFragmentManager().getFragment(savedInstanceState, "frag_masterlist");
            mUserEditFrag = getSupportFragmentManager().getFragment(savedInstanceState, "frag_detail");
            mAppHeadFrag = getSupportFragmentManager().getFragment(savedInstanceState, "app_header_frag");
            isEditUser = savedInstanceState.getBoolean("editUserBoolean");
            mUserAge = savedInstanceState.getInt("userAge");
            mUserHeight = savedInstanceState.getInt("userHeight");
            mUserWeight = savedInstanceState.getInt("userWeight");
            mUserSex = savedInstanceState.getString("userSex");
            mUserFName = savedInstanceState.getString("userFirstName");
            mUserLName = savedInstanceState.getString("userLastName");
            mUserFName = savedInstanceState.getString("userFullName");
            mUserCity = savedInstanceState.getString("userCity");
            mUserCountry = savedInstanceState.getString("userCountry");
            mUserBMI = savedInstanceState.getDouble("userBMI");
            mUserBMR = savedInstanceState.getDouble("userBMR");
            mUserProfilePic = savedInstanceState.getBundle("userPic");
        }

        if (isEditUser) {
            FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
            if(isTablet()) {
                fTrans.replace(R.id.fl_edituser_tablet, mUserEditFrag, "frag_detail");
            } else {
                mBlankHeadFrag = new BlankHeadFragment();
                fTrans.replace(R.id.fl_main_container_phone, mUserEditFrag, "frag_detail");
                fTrans.replace(R.id.fl_header_phone, mBlankHeadFrag, "head_detail");
            }
            fTrans.commit();
        }
    }

    // For the different Modules
    @Override
    public void passData(int position) {
        Bundle positionBundle = new Bundle();
        positionBundle.putInt("click_position", position);

        switch (position) {
            case 0: { // Profile Page
                if(isTablet()){

                } else {

                }
                Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                positionBundle.putString("userFullName", mUserFName);
                positionBundle.putInt("userAge", mUserAge);
                positionBundle.putString("userSex", mUserSex);
                positionBundle.putString("userCity", mUserCity);
                positionBundle.putString("userCountry", mUserCountry);
                positionBundle.putInt("userHeight", mUserHeight);
                positionBundle.putInt("userWeight", mUserWeight);
                sendIntent.putExtras(positionBundle);
                startActivity(sendIntent);
                break;
            }
            case 1: { // BMI Page
                double bmiValue = User.calculateBMI(mUserHeight, mUserWeight);
                Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                positionBundle.putDouble("bmi_data", bmiValue);
                sendIntent.putExtras(positionBundle);
                try {
                    startActivity(sendIntent);
                } catch (Exception ex) {
                    Log.e("Error", ex.getMessage());
                }
                break;
            }
            case 2: { //BMR Page
                // Add to position bundle
                double bmrValue = User.calculateBMR(mUserHeight,mUserWeight,mUserAge, mUserSex);
                Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                positionBundle.putDouble("bmr_data", bmrValue);
                sendIntent.putExtras(positionBundle);
                try{
                    startActivity(sendIntent);
                }catch (Exception e){
                    Log.e("Error", e.getMessage());
                }
                break;
            }
            case 3: { //Weather Page
                Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                positionBundle.putString("userCity", mUserCity);
                positionBundle.putString("userCountry", mUserCountry);
                sendIntent.putExtras(positionBundle);
                startActivity(sendIntent);
                break;
            }
            case 4: { // Hikes Page
                Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                positionBundle.putString("userCity", mUserCity);
                positionBundle.putString("userCountry", mUserCountry);
                sendIntent.putExtras(positionBundle);
                startActivity(sendIntent);
            }
            case 5: { // Goals Page
                Intent sendIntent = new Intent(this, ViewDetailActivity.class);
                positionBundle.putInt("userAge", mUserAge);
                positionBundle.putInt("userHeight", mUserHeight);
                positionBundle.putInt("userWeight", mUserWeight);
                positionBundle.putString("userSex", mUserSex);
                sendIntent.putExtras(positionBundle);
                startActivity(sendIntent);
            }
        }
    }

    // For EditUserData
    @Override
    public void onDataPass(String firstName, String lastName, int age, int height, int weight, String city, String country, Bundle thumbnailImage, String sex) {
        mUserFName = firstName;
        mUserLName = lastName;
        mUserFName = firstName + " " + lastName;
        mUserAge = age;
        mUserHeight = height;
        mUserWeight = weight;
        mUserCity = city;
        mUserCountry = country;
        mUserSex = sex;
        mUserProfilePic = thumbnailImage;
        isEditUser = false;

        Bitmap thumbnail = (Bitmap) thumbnailImage.get("data");
        mUser = new User(1, firstName, lastName, age, sex, thumbnail, city, country, height, weight);
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();



        mMasterListFrag = new MasterListFragment();
        fTrans.replace(R.id.fl_main_container_phone, mMasterListFrag, "frag_masterlist");

        Bundle headerBundle = new Bundle();
        headerBundle.putString("userFirstName", firstName);
        headerBundle.putBundle("userPic", thumbnailImage);

        mAppHeadFrag = new AppHeadFragment();
        mAppHeadFrag.setArguments(headerBundle);

        fTrans.replace(R.id.fl_header_phone, mAppHeadFrag, "app_header_frag");
        fTrans.commit();
    }
}