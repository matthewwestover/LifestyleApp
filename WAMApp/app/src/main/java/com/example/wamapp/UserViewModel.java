package com.example.wamapp;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amplifyframework.core.Amplify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<User> mUser;
    private static UserRepository mUserRepository;

    public UserViewModel(Application application) {
        super(application);
        mUserRepository = UserRepository.getInstance(application);
        mUser = mUserRepository.getUser();
    }

    public void insert(User user) {
        mUserRepository.insert(user);
    }

    public void update(User user) {
        mUserRepository.update(user);
    }

    public void dumpInDB(User user) {
        update(user);
        uploadFile("WAMUSER", getApplication());
    }

    public LiveData<User> getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUserRepository.setUser(user);
    }

    public VoidAsyncTask getNumberOfUserInDatabase() {
        return mUserRepository.getNumberOfProfilesInDatabase();
    }

    public File getDatabaseFile(Application application) {
        String backupDBPath = UserDatabase.getDatabase(application.getApplicationContext()).getOpenHelper().getWritableDatabase().getPath();
        File dbPath = new File(backupDBPath) ;
        if (dbPath.exists()) {
            Log.d("UserRepository", "File created successfully.") ;
        }
        return dbPath ;
    }

    public void uploadFile(String key, Application application) {
//        File file = getDatabaseFile(application);
        File file = application.getApplicationContext().getDatabasePath("user_database");
        Amplify.Storage.uploadFile( key, file, result -> Log.i ("WAMAPP", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("WAMAPP", "Upload failed", storageFailure));
    }


}

