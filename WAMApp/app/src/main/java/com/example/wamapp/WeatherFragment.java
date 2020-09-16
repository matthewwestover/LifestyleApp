package com.example.wamapp;

import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    TextView cityName;
    TextView temperatureResult;
    TextView countryName;

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

    public String getTemperature(View view) {
//        cityName = view.findViewById(R.id.city_spin);
//        temperatureResult = view.findViewById(R.id.temperature_result);
//        countryName = view.findViewById(R.id.country_spin);
        String resultText = "";

//        String cName = cityName.getText().toString();
        String cName = "Salt Lake City";
//        String coName = countryName.getText().toString();
        String coName = "USA";

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

            resultText = cityName + "\n" + temperature + "˚";
//            temperatureResult.setText(resultText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultText;
    }
}
