package com.example.wamapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AppHeadFragment extends Fragment {
    // Member Variables
    private String mFirstName;
    private TextView mTvFirstName;
    private ImageView mIvPicture;
    private Bundle pictureBundle;
    private Bitmap thumbNail;

    public AppHeadFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.header_fragment, container, false);

        mTvFirstName = (TextView) view.findViewById(R.id.tv_fn_head);
        mIvPicture = (ImageView) view.findViewById(R.id.iv_pic);

        mFirstName = getArguments().getString("userFirstName");
        pictureBundle = getArguments().getBundle("userPic");
        thumbNail = (Bitmap) pictureBundle.get("data");

        if(mFirstName != null) {
            mTvFirstName.setText("Hello " + mFirstName);
        }

        mIvPicture.setImageBitmap(thumbNail);

        return view;
    }
}
