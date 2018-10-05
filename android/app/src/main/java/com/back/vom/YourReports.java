package com.back.vom;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The type Sign up step one fragment.
 */
public class YourReports extends Fragment {

    /**
     * The constant TAG for logs.
     */
    private static final String TAG = YourReports.class.getSimpleName();

    /**
     * The M view to inflate the fragment view .
     */
    private View mView;

    /**
     * Instantiates a new Sign up step one fragment.
     */
    public YourReports() {
    }

    /**
     * This method is used to display the step one fragment for signUp with User id,
     * Email id & Parent's Phone no.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.yourreports, container, false);


        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}