package com.back.vom.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.back.vom.R;
import com.google.firebase.database.DatabaseReference;

/**
 * The type Sign up step one fragment.
 */
public class NewReports extends Fragment {

    private static final String TAG = NewReports.class.getSimpleName();
    private View mView;
    public EditText titleEditText, commentEditText;
    public String title, comment;

    public NewReports() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.newreports, container, false);



        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        titleEditText = getView().findViewById(R.id.reportTitle);
        commentEditText = getView().findViewById(R.id.reportComment);

        title = titleEditText.getText().toString();
        comment = commentEditText.getText().toString();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}