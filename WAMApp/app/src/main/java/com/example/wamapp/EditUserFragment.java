package com.example.wamapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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

public class EditUserFragment extends Fragment implements View.OnClickListener {
    private EditText mETFirstName, mETLastName;
    private Spinner mSAge, mSSex, mSCountry, mSCity, mSHeight, mSWeight;
    private Button mBtPicture, mBtSubmit;
    private String mFirstName, mLastName;
    private UserViewModel mUserViewModel;
    Bundle thumbnailImage;
    OnDataPass dataPasser;
    private String[] countryOptions = new String[3];
    private String[] cityOptions = new String[6];
    private String[] sexOptions = new String[2];
    private String[] ageOptions = new String[100];
    private String[] heightOptions = new String[96];
    private String[] weightOptions = new String[400];

    public EditUserFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    public void passData() {
        dataPasser.onEditUserSubmit();
    }

    public interface OnDataPass {
        void onEditUserSubmit();
    }

    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            if (user != null) {
                observeUserAge();
                observeUserCity();
                observeUserWeight();
                observeUserHeight();
                observeUserCountry();
                observeUserSex();
                observeUserName();
                observeUserProfilePic();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edituser_fragment, container, false);
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mUserViewModel.getUser().observe(this, userObserver);

        VoidAsyncTask task = mUserViewModel.getNumberOfUserInDatabase();
        task.execute();

        int numUsersInDB = 0;
        try {
            numUsersInDB = (int) task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get Views
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

        if (mUserViewModel.getUser().getValue() != null) {
            observeUserName();
            observeUserProfilePic();
        }

        buildAgeSpinner();
        buildWeightSpinner();
        buildHeightSpinner();
        buildCitySpinner();
        buildCountrySpinner();
        buildSexSpinner();

        return view;
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
                } else if (mUserViewModel.getUser().getValue().getPhotoData() == null) {
                    Toast.makeText(getActivity(), "You need to take a photo!", Toast.LENGTH_SHORT).show();
                } else {
                    //Remove any leading spaces or tabs on the first and last name
                    mFirstName = mFirstName.replaceAll("^\\s+", "");
                    mLastName = mLastName.replaceAll("^\\s+", "");
                    double bmi = User.calculateBMI(mUserViewModel.getUser().getValue().getHeight(), mUserViewModel.getUser().getValue().getWeight());
                    mUserViewModel.getUser().getValue().setBMI(bmi);
                    double bmr = User.calculateBMR(mUserViewModel.getUser().getValue().getHeight(), mUserViewModel.getUser().getValue().getWeight(), mUserViewModel.getUser().getValue().getAge(), mUserViewModel.getUser().getValue().getSex());
                    mUserViewModel.getUser().getValue().setBMR(bmr);
                    mUserViewModel.getUser().getValue().setFirstName(mFirstName);
                    mUserViewModel.getUser().getValue().setLastName(mLastName);
                    mUserViewModel.dumpInDB(mUserViewModel.getUser().getValue());
                    passData();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mFirstName = mETFirstName.getText().toString();
        mLastName = mETLastName.getText().toString();

        if(mUserViewModel.getUser().getValue() != null) {
            mUserViewModel.getUser().getValue().setFirstName(mFirstName);
            mUserViewModel.getUser().getValue().setLastName(mLastName);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode == getActivity().RESULT_OK){
            Bundle thumbnailImage = data.getExtras();
            mUserViewModel.getUser().getValue().setProfileImageData((Bitmap)thumbnailImage.get("data"));
        }
    }

    public void buildAgeSpinner() {
        for(int i = 0; i < 100; i++) {
            ageOptions[i] = String.valueOf(i + 13);
        }
        final String[] finalAgeOptions = ageOptions;
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalAgeOptions);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSAge.setAdapter(ageAdapter);

        mSAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setAge(Integer.parseInt(finalAgeOptions[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSAge.setSelection(1);
            }
        });
    }

    public void buildSexSpinner() {
        sexOptions[0] = "Male";
        sexOptions[1] = "Female";
        final String[] finalSexOptions = sexOptions;
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalSexOptions);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSSex.setAdapter(sexAdapter);
        mSSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setSex(finalSexOptions[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSSex.setSelection(0);
            }
        });
    }
    public void buildCitySpinner() {
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
        mSCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setCity(finalCityOptions[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSCity.setSelection(0);
            }
        });
    }
    public void buildCountrySpinner() {
        countryOptions[0] = "USA";
        countryOptions[1] = "CA";
        countryOptions[2] = "MX";
        final String[] finalCountryOptions = countryOptions;
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalCountryOptions);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSCountry.setAdapter(countryAdapter);
        mSCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setCountry(finalCountryOptions[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSCountry.setSelection(0);
            }
        });
    }
    public void buildHeightSpinner() {
        for(int i = 0; i < 96; i++) {
            heightOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalHeightOptions = heightOptions;
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalHeightOptions);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSHeight.setAdapter(heightAdapter);
        mSHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setHeight(Integer.parseInt(finalHeightOptions[position]));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSHeight.setSelection(65);
            }
        });
    }
    public void buildWeightSpinner() {
        for(int i = 0; i < 400; i++) {
            weightOptions[i] = String.valueOf(i + 1);
        }
        final String[] finalWeightOptions = weightOptions;
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, finalWeightOptions);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSWeight.setAdapter(weightAdapter);

        mSWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                mUserViewModel.getUser().getValue().setWeight(Integer.parseInt(finalWeightOptions[position]));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                mSWeight.setSelection(149);
            }
        });
    }

    public void observeUserAge() {
        if (mUserViewModel.getUser().getValue().getAge() > 1) {
            mSAge.setSelection(mUserViewModel.getUser().getValue().getAge() - 1);
        } else {
            mSAge.setSelection(12);
            mUserViewModel.getUser().getValue().setAge(13);
        }
    }
    public void observeUserWeight() {
        if (mUserViewModel.getUser().getValue().getWeight() > 1) {
            mSWeight.setSelection(mUserViewModel.getUser().getValue().getWeight() - 1);
        } else {
            mSWeight.setSelection(99);
            mUserViewModel.getUser().getValue().setWeight(100);
        }
    }
    public void observeUserHeight() {
        if (mUserViewModel.getUser().getValue().getHeight() > 1) {
            mSHeight.setSelection(mUserViewModel.getUser().getValue().getHeight() - 1);
        } else {
            mSHeight.setSelection(65);
            mUserViewModel.getUser().getValue().setHeight(65);
        }
    }
    public void observeUserCity() {
        if (mUserViewModel.getUser().getValue().getCity() != null) {
            int index = 0;
            for (int i = 0; i < cityOptions.length; i++) {
                if (cityOptions[i].equals(mUserViewModel.getUser().getValue().getCity())) {
                    index = i;
                    continue;
                }
                mSCity.setSelection(index);
            }
        } else {
            mSCity.setSelection(3);
            mUserViewModel.getUser().getValue().setCity(cityOptions[3]);
        }
    }
    public void observeUserCountry() {
        if (mUserViewModel.getUser().getValue().getCountry() != null) {
            int index = 0;
            for (int i = 0; i < countryOptions.length; i++) {
                if (countryOptions[i].equals(mUserViewModel.getUser().getValue().getCountry())) {
                    index = i;
                    break;
                }
            }
            mSCountry.setSelection(index);
        } else {
            mSCountry.setSelection(0);
            mUserViewModel.getUser().getValue().setCountry(countryOptions[0]);
        }
    }
    public void observeUserSex() {
        if (mUserViewModel.getUser().getValue().getSex() != null) {
            if (mUserViewModel.getUser().getValue().getSex().equals(sexOptions[1])) {
                mSSex.setSelection(1);
            }
        } else {
            mSSex.setSelection(0);
            mUserViewModel.getUser().getValue().setSex(sexOptions[0]);
        }
    }
    public void observeUserName() {
        mETFirstName.setText(mUserViewModel.getUser().getValue().getFirstName());
        mETLastName.setText(mUserViewModel.getUser().getValue().getLastName());
    }

    public void observeUserProfilePic() {
        if(mUserViewModel.getUser().getValue().getPhotoData() != null) {
            mBtPicture.setBackgroundResource(R.drawable.pic_button);
        }
    }
}
