package com.example.wamapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HikesFragment extends Fragment implements View.OnClickListener {

    private Button mSearchButton;
    private String mCity, mCountry;

    public HikesFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hikes_fragment, container, false);

        mSearchButton = (Button) view.findViewById(R.id.hike_submit_button);
        mSearchButton.setOnClickListener(this);
        mCity = getArguments().getString("userCity");
        mCountry = getArguments().getString("userCountry");

        return view;
    }

    @Override
    public void onClick(final View view) {
        // We have to grab the search term and construct a URI object from it.
        // Probably want to look into setting long/lat or finding out how to get device permissions to get it and set this uri different
        Uri searchUri = Uri.parse("geo:40.767778,-111.845205?q=" + " Hikes in " + mCity + " " + mCountry);

        //Create the implicit intent
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);

        //If there's an activity associated with this intent, launch it
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
