package com.example.wamapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class UserProfileFragment extends Fragment {
    String mFullName, mSex, mCountry, mCity;
    int mAge, mHeight, mWeight;
    TextView mTVFullName, mTVAge, mTVSex, mTVCountry, mTVCity, mTVHeight, mTVWeight;

    public UserProfileFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mTVFullName = view.findViewById(R.id.tv_fullname);
        mTVAge = view.findViewById(R.id.tv_age);
        mTVSex = view.findViewById(R.id.tv_sex);
        mTVCountry = view.findViewById(R.id.tv_country);
        mTVCity = view.findViewById(R.id.tv_city);
        mTVHeight = view.findViewById(R.id.tv_height);
        mTVWeight = view.findViewById(R.id.tv_weight);

        if (getArguments() != null) {
            mFullName = getArguments().getString("userFullName");
            mAge = getArguments().getInt("userAge");
            mSex = getArguments().getString("userSex");
            mCountry = getArguments().getString("userCountry");
            mCity = getArguments().getString("userCity");
            mHeight = getArguments().getInt("userHeight");
            mWeight = getArguments().getInt("userWeight");
        }


        mTVFullName.setText(mFullName);
        mTVAge.setText("" + mAge);
        mTVSex.setText(mSex);
        mTVCountry.setText(mCountry);
        mTVCity.setText(mCity);
        mTVHeight.setText("" + mHeight);
        mTVWeight.setText("" + mWeight);

        return view;
    }
}