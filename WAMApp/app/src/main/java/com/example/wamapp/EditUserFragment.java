package com.example.wamapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditUserFragment extends Fragment
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText mETFirstName, mETLastName;
    private Spinner mSAge, mSSex, mSCountry, mSCity, mSHeight, mSWeight;
    private Button mBtPicture, mBtSubmit;
    private String mFirstName, mLastName;
    private Bitmap photo;
    OnDataPass dataPasser;

    public EditUserFragment() {
    }

    //Callback interface
    public interface OnDataPass {
        public void onDataPass(String[] data);

        void onEditUserSubmit();
    }

    public void passData(String[] data) {
        dataPasser.onDataPass(data);
        dataPasser.onEditUserSubmit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataPasser = (OnDataPass) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnDataPass");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edituser_fragment, container, false);
        // Get views
        mETFirstName = (EditText) view.findViewById(R.id.et_fname);
        mETLastName = (EditText) view.findViewById(R.id.et_lname);
        mSAge = (Spinner) view.findViewById(R.id.age_spin);
        mSSex = (Spinner) view.findViewById(R.id.sex_spin);
        mSCountry = (Spinner) view.findViewById(R.id.country_spin);
        mSCity = (Spinner) view.findViewById(R.id.city_spin);
        mSHeight = (Spinner) view.findViewById(R.id.height_spin);
        mSWeight = (Spinner) view.findViewById(R.id.weight_spin);
        mBtPicture = (Button) view.findViewById(R.id.pic_button);
        mBtSubmit = (Button) view.findViewById(R.id.sub_button);
        mBtPicture.setOnClickListener(this);
        mBtSubmit.setOnClickListener(this);

        //set Age spinner
        Integer[] ages = new Integer[100];
        for (int i = 0; i < 100; i++) {
            ages[i] = i + 1; //
        }
        ArrayAdapter<Integer> ageDA = new ArrayAdapter<>(mSAge.getContext(), android.R.layout.simple_spinner_dropdown_item, ages);
        ageDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSAge.setAdapter(ageDA);

        //set Sex spinner
        List<String> sexes = new ArrayList<>();
        sexes.add("Female");
        sexes.add("Male");
        ArrayAdapter<String> sexDA = new ArrayAdapter<String>
                (mSSex.getContext(), android.R.layout.simple_spinner_dropdown_item, sexes);
        sexDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSSex.setAdapter(sexDA);

        //set Country spinner
        List<String> countries = new ArrayList<>(); // Can add more later?
        countries.add("USA");
        countries.add("CA");
        countries.add("MX");
        ArrayAdapter<String> countryDA = new ArrayAdapter<String>
                (mSCountry.getContext(), android.R.layout.simple_spinner_dropdown_item, countries);
        countryDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSCountry.setAdapter(countryDA);

        //set City spinner
        List<String> cities = new ArrayList<>(); // Can add more later?
        cities.add("Los Angeles");
        cities.add("Salt Lake City");
        cities.add("Chicago");
        cities.add("New York City");
        cities.add("Toronto");
        cities.add("Mexico City");
        ArrayAdapter<String> cityDA = new ArrayAdapter<String>
                (mSCity.getContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        cityDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSCity.setAdapter(cityDA);

        //Set Height in inches spinner
        mSHeight = (Spinner) view.findViewById(R.id.height_spin);
        List<Integer> height = new ArrayList<>();
        for (int i = 48; i < 97; i++) {
            height.add(i);
        }
        ArrayAdapter<Integer> heightDA = new ArrayAdapter<Integer>
                (mSHeight.getContext(), android.R.layout.simple_spinner_dropdown_item, height);
        heightDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSHeight.setAdapter(heightDA);

        //Set Weight in lbs spinner
        mSWeight = (Spinner) view.findViewById(R.id.weight_spin);
        List<Integer> weight = new ArrayList<>();
        for (int i = 100; i < 400; i++) {
            weight.add(i);
        }
        ArrayAdapter<Integer> weightDA = new ArrayAdapter<Integer>
                (mSWeight.getContext(), android.R.layout.simple_spinner_dropdown_item, weight);

        weightDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSWeight.setAdapter(weightDA);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        String[] selected = new String[1];
        selected[0] = item;
        dataPasser.onDataPass(selected);
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            Bundle thumbnailImage = data.getExtras();
            photo = (Bitmap) thumbnailImage.get("data");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sub_button: {
                mFirstName = mETFirstName.getText().toString();
                mLastName = mETLastName.getText().toString();

                //Check if the EditText string is empty
                if (mFirstName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a first name!", Toast.LENGTH_SHORT).show();
                } else if (mLastName.matches("")) {
                    Toast.makeText(getActivity(), "Enter a last name!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Good job!", Toast.LENGTH_SHORT).show();
                    //Remove any leading spaces or tabs on the first and last name
                    mFirstName = mFirstName.replaceAll("^\\s+", "");
                    mLastName = mLastName.replaceAll("^\\s+", "");
                    String[] data = new String[9];
                    data[0] = mFirstName;
                    data[1] = mLastName;
                    data[2] = mSAge.toString();
                    data[3] = mSSex.toString();
                    data[4] = mSCountry.toString();
                    data[5] = mSCity.toString();
                    data[6] = mSHeight.toString();
                    data[7] = mSWeight.toString();
                    data[8] = photo.toString();

                    passData(data);
                }
                break;
            }
            case R.id.pic_button: {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, 1);
                }
            }
        }
    }

    // Save data for rotation
    // No idea if its right
    @Override
    public void onSaveInstanceState(Bundle outState) {
        mFirstName = mETFirstName.getText().toString();
        mLastName = mETLastName.getText().toString();

        super.onSaveInstanceState(outState);
    }
}
