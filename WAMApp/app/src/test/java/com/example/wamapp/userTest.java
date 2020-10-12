package com.example.wamapp;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.UserData;
import android.view.LayoutInflater;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

public class userTest {
    int uID = 1;
    String fname = "Kevin";
    String lname = "Wright";
    int age = 15;
    String sex = "Male";
    String city = "Salt Lake City";
    String country = "USA";
    int Height = 55;
    int weight = 105;
    User testUser = new User(0);

    @Before
    public void setUp() throws Exception {
        testUser.setUserID(uID);
        testUser.setFirstName(fname);
        testUser.setLastName(lname);
        testUser.setAge(age);
        testUser.setSex(sex);
        testUser.setCity(city);
        testUser.setCountry(country);
        testUser.setHeight(Height);
        testUser.setWeight(weight);
        testUser.setBMI(User.calculateBMI(Height,weight));
        testUser.setBMR(User.calculateBMR(Height, weight, age, sex));
    }

    @Test
    //Tests the sets and gets for user ID:
    public void testUserID()
    {
        assertEquals(1, testUser.getUserID());
        testUser.setUserID(2);
        assertEquals(2, testUser.getUserID());
    }
    @Test
    //Tests the set and get for the user's first name:
    public void testUserName()
    {
        assertEquals("Kevin Wright", testUser.getFullName());
        assertEquals("Kevin", testUser.getFirstName());
        testUser.setFirstName("Noodle");
        assertEquals("Noodle", testUser.getFirstName());
        assertEquals("Wright", testUser.getLastName());
        testUser.setLastName("Doodle");
        assertEquals("Doodle", testUser.getLastName());
        assertEquals("Noodle Doodle", testUser.getFullName());
    }
    @Test
    //Test setting and getting the user's age:
    public void testUserAge()
    {
        assertEquals(15, testUser.getAge());
        testUser.setAge(27);
        assertEquals(27, testUser.getAge());
    }
    @Test
    //Test setting and getting the user's Sex:
    public void testUserSex()
    {
        assertEquals("Male", testUser.getSex());
        testUser.setSex("Female");
        assertEquals("Female", testUser.getSex());
    }
    @Test
    // Test setting and getting the user's City and Country:
    public void testUserLocation()
    {
        assertEquals("Salt Lake City", testUser.getCity());
        assertEquals("USA", testUser.getCountry());
        testUser.setCity("London");
        testUser.setCountry("England");
        assertEquals("London", testUser.getCity());
        assertEquals("England", testUser.getCountry());
    }
    @Test
    //Test setting and getting the user's Height, Weight, BMI, and BMR:
    public void testUserStats()
    {
        assertEquals(55, testUser.getHeight());
        assertEquals(105, testUser.getWeight(),0.0);
        assertEquals(24.4, testUser.getBMI(),0.1);
        assertEquals(1311.7, testUser.getBMR(),0.1);
        testUser.setHeight(61);
        testUser.setWeight(130);
        assertEquals(61, testUser.getHeight());
        assertEquals(130, testUser.getWeight(),0.0);
    }
    @Test
    //Tests user's weight goals
    public void testUserGoals()
    {
        double calories = User.calculateCalories(1311.7,"Lightly Active", 3.0);
        testUser.setCalories((int)calories);
        assertEquals(3303, testUser.getCalories(),0.0);
        double calories2 = User.calculateCalories(1311.7,"Lightly Active", -3.0);
        testUser.setCalories((int)calories2);
        assertEquals(303, testUser.getCalories(), 0.0);
    }
    @Mock
    MainActivity mainActivity;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testMainActivity(){
       mainActivity.onCreate(null);
       assertNotNull(mainActivity);
       assertFalse(mainActivity.isTablet());
    }

    @Mock
    AppHeadFragment appHeadFragment;

    @Test
    public void testAppHeadFragment(){
        appHeadFragment.onCreateView(null,null,null);
        assertNotNull(appHeadFragment);
    }

    @Mock
    BlankHeadFragment blankHeadFragment;

    @Test
    public void testBlankHeadFragment(){
        blankHeadFragment.onCreateView(null, null, null);
        assertNotNull(blankHeadFragment);
    }

    @Mock BmiFragment bmiFragment;

    @Test
    public void testBmiFragment(){
        bmiFragment.onCreateView(null, null, null);
        assertNotNull(bmiFragment);
    }

    @Mock BmrFragment bmrFragment;

    @Test
    public void testBmrFragment(){
        bmrFragment.onCreateView(null, null, null);
        assertNotNull(bmrFragment);
    }

    @Mock EditUserFragment editUserFragment;

    @Test
    public void testEditUserFragment(){
        editUserFragment.onCreateView(null, null, null);
        assertNotNull(editUserFragment);
    }

    @Mock GoalsFragment goalsFragment;

    @Test
    public void testGoalsFragment(){
        goalsFragment.onCreateView(null, null, null);
        assertNotNull(goalsFragment);
    }

    @Mock HikesFragment hikesFragment;

    @Test
    public void testHikesFragment(){
        hikesFragment.onCreateView(null, null, null);
        assertNotNull(hikesFragment);
    }

    @Mock BmiFragment MasterListFragment;

    @Test
    public void testMasterListFragment(){
        MasterListFragment.onCreateView(null, null, null);
        assertNotNull(MasterListFragment);
    }

    @Mock MyRVAdapter myRVAdapter;

    @Test
    public void testRVAdapter(){
        myRVAdapter.onCreateViewHolder(null, 0);
        assertNotNull(myRVAdapter);
    }

    @Mock SplashActivity splashActivity;

    @Test
    public void testSplashActivity(){
        splashActivity.onCreate(null);
        assertNotNull(splashActivity);
    }

    @Mock UserProfileFragment userProfileFragment;

    @Test
    public void testUserProfileFragment(){
        userProfileFragment.onCreateView(null, null, null);
        assertNotNull(userProfileFragment);
    }

    @Mock ViewDetailActivity viewDetailActivity;

    @Test
    public void testViewDetailActivity(){
        viewDetailActivity.onCreate(null);
        assertNotNull(viewDetailActivity);
    }

    @Mock WeatherFragment weatherFragment;

    @Test
    public void testWeatherFragment(){
        weatherFragment.onCreateView(null, null, null);
        assertNotNull(weatherFragment);
    }
}
