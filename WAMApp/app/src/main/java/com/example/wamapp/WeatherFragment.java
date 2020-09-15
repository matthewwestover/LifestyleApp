package com.example.wamapp;

import androidx.fragment.app.Fragment;

import android.os.AsyncTask;
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

    public void getTemperature(View view) {
        cityName = view.findViewById(R.id.city_spin);
        temperatureResult = view.findViewById(R.id.temperature_result);
        countryName = view.findViewById(R.id.country_spin);

        String cName = cityName.getText().toString();
        String coName = countryName.getText().toString();

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
            Log.i("content",content);

            // JSON
            JSONObject jObj = new JSONObject(content);
            String mainTemp = jObj.getString("main");
            Log.i("mainTemp", mainTemp);

            String temperature = "";

            JSONObject mainPart = new JSONObject(mainTemp);
            temperature = mainPart.getString("temp");

            double fTemp = Double.valueOf(temperature);
            DecimalFormat df = new DecimalFormat("#");
            temperature = df.format(fTemp);

            Log.i("temp",temperature);

            String resultText = temperatureResult + "˚";
            temperatureResult.setText(resultText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
}
