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
    String mFName, mLName;
    TextView mTVAge, mTVSex, mTVCountry, mTVCity, mTVHeight,
            mTVWeight, mTVFirstName, mTVLastName;

    public UserProfileFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mTVFirstName = view.findViewById(R.id.tv_fname);
        mTVCity = view.findViewById(R.id.city_name);
        mTVCountry = view.findViewById(R.id.country);

        mFName = getArguments().getString("userFName");
        Log.i("mFName",mFName);

        mTVFirstName.setText((mFName));
//        tvCityName.setText(cityName);

        return view;
    }
}