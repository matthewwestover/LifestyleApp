package com.example.wamapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

@Entity(tableName = "user_table")
public class User implements Parcelable {

    @PrimaryKey
    @NonNull
    private int userID;

    private String firstName;
    private String lastName;
    private int age;
    private String sex;
    private String city;
    private String country;
    private int height;
    private int weight;
    private double BMI;
    private double BMR;
    private String activeLevel;
    private double weightGoal;
    private double calories;
    private int steps = 0;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] photo;

    private String weatherValues;

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        sex = in.readString();
        activeLevel = in.readString();
        city = in.readString();
        country = in.readString();
        userID = in.readInt();
        age = in.readInt();
        height = in.readInt();
        weight = in.readInt();
        BMR = in.readDouble();
        BMI = in.readDouble();
        weightGoal = in.readDouble();
        calories = in.readDouble();
        Bitmap profilePic = in.readParcelable(null);
        setProfileImageData(profilePic);
    }

    public User(int userID) {
        firstName = "";
        lastName = "";
        userID = userID;
        age = 14;
    }

//    @Ignore
//    public User(int userID, String firstName, String lastName, String sex, String city, String country,
//                String activeLevel, int age, int height, int weight, double BMR, double BMI,
//                double weightGoal, double calories, byte[] photo) {
//        this.userID =userID;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.sex = sex;
//        this.city = city;
//        this.country =country;
//        this.activeLevel = activeLevel;
//        this.age = age;
//        this.height = height;
//        this.weight = weight;
//        this.BMR = BMR;
//        this.BMI = BMI;
//        this. weightGoal = weightGoal;
//        this.calories = calories;
//        this.photo = photo;
//
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(sex);
        dest.writeString(activeLevel);
        dest.writeString(city);
        dest.writeString(country);
        dest.writeInt(userID);
        dest.writeInt(age);
        dest.writeInt(height);
        dest.writeInt(weight);
        dest.writeDouble(BMR);
        dest.writeDouble(BMI);
        dest.writeDouble(weightGoal);
        dest.writeDouble(calories);
        Bitmap profilePic = getPhotoBitmap(photo);
        dest.writeParcelable(profilePic, flags);
    }

    public void updateUser(Bundle userData) {
        userID = userData.getInt("userID");
        firstName = userData.getString("userFirstName");
        lastName = userData.getString("userLastName");
        sex = userData.getString("userSex");
        city = userData.getString("userCity");
        country = userData.getString("userCountry");
        age = userData.getInt("userAge");
        height = userData.getInt("userHeight");
        weight = userData.getInt("userWeight");
        BMR = userData.getDouble("userBMR");
        BMI = userData.getDouble("userBMI");
        activeLevel = userData.getString("userActiveLevel");
        weightGoal = userData.getDouble("userGoal");
        calories = userData.getDouble("userCalories");
        Bitmap profilePic = userData.getParcelable("userPhoto");
        setProfileImageData(profilePic);
    }

    // Calculate BMI
    public static double calculateBMI(int height, int weight) {
        double heightM = height * 0.0254;
        double heightSq = heightM * heightM;
        double weightKg = weight * 0.453592;
        double BMI = weightKg / heightSq;
        DecimalFormat df = new DecimalFormat(".##");
        String strBMI = df.format(BMI);
        BMI = Double.parseDouble(strBMI);
        return BMI;
    }

    // Calculate BMR
    // Formula from: https://bmi-calories.com/bmr-calculator.html
    // Men BMR = 88.362 + (13.397 x weight in kg) + (4.799 x height in cm) - (5.677 x age in years)
    // Women BMR = 447.593 + (9.247 x weight in kg) + (3.098 x height in cm) - (4.330 x age in years)
    public static double calculateBMR(int height, int weight, int age, String sex) {
        double heightCM = height * 2.54;
        double weightKG = weight * 0.453592;
        double BMR = 0;
        if (sex.equals("Male")) {
            weightKG = weightKG * 13.397;
            heightCM = heightCM * 4.799;
            double age2 = age * 5.677;
            BMR = 88.382 + weightKG + heightCM - age2;
            DecimalFormat df = new DecimalFormat(".##");
            String strBMR = df.format(BMR);
            BMR = Double.parseDouble(strBMR);
        } else {
            weightKG = weightKG * 9.247;
            heightCM = heightCM * 3.098;
            double age2 = age * 4.33;
            BMR = 447.593 + weightKG + heightCM - age2;
            DecimalFormat df = new DecimalFormat(".##");
            String strBMR = df.format(BMR);
            BMR = Double.parseDouble(strBMR);
        }
        return BMR;
    }

    public static double calculateCalories(double BMR, String activityLevel, double weightGoal) {
        double calories = 0;
        switch (activityLevel) {
            case "Sedentary":
                calories = BMR * 1.2;
                break;
            case "Lightly Active":
                calories = BMR * 1.375;
                break;
            case "Moderately Active":
                calories = BMR * 1.55;
                break;
            case "Very Active":
                calories = BMR * 1.725;
                break;
        }
        calories = (int) calories;
        return calories + (500 * weightGoal);
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public double getBMI() {
        return BMI;
    }
    public void setBMI(double BMI) {
        this.BMI = BMI;
    }
    public double getBMR() {
        return BMR;
    }
    public void setBMR(double BMR) {
        this.BMR = BMR;
    }
    public String getActiveLevel() {
        return activeLevel;
    }
    public void setActiveLevel(String activeLevel) {
        this.activeLevel = activeLevel;
    }
    public double getWeightGoal() {
        return weightGoal;
    }
    public void setWeightGoal(double weightGoal) {
        this.weightGoal = weightGoal;
    }
    public double getCalories() {
        return calories;
    }
    public void setCalories(double calories) {
        this.calories = calories;
    }
    public byte[] getPhoto() { return photo; }
    public void setPhoto(byte [] photo) { this.photo = photo; }
    public byte[] getPhotoData() {
        return photo;
    }
    public int getSteps() { return steps; }
    public void setSteps(int step) { steps = step; }

    // Bitmap to byte[] to profileImageData
    public void setProfileImageData(Bitmap image) {
        if (image != null) {
            //bitmap to byte[]
            photo = bitmapToByte(image);
        } else {
            photo = null;
        }
    }

    public byte[] bitmapToByte(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //bitmap to byte[] stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] x = stream.toByteArray();
            //close stream to save memory
            stream.close();
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getPhotoBitmap(byte[] profileImage) {
        if (profileImage != null) {
            return BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
        }
        return null;
    }

    public void addTemp(String temp) {
        weatherValues.concat(temp);
    }
}
