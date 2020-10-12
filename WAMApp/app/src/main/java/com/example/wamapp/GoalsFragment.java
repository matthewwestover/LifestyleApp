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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class GoalsFragment extends Fragment implements View.OnClickListener {

    Spinner mSActive, mSWeight;
    TextView tvRecommendedCalories;
    Button bSubmit;
    private UserViewModel mUserViewModel;
    private User mCurrentUser;

    public GoalsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                mCurrentUser = user;
                tvRecommendedCalories.setText(String.valueOf(mCurrentUser.getCalories()));
            }
        }
    };

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
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mUserViewModel.getUser().observe(this, userObserver);

        if(savedInstanceState != null) {
            calGoal = String.valueOf(mUserViewModel.getUser().getValue().getCalories());
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
                mUserViewModel.getUser().getValue().setActiveLevel(finalActive[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mSActive.setSelection(2);
            }
        });

        // Setup Weight Loss Spinner
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
                mUserViewModel.getUser().getValue().setWeightGoal(Double.parseDouble(finalWeightChange[position]));
                if(mUserViewModel.getUser().getValue().getWeightGoal() > 2 || mUserViewModel.getUser().getValue().getWeightGoal() < -2) {
                    Toast.makeText(getActivity(), "Warning: Losing/Gaining more than 2 pounds", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSWeight.setSelection(6);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        String mUserSex = mUserViewModel.getUser().getValue().getSex();
        double userCalories = User.calculateCalories(mUserViewModel.getUser().getValue().getBMR(), mUserViewModel.getUser().getValue().getActiveLevel(), mUserViewModel.getUser().getValue().getWeightGoal());
        mUserViewModel.getUser().getValue().setCalories(userCalories);
        int calories = (int) userCalories;
        String calString = Integer.toString(calories);
        int calorieLimit = mUserSex.equals("Male") ? 1200 : 1000;
        if(userCalories < calorieLimit) {
            Toast.makeText(getActivity(), "Warning: Low Calorie Level", Toast.LENGTH_SHORT).show();
        }
        mUserViewModel.dumpInDB(mUserViewModel.getUser().getValue());
        tvRecommendedCalories.setText(String.valueOf(calories));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
