package com.example.wamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
       // implements UserFragment.OnDataPass
{
    private String mFirstName, mLastName, mAge, mSex, mLocation, mHeight, mWeight, mBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create Fragment
       // UserFragment userFragment = new UserFragment();

        //Replace User Fragment Container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        //fTrans.replace(R.id.user_frag, userFragment, "user_frag");
        fTrans.commit();
    }
   // @Override
//    public void onDataPass(String[] data)
//    {
//
//    }

}