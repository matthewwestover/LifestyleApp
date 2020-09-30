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

public class EditUserFragment extends Fragment
        implements View.OnClickListener {
    private EditText mETFirstName, mETLastName;
    private Spinner mSAge, mSSex, mSCountry, mSCity, mSHeight, mSWeight;
    private Button mBtPicture, mBtSubmit;
    private String mFirstName, mLastName, mCity, mCountry, mSex;
    int mAge, mHeight, mWeight;
    private Bitmap photo;
    Bundle thumbnailImage;
    OnDataPass dataPasser;

    public EditUserFragment() {
    }

    //Callback interface
    public interface OnDataPass{
        void onDataPass(String firstName, String lastName, int age, int height, int weight, String city, String country, Bundle thumbnailImage, String sex);
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

        if (savedInstanceState != null) {
            mFirstName = savedInstanceState.getString("userFirstName");
            mETFirstName.setText(mFirstName);
            mLastName = savedInstanceState.getString("userLastName");
            mETLastName.setText(mLastName);
            mCity = savedInstanceState.getString("userCity");
            mCountry = savedInstanceState.getString("userCountry");
            mSex = savedInstanceState.getString("userSex");
            mAge = savedInstanceState.getInt("userAge");
            mHeight = savedInstanceState.getInt("userHeight");
            mWeight = savedInstanceState.getInt("userWeight");
            thumbnailImage = savedInstanceState.getBundle("userPicture");
        }

        Intent intent = getActivity().getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mFirstName = extras.getString("userFirstName");
            mETFirstName.setText(mFirstName);
            mLastName = extras.getString("userLastName");
            mETLastName.setText(mLastName);
            mCity = extras.getString("userCity");
            mCountry = extras.getString("userCountry");
            mSex = extras.getString("userSex");
            mAge = extras.getInt("userAge");
            mHeight = extras.getInt("userHeight");
            mWeight = extras.getInt("userWeight");
            thumbnailImage = extras.getBundle("userPicture");
        }

        //set Age spinner
        String[] ageOptions = new String[100];
        for(int i = 0; i < 100; i++) {
            ageOptions[i] = String.valueOf(i + 13);
        }
        final String[] finalAgeOptions = ageOptions;
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalAgeOptions);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSAge.setAdapter(ageAdapter);
        mSAge.setSelection(7);
        mSAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mAge = Integer.parseInt(finalAgeOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSAge.setSelection(1);
            }
        });

        //set Sex spinner
        String[] sexOptions = new String[2];
        sexOptions[0] = "Male";
        sexOptions[1] = "Female";
        final String[] finalSexOptions = sexOptions;
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalSexOptions);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSSex.setAdapter(sexAdapter);
        if(mSex != null) {
            if (mSex.equals("Female")) {
                mSSex.setSelection(1);
            }
        } else {
            mSSex.setSelection(0);
        }
        mSSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mSex = finalSexOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSSex.setSelection(0);
            }
        });

        //set Country spinner
        String[] countryOptions = new String[3];
        countryOptions[0] = "USA";
        countryOptions[1] = "CA";
        countryOptions[2] = "MX";
        final String[] finalCountryOptions = countryOptions;
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalCountryOptions);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSCountry.setAdapter(countryAdapter);
        if(mCountry != null) {
            int index = 0;
            for(int i = 0; i < countryOptions.length; i++) {
                if(countryOptions[i].equals(mCountry)) {
                    index = i;
                    break;
                }
            }
            mSCountry.setSelection(index);
        } else {
            mSCountry.setSelection(0);
        }
        mSCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mCountry = finalCountryOptions[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSCountry.setSelection(0);
            }
        });

        //set City spinner
        String[] cityOptions = new String[6];
        cityOptions[0] = "Los Angeles";
        cityOptions[1] = "Salt Lake City";
        cityOptions[2] = "Chicago";
        cityOptions[3] = "New York City";
        cityOptions[4] = "Toronto";
        cityOptions[5] = "Mexico City";
        final String[] finalCityOptions = cityOptions;
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalCityOptions);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSCity.setAdapter(cityAdapter);
        if(mCity != null) {
            int index = 0;
            for(int i = 0; i < cityOptions.length; i++) {
                if(cityOptions[i].equals(mCity)) {
                    index = i;
                    break;
                }
                mSCity.setSelection(index);
            }
        } else {
            mSCity.setSelection(0);
        }
        mSCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mCity = finalCityOptions[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSCity.setSelection(0);
            }
        });

        //Set Height in inches spinner
        String[] heightOptions = new String[96];
        for(int i = 0; i < 96; i++) {
            heightOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalHeightOptions = heightOptions;
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalHeightOptions);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSHeight.setAdapter(heightAdapter);
        if(mHeight > 1) {
            mSHeight.setSelection(mHeight - 1);
        } else {
            mSHeight.setSelection(59);
        }
        mSHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mHeight = Integer.parseInt(finalHeightOptions[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSHeight.setSelection(65);
            }
        });

        //Set Weight in lbs spinner
        String[] weightOptions = new String[400];
        for(int i = 0; i < 400; i++) {
            weightOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalWeightOptions = weightOptions;
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightOptions);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSWeight.setAdapter(weightAdapter);
        if(mWeight > 1) {
            mSWeight.setSelection(mWeight - 1);
        } else {
            mSWeight.setSelection(99);
        }
        mSWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mWeight = Integer.parseInt(finalWeightOptions[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSWeight.setSelection(149);
            }
        });

        return view;
    }

    // Get the Photo
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            thumbnailImage = data.getExtras();
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
                } else if (thumbnailImage == null) {
                    Toast.makeText(getActivity(), "You need to take a photo!", Toast.LENGTH_SHORT).show();
                } else {
                    //Remove any leading spaces or tabs on the first and last name
                    mFirstName = mFirstName.replaceAll("^\\s+", "");
                    mLastName = mLastName.replaceAll("^\\s+", "");

                    dataPasser.onDataPass(mFirstName, mLastName, mAge, mHeight, mWeight, mCity, mCountry, thumbnailImage, mSex);
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
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Collect input data
        mFirstName = mETFirstName.getText().toString();
        mLastName = mETLastName.getText().toString();

        //Put them in the outgoing Bundle
        outState.putString("userFirstName", mFirstName);
        outState.putString("userLastName", mLastName);
        outState.putBundle("userPicture", thumbnailImage);
        outState.putInt("userAge", mAge);
        outState.putInt("userWeight", mWeight);
        outState.putInt("userHeight", mHeight);
        outState.putString("userCity", mCity);
        outState.putString("userCountry", mCountry);
        outState.putString("userSex", mSex);

        super.onSaveInstanceState(outState);
    }

    // Restore data
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        //Restore the view hierarchy automatically
        super.onViewStateRestored(savedInstanceState);

        //check and make sure bundle is not null first
        if(savedInstanceState != null) {
            //Restore stuff

            if (savedInstanceState.getString("userFirstName") == null) {
                mETFirstName.setText("");
            } else {
                mETFirstName.setText(savedInstanceState.getString("userFirstName"));
            }
            if (savedInstanceState.getString("userLastName") == null) {
                mETLastName.setText("");
            } else {
                mETLastName.setText(savedInstanceState.getString("userLastName"));
            }
            mSex = savedInstanceState.getString("userSex");
            mCity = savedInstanceState.getString("userCity");
            mCountry = savedInstanceState.getString("userCountry");
            mAge = savedInstanceState.getInt("userAge");
            if(savedInstanceState.getParcelable("userPicture") != null) {
                thumbnailImage = savedInstanceState.getParcelable("userPicture");
            }
        }
    }
}
