package com.back.vom.models;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Report {

    String mImageUrl;
    String mTitle;
    String mDescription;

    Boolean mVolunteer;
    String mDate;

    public ArrayList<String> mVolunteers = new ArrayList<String>();

    public ArrayList<Comment> mComments = new ArrayList<Comment>();

    public static final String TAG = Report.class.getSimpleName();

    String createdBy;
    String uid;

    long upvotes;

    String mStatus;

    String mRating;
    String mReview;

    double mLatitude, mLongitude;
    int mReportDate;




    public Report(String title, String description, boolean volunteer, String selectedDate, String imageURL) {
        mTitle = title;
        mDescription = description;
        mVolunteer = volunteer;
        mDate = selectedDate;
        mImageUrl = imageURL;
    }




    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        if (mStatus.equalsIgnoreCase("RESOLVED"))
        this.mRating = mRating;
        else Log.d(TAG, "Report isn't still Resolved!");
    }

    public String getmReview() {
        return mReview;
    }

    public void setmReview(String mReview) {
        if (mStatus.equalsIgnoreCase("RESOLVED"))
        this.mReview = mReview;
        else Log.d(TAG, "Report isn't still Resolved!");
    }


    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getReportDate() {
        return mReportDate;
    }

    public void setReportDate(int reportDate) {
        mReportDate = reportDate;
    }
}
