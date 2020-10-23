package com.example.wamapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MasterListFragment extends Fragment
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> mItemList;
    private ArrayList<Integer> mImageList;
    private static Bundle mBundleRecyclerViewState;

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
        mItemList.add("Steps");


        mImageList = new ArrayList<>();
        mImageList.add(R.drawable.male);
        mImageList.add(R.drawable.bmi);
        mImageList.add(R.drawable.bmr);
        mImageList.add(R.drawable.weather);
        mImageList.add(R.drawable.trails);
        mImageList.add(R.drawable.goals);
        mImageList.add(R.drawable.step);

        //Get the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_Master);

        //Tell Android that we know the size of the recyclerview doesn't change
        mRecyclerView.setHasFixedSize(true);

        //Set the layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        //Set the adapter
        mAdapter = new MyRVAdapter(mItemList, mImageList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable("recycler_state", listState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable("recycler_state");
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
