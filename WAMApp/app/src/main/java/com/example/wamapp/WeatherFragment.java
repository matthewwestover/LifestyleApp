package com.example.wamapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    public WeatherFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



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
        tvCityName = view.findViewById(R.id.city_name);
        tvTemperatureResult = view.findViewById(R.id.temperature_result);
        mCity = getArguments().getString("userCity");
        mCountry = getArguments().getString("userCountry");

        this.getTemperature();
        return view;
    }

    public void getTemperature() {
        String resultText;
        String cName = mCity;
        String coName = mCountry;

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
                    cName + "," + coName + "&units=imperial&appid=f6433bd2ce4b361f4acb887f3d679a8e").get();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
