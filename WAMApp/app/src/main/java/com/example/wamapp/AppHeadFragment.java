package com.example.wamapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class AppHeadFragment extends Fragment implements View.OnClickListener {

    private TextView mTvFirstName;
    private ImageView mIvPicture;
    private Button mEditButton;
    private UserViewModel mUserViewModel;
    private User mCurrentUser;
    private OnDataPass dataPasser;

    public AppHeadFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    public void passData() {
        dataPasser.onEditUserClick();
    }

    public interface OnDataPass {
        void onEditUserClick();
    }

    @Override
    public void onClick(View view) {
        passData();
    }

    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                mTvFirstName.setText(mUserViewModel.getUser().getValue().getFirstName());
                mIvPicture.setImageBitmap(User.getPhotoBitmap(mUserViewModel.getUser().getValue().getPhotoData()));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.header_fragment, container, false);

        mTvFirstName = (TextView) view.findViewById(R.id.tv_fn_head);
        mIvPicture = (ImageView) view.findViewById(R.id.iv_pic);
        mEditButton = view.findViewById(R.id.prof_edit_button);
        mEditButton.setOnClickListener(this);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserViewModel.getUser().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                mCurrentUser = user;
                mTvFirstName.setText("Hello, " + mCurrentUser.getFirstName());
                mIvPicture.setImageBitmap(User.getPhotoBitmap(mCurrentUser.getPhotoData()));
            }
        });

        return view;
    }
}
