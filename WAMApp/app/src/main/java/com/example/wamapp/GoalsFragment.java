package com.example.wamapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GoalsFragment extends Fragment
        implements View.OnClickListener {

    OnDataPass dataPasser;
    Spinner mSActive, mSWeight;
    String mUserSex, mUserActivityLevel;
    double mUserBMR, mUserGoal, mUserCalories;
    int mUserHeight, mUserAge, mUserWeight;
    TextView tvRecommendedCalories;
    Button bSubmit;

    public GoalsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface OnDataPass{
        void onDataPass(String activityLevel, double BMR, double dailyCalories, double goal);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_fragment, container, false);
        bSubmit = (Button) view.findViewById(R.id.submit_button);
        bSubmit.setOnClickListener(this);
        tvRecommendedCalories = (TextView) view.findViewById(R.id.tv_calories);
        mSActive = (Spinner) view.findViewById(R.id.active_spin);
        mSWeight = (Spinner) view.findViewById(R.id.weightgoal_spin);
        String calGoal = "";

        if(savedInstanceState != null) {
            calGoal = savedInstanceState.getString("goalTextView");
            mUserAge = savedInstanceState.getInt("userAge");
            mUserHeight = savedInstanceState.getInt("userHeight");
            mUserWeight = savedInstanceState.getInt("userWeight");
            mUserSex = savedInstanceState.getString("userSex");
            mUserBMR = savedInstanceState.getDouble("userBMR");
            mUserGoal = savedInstanceState.getDouble("userEnteredGoal");
            mUserCalories = savedInstanceState.getDouble("userCalories");
        } else {
            mUserAge = getArguments().getInt("userAge");
            mUserHeight = getArguments().getInt("userHeight");
            mUserWeight = getArguments().getInt("userWeight");
            mUserSex = getArguments().getString("userSex");
        }

        if(calGoal != "") {
            tvRecommendedCalories.setText(calGoal);
        }

        // Setup Activity Level Spinner
        String [] activityLevelOptions = new String[4];
        activityLevelOptions[0] = "Sedentary";
        activityLevelOptions[1] = "Lightly Active";
        activityLevelOptions[2] = "Moderately Active";
        activityLevelOptions[3] = "Very Active";
        final String[] finalActive = activityLevelOptions;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalActive);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSActive.setAdapter(adapter);
        mSActive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mUserActivityLevel = finalActive[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if(mUserActivityLevel.equals("")) {
                    mSActive.setSelection(2);
                } else {
                    for(int i = 0; i < finalActive.length; i++) {
                        if(mUserActivityLevel.equals(finalActive[i])) {
                            mSActive.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });

        String [] weightChange = new String[13];
        double temp = -3.0;
        for(int i = 0; i < 13; i++) {
            weightChange[i] = String.valueOf(temp);
            temp += 0.5;
        }
        final String[] finalWeightChange = weightChange;
        ArrayAdapter<String> goalAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightChange);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSWeight.setAdapter(goalAdapter);
        mSWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserGoal = Double.parseDouble(finalWeightChange[position]);
                if(mUserGoal > 2 || mUserGoal < -2) {
                    Toast.makeText(getActivity(), "Warning: Losing/Gaining more than 2 pounds", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                if(mUserGoal == 0) {
                    mSWeight.setSelection(6);
                }
                for(int i = 0; i < finalWeightChange.length; i++) {
                    if(mUserGoal == Double.parseDouble(finalWeightChange[i])) {
                        mSWeight.setSelection(i);
                        break;
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        mUserBMR = User.calculateBMR(mUserHeight, mUserWeight, mUserAge, mUserSex);
        mUserCalories = User.calculateCalories(mUserBMR, mUserActivityLevel, mUserGoal);
        int calories = (int) mUserCalories;
        int calorieLimit = mUserSex.equals("Male") ? 1200 : 1000;
        if(mUserCalories < calorieLimit) {
            Toast.makeText(getActivity(), "Warning: Low Calorie Level", Toast.LENGTH_SHORT).show();
        }
        tvRecommendedCalories.setText(calories + " cal");
        dataPasser.onDataPass(mUserActivityLevel, mUserBMR, mUserCalories, mUserGoal);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        String calGoal = tvRecommendedCalories.getText().toString();
        outState.putInt("userAge", mUserAge);
        outState.putInt("userHeight", mUserHeight);
        outState.putDouble("userWeight", mUserWeight);
        outState.putString("userSex", mUserSex);
        outState.putDouble("userBMR", mUserBMR);
        outState.putDouble("userEnteredGoal", mUserGoal);
        outState.putString("goalTextView", calGoal);
        outState.putDouble("userCalories", mUserCalories);

        super.onSaveInstanceState(outState);
    }
}
