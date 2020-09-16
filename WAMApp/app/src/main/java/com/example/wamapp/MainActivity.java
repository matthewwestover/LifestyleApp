package com.example.wamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements EditUserFragment.OnDataPass, MyRVAdapter.DataPasser {
    private String mFirstName, mLastName, mAge, mSex, mState, mCountry, mHeight, mWeight, mPhoto;
    private User user;
    private Boolean isEditUser = false;
    private Boolean userExists = false;
    private int containerBody;
    private MasterListFragment mMasterListFragment = new MasterListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tablet Check
        containerBody = isTablet() ? R.id.fl_list_tablet : R.id.fl_main_container_phone;

        changeDisplay();
    }

    @Override
    public void onDataPass(String[] data) {
        mFirstName = data[0];
        mLastName = data[1];
        mAge = data[2];
        mSex = data[3];
        mState = data[4];
        mCountry = data[5];
        mHeight = data[6];
        mWeight = data[7];
        mPhoto = data[8];
    }

    public void changeDisplay() {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTrans = fManager.beginTransaction();

        if (!userExists) {
            fTrans.replace(containerBody, new EditUserFragment(), "edituser_frag");
        } else {
            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putString("STATE", mState);
            fragmentBundle.putString("COUNTRY", mCountry);
            mMasterListFragment.setArguments(fragmentBundle);
            fTrans.replace(containerBody, mMasterListFragment, "masterListFragment");
        }
        fTrans.commit();
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    @Override
    public void onEditUserSubmit() {
        isEditUser = false;
        userExists = true;
        changeDisplay();
    }

    // For MasterListFragment
    @Override
    public void passData(int position) {
        Bundle positionBundle = new Bundle();
        positionBundle.putInt("click_position", position);

//        Intent sendIntent = new Intent(this, EditUserFragment.class);
//        sendIntent.putExtras(positionBundle);
//        startActivity(sendIntent);
    }


}