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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class BmrFragment extends Fragment {
    private TextView mTvBMRData;
    private UserViewModel mUserViewModel;

    public BmrFragment() {
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
                double bmrValue = user.getBMR();
                String bmrValueString = Double.toString(bmrValue);
                mTvBMRData.setText(bmrValueString);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bmr_fragment, container, false);
        mTvBMRData = view.findViewById(R.id.tv_bmr_data);

        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mUserViewModel.getUser().observe(this, userObserver);

        double bmrValue = 0.0;
        String bmrValueString = Double.toString(bmrValue);

        mTvBMRData.setText(bmrValueString);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}


