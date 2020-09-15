package com.example.wamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
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

    public void getTemperature(View view) {
        cityName = findViewById(R.id.city_name);
        temperatureResult = findViewById(R.id.temperature_result);

        String cName = cityName.getText().toString();

        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q=" +
                    cName + ",us&units=imperial&appid=f6433bd2ce4b361f4acb887f3d679a8e").get();
            Log.i("content",content);

            // JSON
            JSONObject jObj = new JSONObject(content);
            String weatherData = jObj.getString("weather");
            String mainTemp = jObj.getString("main");
            Log.i("weatherData",weatherData);

            JSONArray wArray = new JSONArray(weatherData);

            String main = "";
            String description = "";
            String temperature = "";

            for (int i = 0; i < wArray.length(); i++) {
                JSONObject weatherPart = wArray.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }

            JSONObject mainPart = new JSONObject(mainTemp);
            temperature = mainPart.getString("temp");

            double fTemp = Double.valueOf(temperature);
//            fTemp = fTemp * (9/5.0) + 32;
            DecimalFormat df = new DecimalFormat("#");
            temperature = df.format(fTemp);

            Log.i("temp",temperature);


//            Log.i("main",main);
//            Log.i("description",description);

            String resultText = "Temperature: " + temperature + "\nMain: " + main + "\nDescription: " + description;
            temperatureResult.setText(resultText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
