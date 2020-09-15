package com.example.wamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
        implements EditUserFragment.OnDataPass {
    private String mFirstName, mLastName, mAge, mSex, mLocation, mHeight, mWeight, mPhoto;
    private User user;
    private Boolean isEditUser = false;
    private Boolean userExists = false;
    private int containerBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tablet Check
        containerBody = isTablet() ? R.id.fl_main_container_tablet : R.id.fl_main_container_phone;

        changeDisplay();
    }

    @Override
    public void onDataPass(String[] data) {
        mFirstName = data[0];
        mLastName = data[1];
        mAge = data[2];
        mSex = data[3];
        mLocation = data[4];
        mHeight = data[5];
        mWeight = data[6];
        mPhoto = data[7];
    }

    public void changeDisplay() {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTrans = fManager.beginTransaction();

        if (!userExists) {
            fTrans.replace(containerBody, new EditUserFragment(), "edituser_frag");
        } else {
            fTrans.replace(containerBody, new MasterListFragment(), "masterListFragment");
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
}