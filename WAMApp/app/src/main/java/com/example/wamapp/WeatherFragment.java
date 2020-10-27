package com.example.wamapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

public class WeatherFragment extends Fragment {

    String mCity, mCountry;
    TextView tvCityName;
    TextView tvTemperatureResult;
    private User mCurrentUser;
    String weatherAPIKey = BuildConfig.WEATHER_API_KEY;
    private UserViewModel mUserViewModel;

    public WeatherFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if(user!=null) {
                mCurrentUser = user;
            }
        }
    };

    class Weather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;

                while (data != -1) {
                    ch = (char) data;
                    content += ch;
                    data = isr.read();
                }
                return content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false);
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mUserViewModel.getUser().observe(getViewLifecycleOwner(), userObserver);
        mCity = String.valueOf(mUserViewModel.getUser().getValue().getCity());
        mCountry = String.valueOf(mUserViewModel.getUser().getValue().getCountry());

        tvCityName = view.findViewById(R.id.city_name);
        tvTemperatureResult = view.findViewById(R.id.temperature_result);

        int resultantTemp = this.getTemperature();
        mUserViewModel.getUser().getValue().addTemp(resultantTemp);
        mUserViewModel.dumpInDB(mUserViewModel.getUser().getValue());

        return view;
    }

    public int getTemperature() {
        String resultText;
        String cName = mCity;
        String coName = mCountry;
        int temp = 0;

        if (coName == "USA") {
            coName = "us";
        } else if (coName == "MX") {
            coName = "mx";
        } else if (coName == "CA") {
            coName = "ca";
        }

        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=" +
                    cName + "," + coName + "&units=imperial&appid=" + weatherAPIKey).get();

            // JSON
            JSONObject jObj = new JSONObject(content);
            String mainTemp = jObj.getString("main");
            String cityName = jObj.getString("name");

            String temperature = "";

            JSONObject mainPart = new JSONObject(mainTemp);
            temperature = mainPart.getString("temp");

            double fTemp = Double.parseDouble(temperature);
            DecimalFormat df = new DecimalFormat("#");
            temperature = df.format(fTemp);

            resultText = temperature + "˚";
            Log.i("cityName", cityName);
            Log.i("coName", coName);
            Log.i("resultText", resultText);
            tvCityName.setText(cityName);
            tvTemperatureResult.setText(resultText);

            temp = Integer.parseInt(temperature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }
}
