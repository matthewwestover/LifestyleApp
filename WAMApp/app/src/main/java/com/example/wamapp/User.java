package com.example.wamapp;

public class User {
    // Member Vars
    private int userID;
    private String firstName;
    private String lastName;
    private int age;
    private Sex sex;
    private String photoPath;
    private int zipCode;
    private String country;
    private int height; // convert to total inches
    private double weight; // convert as total pounds
    private int BMI;
    private int BMR;
    private ActiveLevel activeLevel;
    private int weightGoal;
    private int calories;


    public User(String firstName, String lastName, int age, Sex sex, String photoPath, int zipCode, String country, int height, double weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.photoPath = photoPath;
        this.zipCode = zipCode;
        this.country = country;
        this.height = height;
        this.weight = weight;
        // generate userID somehow down the road
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getBMI() {
        return BMI;
    }

    public void setBMI(int BMI) {
        this.BMI = BMI;
    }

    public int getBMR() {
        return BMR;
    }

    public void setBMR(int BMR) {
        this.BMR = BMR;
    }

    public ActiveLevel getActiveLevel() {
        return activeLevel;
    }

    public void setActiveLevel(ActiveLevel activeLevel) {
        this.activeLevel = activeLevel;
    }

    public int getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(int weightGoal) {
        this.weightGoal = weightGoal;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
