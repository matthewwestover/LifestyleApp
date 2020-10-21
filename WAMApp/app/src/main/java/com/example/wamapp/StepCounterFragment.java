package com.example.wamapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

// See http://www.gadgetsaint.com/android/create-pedometer-step-counter-android/#.W832EVJMHOQ
public class StepCounterFragment extends Fragment implements SensorEventListener, StepListener {
    private StepDetector mSimpleStepDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelSensor;
    private int mNumSteps;
    private Sensor mLinearAccelerometer;
    long mLastTime;
    double mLast_x;
    double mLast_y;
    double mLast_z;
    private User mCurrentUser;
    private UserViewModel mUserViewModel;
    private TextView mTvStepData;

    public StepCounterFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.step_fragment, container, false);
        mUserViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        mUserViewModel.getUser().observe(this, userObserver);
        mTvStepData = fragmentView.findViewById(R.id.tv_step_data);
        mNumSteps = (mUserViewModel.getUser().getValue().getSteps());

        if(savedInstanceState != null) {
            mLast_x = savedInstanceState.getDouble("x");
            mLast_y = savedInstanceState.getDouble("y");
            mLast_z = savedInstanceState.getDouble("z");
            mLastTime = savedInstanceState.getLong("time");
        }

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSimpleStepDetector = new StepDetector();
        mSimpleStepDetector.registerListener(StepCounterFragment.this);
        mLinearAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    final Observer<User> userObserver  = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            if(user!=null) {
                mCurrentUser = user;
                mTvStepData.setText("" + mCurrentUser.getSteps());
            }
        }
    };

    private SensorEventListener mShakeListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            double now_x = sensorEvent.values[0];
            double now_y = sensorEvent.values[1];
            double now_z = sensorEvent.values[2];
            final int SHAKE_THRESHOLD = 3;

            long currentTime = System.currentTimeMillis();
            long dTime = currentTime - mLastTime;

            if (dTime > 100) {
                mLastTime = currentTime;
                double startShake = Math.abs(mLast_x - now_x);
                double stopShake = Math.abs(mLast_y - now_y);

                if (startShake > SHAKE_THRESHOLD) {
                    mSensorManager.registerListener(StepCounterFragment.this, mAccelSensor, SensorManager.SENSOR_DELAY_FASTEST);
                } else if (stopShake > SHAKE_THRESHOLD) {
                    mUserViewModel.update(mUserViewModel.getUser().getValue());
                    mSensorManager.unregisterListener(StepCounterFragment.this);
                }
            }
            mLast_x = now_x;
            mLast_y = now_y;
            mLast_z = now_z;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mSimpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        mNumSteps++;
        mTvStepData.setText("" + mNumSteps);
        mUserViewModel.getUser().getValue().setSteps(mNumSteps);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mLinearAccelerometer!=null){
            mSensorManager.registerListener(mShakeListener, mLinearAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mLinearAccelerometer!=null){
            mSensorManager.unregisterListener(mShakeListener);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        outstate.putDouble("x", mLast_x);
        outstate.putDouble("y", mLast_y);
        outstate.putDouble("z", mLast_z);
        outstate.putLong("time", mLastTime);
        outstate.putInt("steps", mNumSteps);
        super.onSaveInstanceState(outstate);
    }
}
