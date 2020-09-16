package com.example.wamapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MasterListFragment extends Fragment
    //implements View.OnClickListener
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> mItemList;
    private ArrayList<Integer> mImageList;
    private String mCity, mCountry;
    private ArrayList<String> mDataList;

    public MasterListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.masterlist_fragment, container, false);
        mItemList = new ArrayList<>();
        mItemList.add("Profile");
        mItemList.add("BMI");
        mItemList.add("BMR");
        mItemList.add("Weather");
        mItemList.add("Trails");
        mItemList.add("Goals");

        mImageList = new ArrayList<>();
        mImageList.add(R.drawable.male);
        mImageList.add(R.drawable.bmi);
        mImageList.add(R.drawable.bmr);
        mImageList.add(R.drawable.weather);
        mImageList.add(R.drawable.trails);
        mImageList.add(R.drawable.goals);

        //Get the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_Master);

        //Tell Android that we know the size of the recyclerview doesn't change
        mRecyclerView.setHasFixedSize(true);

        //Set the layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        //Get the data that was sent in
        mCity = getArguments().getString("STATE_DATA");
        mCountry = getArguments().getString("COUNTRY_DATA");
        mDataList = new ArrayList<>();
        mDataList.add(mCity);
        mDataList.add(mCountry);

        //Set the adapter
        mAdapter = new MyRVAdapter(mItemList, mImageList, mDataList);
        mRecyclerView.setAdapter(mAdapter);





        return view;
    }

}
