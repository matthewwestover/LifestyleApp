package com.example.wamapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserProfileFragment extends Fragment {
    TextView mTVFullName, mTVAge, mTVSex, mTVCountry, mTVCity, mTVHeight, mTVWeight;
    private UserViewModel mUserViewModel;
    private User mCurrentUser;

    public UserProfileFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            if(user!=null) {
                mTVFullName.setText(mUserViewModel.getUser().getValue().getFullName());
                mTVAge.setText("" + mUserViewModel.getUser().getValue().getAge());
                mTVSex.setText(mUserViewModel.getUser().getValue().getSex());
                mTVCountry.setText(mUserViewModel.getUser().getValue().getCountry());
                mTVCity.setText(mUserViewModel.getUser().getValue().getCity());
                mTVHeight.setText("" + mUserViewModel.getUser().getValue().getHeight());
                mTVWeight.setText("" + mUserViewModel.getUser().getValue().getWeight());
            }
        }
    };

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
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        mUserViewModel.getUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                mCurrentUser = user;
                mTVFullName.setText(mCurrentUser.getFullName());
                mTVAge.setText("" + mCurrentUser.getAge());
                mTVSex.setText(mCurrentUser.getSex());
                mTVCountry.setText(mCurrentUser.getCountry());
                mTVCity.setText(mCurrentUser.getCity());
                mTVHeight.setText("" + mCurrentUser.getHeight());
                mTVWeight.setText("" + mCurrentUser.getWeight());
            }
        });

        return view;
    }
}