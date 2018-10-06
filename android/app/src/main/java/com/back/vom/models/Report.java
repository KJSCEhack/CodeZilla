package com.back.vom.models;

import android.util.Log;

import java.util.ArrayList;

public class Report {

    String mImageUrl;
    String mCategory;
    String mDescription;

    Boolean mVolunteer;
    String mDate;

    public Ward getWard() {
        return ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
    }

    public Ward ward;

    public ArrayList<String> mVolunteers = new ArrayList<String>();

    public ArrayList<Comment> mComments = new ArrayList<Comment>();

    public static final String TAG = Report.class.getSimpleName();

    String createdBy;
    String uid;

    long upvotes;

    int mStatus;

    String mRating;
    String mReview;

    public ArrayList<String> getVolunteers() {
        return mVolunteers;
    }

    public void setVolunteers(ArrayList<String> volunteers) {
        mVolunteers = volunteers;
    }


    double mLatitude, mLongitude;
    int mReportDate;

    public Report() {

    }




    public Report(String category, String description, boolean volunteer, String selectedDate, String imageURL) {
        mCategory = category;
        mDescription = description;
        mVolunteer = volunteer;
        mDate = selectedDate;
        mImageUrl = imageURL;
    }




    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        if (mStatus == 1)
        this.mRating = mRating;
        else Log.d(TAG, "Report isn't still Resolved!");
    }

    public String getmReview() {
        return mReview;
    }



    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(long upvotes) {
        this.upvotes = upvotes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
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

    public ArrayList<String> getmVolunteers() {
        return mVolunteers;
    }

    public ArrayList<Comment> getmComments() {
        return mComments;
    }

    public void setmComments(ArrayList<Comment> mComments) {
        this.mComments = mComments;
    }

    public void setmVolunteers(ArrayList<String> mVolunteers) {
        this.mVolunteers = mVolunteers;
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
