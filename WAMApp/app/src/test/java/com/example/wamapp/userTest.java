package com.example.wamapp;
import android.graphics.Bitmap;

import org.junit.Test;

import static org.junit.Assert.*;

public class userTest {
    User testUser = new User(1, "Kevin", "Wright", 15, "Male", null, "Salt Lake City", "USA",  55,105);

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
        double calories = testUser.calculateCalories(1311.7,"Lightly Active", 3.0);
        testUser.setCalories((int)calories);
        assertEquals(3303, testUser.getCalories());
        double calories2 = testUser.calculateCalories(1311.7,"Lightly Active", -3.0);
        testUser.setCalories((int)calories2);
        assertEquals(303, testUser.getCalories());
    }


}
