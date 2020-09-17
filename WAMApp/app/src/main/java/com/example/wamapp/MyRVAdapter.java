package com.example.wamapp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class MyRVAdapter extends RecyclerView.Adapter<MyRVAdapter.ViewHolder> {
    private List<String> mListItems;
    private List<Integer> mImageItems;
    private Context mContext;
    private DataPasser mDataPasser;

    public MyRVAdapter(List<String> inputList, List<Integer> inputImages) {
        mListItems = inputList;
        mImageItems = inputImages;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        protected View itemLayout;
        protected TextView itemModText;
        protected ImageView itemModImage;

        public ViewHolder(View view) {
            super(view);
            itemLayout = view;
            itemModText = (TextView) view.findViewById(R.id.modText);
            itemModImage = (ImageView) view.findViewById(R.id.modImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        try {
            mDataPasser = (DataPasser) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + " must implement OnDataPass");
        }

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View myView = layoutInflater.inflate(R.layout.moduleitem_fragment, parent, false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemModText.setText(mListItems.get(position));
        holder.itemModImage.setImageResource(mImageItems.get(position));
        holder.itemLayout.setOnClickListener(new View.OnClickListener(){
                                                 @Override
                                                 public void onClick(View view) {
                                                     mDataPasser.passData(position);
                                                 }
                                             }
        );
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    public static interface DataPasser {
        public void passData(int position);
    }
}
