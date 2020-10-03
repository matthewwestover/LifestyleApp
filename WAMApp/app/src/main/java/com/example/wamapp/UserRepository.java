package com.example.wamapp;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

public class UserRepository extends AppCompatActivity {
    private static UserRepository single_instance = null;
    private static final MutableLiveData<User> mUser = new MutableLiveData<>();
    private UserDao mUserDao;
    private static Context mContext;

    public static UserRepository getInstance(Application application)
    {
        if (single_instance == null) {
            single_instance = new UserRepository(application);
        }
        return single_instance;
    }

    private UserRepository(Application application) {
        UserDatabase db = UserDatabase.getDatabase(application);
        mContext = application.getApplicationContext() ;
        mUserDao = db.userDao();
    }

    public void setUser(User user) {
        mUser.setValue(user);
    }

    public void insert(User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update(User user) {
        new updateAsyncTask(mUserDao, mUser).execute(user);
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, User>{
        private UserDao mAsyncTaskDao;
        private MutableLiveData<User> mProfileData;

        updateAsyncTask(UserDao dao, MutableLiveData<User> profile){
            this.mAsyncTaskDao = dao;
            this.mProfileData = profile;
        }

        @Override
        protected User doInBackground(final User... user) {
            mAsyncTaskDao.update(user[0]);
            return user[0];
        }

        @Override
        protected void onPostExecute(User profile) {
            mUser.setValue(profile);
        }
    }

    public MutableLiveData<User> getUser() {
        return mUser;
    }

    public VoidAsyncTask getNumberOfProfilesInDatabase(){
        rowsInDatabaseTask task = new rowsInDatabaseTask(mUserDao);
        return task;
    }

    private static class rowsInDatabaseTask extends VoidAsyncTask<Integer> {

        private UserDao mAsyncTaskDao;
        private int result;

        rowsInDatabaseTask(UserDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            result =  mAsyncTaskDao.getNumberOfUserInDatabase();
            return result;
        }

        protected int onPostExecute(int result) {
            return result;
        }

        public int getResult(){
            return result;
        }

    }

}
