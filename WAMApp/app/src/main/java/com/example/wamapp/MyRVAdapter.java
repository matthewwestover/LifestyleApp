package com.example.wamapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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
    private  List <String> mData;

    public MyRVAdapter(List<String> inputList, List<Integer> inputImages, List<String> data) {
        mListItems = inputList;
        mImageItems = inputImages;
        mData = data;
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
            throw new ClassCastException(mContext.toString() + " must implement HeaderDataPass");
        }

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View myView = layoutInflater.inflate(R.layout.moduleitem_fragment, parent, false);
        return new ViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemModText.setText(mListItems.get(position));
        holder.itemModImage.setImageResource(mImageItems.get(position));
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     if(position == 4){
                                                         //We have to grab the search term and construct a URI object from it.
                                                         Uri searchUri = Uri.parse("geo:40.767778,-111.845205?q="+ " Hikes in " + "Salt Lake City" + " "+ "USA");

                                                         //Create the implicit intent
                                                         Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);


                                                         //If there's an activity associated with this intent, launch it
                                                         if(mapIntent.resolveActivity(mContext.getPackageManager())!=null) {
                                                             mContext.startActivity(mapIntent);

                                                         }
                                                     }
                                                     mDataPasser.passData(position);
                                                 }
                                             }
        );
    }

    public void remove(int position) {
        mListItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {

        return mListItems.size();
    }

    public static interface DataPasser {
        public void passData(int position);
    }
}
