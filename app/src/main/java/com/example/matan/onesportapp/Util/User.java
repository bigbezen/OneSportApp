package com.example.matan.onesportapp.Util;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

    private String userProfilePictureUrl;
    private String userFirstName;
    private String userLastName;
    private Date userBirthDay;
    private String userGender;
    private String userEmail;

    public String getUserProfilePictureUrl() {
        return userProfilePictureUrl;
    }

    public void setUserProfilePictureUrl(String userProfilePictureUrl) {
        this.userProfilePictureUrl = userProfilePictureUrl;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Date getUserBirthDay() {
        return userBirthDay;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public void setUserBirthDay(String userBirthDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            this.userBirthDay = sdf.parse(userBirthDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
