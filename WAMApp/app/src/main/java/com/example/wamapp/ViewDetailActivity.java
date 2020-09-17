package com.example.wamapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ViewDetailActivity extends AppCompatActivity {

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetail_activity);

        if(savedInstanceState != null) {
            extras = savedInstanceState.getBundle("extras");
        } else {
            extras = getIntent().getExtras();
        }

        int position = extras.getInt("click_position");

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        switch(position) {
            case 0: {
                //fTrans.replace(R.id.fl_viewdetail, new ProfileFragment());
                break;
            }
            case 1: {
                fTrans.replace(R.id.fl_viewdetail, new BmiFragment());
                break;
            }
            case 2: {
                //fTrans.replace(R.id.fl_viewdetail, new BMRFragment());
                break;
            }
            case 3: {
                fTrans.replace(R.id.fl_viewdetail, new WeatherFragment());
                break;
            }
            case 4: {
                fTrans.replace(R.id.fl_viewdetail, new HikesFragment());
            }
            case 5: {
                fTrans.replace(R.id.fl_viewdetail, new GoalsFragment());
            }
        }
        fTrans.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putBundle("extras", extras);
    }

}
