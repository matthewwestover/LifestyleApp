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

public class BmiFragment extends Fragment {
    private TextView mTvBMIData;
    private Double bmiValue;
    private String mStringBMIData, bmiValueString;

    public BmiFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.bmi_fragment, container, false);

        //Get the views
        mTvBMIData = fragmentView.findViewById(R.id.tv_bmi_data);

        if (savedInstanceState != null) {
            bmiValueString = savedInstanceState.getString("BMI_TEXT");
        }
        else {
            bmiValue = getArguments().getDouble("bmi_data");
            bmiValueString = Double.toString(bmiValue);
        }
        //Set the text in the fragment
        mTvBMIData.setText("" + bmiValueString);

        return fragmentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Get the strings
        mStringBMIData = mTvBMIData.getText().toString();
        //Put them in the outgoing Bundle
        outState.putString("BMI_TEXT",mStringBMIData);
        //Save the view hierarchy
        super.onSaveInstanceState(outState);
    }
}
