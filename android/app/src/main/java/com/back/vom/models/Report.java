package com.back.vom.models;

public class Report {

    String mImageUrl;
    String mTitle;
    String mDescription;

    Boolean mVolunteer;
    String mDate;

    double mLatitude, mLongitude;
    int mStatus, mReportDate;

    public Report(String title, String description, boolean volunteer, String selectedDate, String imageURL) {
        mTitle = title;
        mDescription = description;
        mVolunteer = volunteer;
        mDate = selectedDate;
        mImageUrl = imageURL;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Boolean getVolunteer() {
        return mVolunteer;
    }

    public void setVolunteer(Boolean volunteer) {
        mVolunteer = volunteer;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getReportDate() {
        return mReportDate;
    }

    public void setReportDate(int reportDate) {
        mReportDate = reportDate;
    }


}
