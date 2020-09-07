package com.example.wamapp;

public class User {
    // Member Vars
    private int userID;
    private String firstName;
    private String lastName;
    private int age;
    private String sex;
    private String photoPath;
    private String city;
    private String country;
    private int height; // convert to total inches
    private double weight; // convert as total pounds
    private int BMI;
    private int BMR;
    private String activeLevel;
    private int weightGoal;
    private int calories;

    public User(String firstName, String lastName, int age, String sex, String photoPath, String city, String country, int height, double weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.photoPath = photoPath;
        this.city = city;
        this.country = country;
        this.height = height;
        this.weight = weight;
        // generate userID somehow down the road
    }

    // Calculate BMI
    // BMI = 703 x weight (lbs) / [height (inches)]^2
    public static double calculateBMI(int height, double weight){
        double heightSq = height * height;
        double weightConv = weight * 703;
        double BMI = weightConv / heightSq;
        return BMI;
    }

    // Calculate BMR
    // Formula from: https://bmi-calories.com/bmr-calculator.html
    // Men BMR = 88.362 + (13.397 x weight in kg) + (4.799 x height in cm) - (5.677 x age in years)
    // Women BMR = 447.593 + (9.247 x weight in kg) + (3.098 x height in cm) - (4.330 x age in years)
    public static double calculateBMR(int height, double weight, int age, String sex){
        double heightCM = height * 2.54;
        double weightKG = weight * 0.453592;
        double BMR = 0;
        if(sex == "Male"){
            weightKG = weightKG * 13.397;
            heightCM = heightCM * 4.799;
            double age2 = age * 5.677;
            BMR = 88.382 + weightKG + heightCM - age2;
        } else {
            weightKG = weightKG * 9.247;
            heightCM = heightCM * 3.098;
            double age2 = age * 4.33;
            BMR = 447.593 + weightKG + heightCM - age2;
        }
        return BMR;
    }

    public static double calculateCalories(double BMR, String activityLevel, double weightGoal) {
        double calories = 0;
        switch (activityLevel) {
            case "Sedentary": calories = BMR * 1.2;
                break;
            case "Lightly Active": calories = BMR * 1.375;
                break;
            case "Moderately Active": calories = BMR * 1.55;
                break;
            case "Very Active": calories = BMR * 1.725;
                break;
        }
        calories = (int)calories;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

    public String getActiveLevel() {
        return activeLevel;
    }

    public void setActiveLevel(String activeLevel) {
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
