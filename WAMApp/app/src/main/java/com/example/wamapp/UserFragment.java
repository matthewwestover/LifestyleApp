package com.example.wamapp;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class UserFragment extends Fragment
        implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    private EditText mETFullName;
    private Spinner mSAge, mSSex, mSCountry, mSCity, mSHeight, mSWeight;
    private Button mBtPicture, mBtSubmit;
    private String mStringFullName;
    OnDataPass mDataPasser;

    public UserFragment()
    {
    }

    //Callback interface
    public interface OnDataPass
    {
        public void onDataPass(String[] data);
    }

    //Associate the callback with this Fragment


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mDataPasser = (OnDataPass) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement OnDataPass");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.edituser_fragment,container,false);
        mBtPicture = (Button) view.findViewById(R.id.pic_button);
        mBtPicture.setOnClickListener(this);
        mBtSubmit = (Button) view.findViewById(R.id.sub_button);
        mBtSubmit.setOnClickListener(this);

        //set Age spinner
        mSAge = (Spinner) view.findViewById(R.id.age_spin);
        Integer [] ages = new Integer[100];
        for(int i = 0; i<100; i++)
        {
            ages[i] = i + 1; //
        }
        ArrayAdapter<Integer> ageDA = new ArrayAdapter<Integer>
                (mSAge.getContext(), android.R.layout.simple_spinner_item, ages);

        ageDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSAge.setAdapter(ageDA);

        //set Sex spinner
        mSSex = (Spinner) view.findViewById(R.id.sex_spin);
        List<String> sexes = new ArrayList<>();
        sexes.add("Female");
        sexes.add("Male");
        ArrayAdapter<String> sexDA = new ArrayAdapter<String>
                (mSSex.getContext(), android.R.layout.simple_spinner_item, sexes);
        sexDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSSex.setAdapter(sexDA);

        //set Country spinner
        mSCountry = (Spinner) view.findViewById(R.id.country_spin);
        List<String> countries = new ArrayList<>(); // Can add more later?
        countries.add("USA");
        countries.add("CA");
        countries.add("MX");
        ArrayAdapter<String> countryDA = new ArrayAdapter<String>
                (mSCountry.getContext(), android.R.layout.simple_spinner_item, countries);
        countryDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSCountry.setAdapter(countryDA);

        //set City spinner
        mSCity = (Spinner) view.findViewById(R.id.city_spin);
        List<String> cities = new ArrayList<>(); // Can add more later?
        cities.add("Los Angeles");
        cities.add("Salt Lake City");
        cities.add("Chicago");
        cities.add("New York City");
        cities.add("Toronto");
        cities.add("Mexico City");
        ArrayAdapter<String> cityDA = new ArrayAdapter<String>
                (mSCity.getContext(), android.R.layout.simple_spinner_item, cities);
        cityDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSCity.setAdapter(cityDA);

        //Set Height in inches spinner
        mSHeight = (Spinner) view.findViewById(R.id.height_spin);
        List<Integer> height = new ArrayList<>();
        for(int i = 48; i<100; i++)
        {
            height.add(i);
        }
        ArrayAdapter<Integer> heightDA = new ArrayAdapter<Integer>
                (mSHeight.getContext(), android.R.layout.simple_spinner_item, height);
        heightDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSHeight.setAdapter(heightDA);

        //Set Weight in lbs spinner
        mSWeight = (Spinner) view.findViewById(R.id.weight_spin);
        List<Integer> weight = new ArrayList<>();
        for(int i = 100; i<400; i++)
        {
            weight.add(i);
        }
        ArrayAdapter<Integer> weightDA = new ArrayAdapter<Integer>
                (mSWeight.getContext(), android.R.layout.simple_spinner_item, weight);

        weightDA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSWeight.setAdapter(weightDA);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String item = parent.getItemAtPosition(position).toString();
        String[] selected = new String[1];
        selected[0] = item;
        mDataPasser.onDataPass(selected);
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }


    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.sub_button:
                {
                mStringFullName = mETFullName.getText().toString();

                //Check if the EditText string is empty
                if (mStringFullName.matches("")) {
                    //Complain that there's no text
                    Toast.makeText(getActivity(), "Enter a name first!", Toast.LENGTH_SHORT).show();
                } else {
                    //Reward them for submitting their names
                    Toast.makeText(getActivity(), "Good job!", Toast.LENGTH_SHORT).show();

                    //Remove any leading spaces or tabs
                    mStringFullName = mStringFullName.replaceAll("^\\s+", "");

                    //Separate the string into first and last name using simple Java stuff
                    String[] splitStrings = mStringFullName.split("\\s+");

                    if (splitStrings.length == 1) {
                        Toast.makeText(getActivity(), "Enter both first and last name!", Toast.LENGTH_SHORT).show();
                    } else if (splitStrings.length == 2) {
                        mDataPasser.onDataPass(splitStrings);

                    } else {
                        Toast.makeText(getActivity(), "Enter only first and last name!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }

    }
}
