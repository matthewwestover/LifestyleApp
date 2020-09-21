package com.example.wamapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BmrFragment extends Fragment {
    private TextView mTvBMRData;
    private Double bmrValue;
    private String mStringBMRData, bmrValueString;

    public BmrFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.bmr_fragment, container, false);

        //Get the views
        mTvBMRData = fragmentView.findViewById(R.id.tv_bmr_data);

        if (savedInstanceState != null) {
            bmrValueString = savedInstanceState.getString("BMR_TEXT");
        }
        else {
            bmrValue = getArguments().getDouble("bmr_data");
            bmrValueString = Double.toString(bmrValue);
        }
        //Set the text in the fragment
        mTvBMRData.setText("" + bmrValueString);

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Get the strings
        mStringBMRData = mTvBMRData.getText().toString();
        //Put them in the outgoing Bundle
        outState.putString("BMR_TEXT",mStringBMRData);
        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }
}


