package com.back.vom.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.back.vom.R;

public class InitFragment {

    /**
     * Set.
     * <p>
     * This method is used to set the navigation drawer.
     *
     * @param activity  the activity for references
     * @param fragment  the fragment To replace or switch to that Fragment
     */
                                                            //, int enterFrom, int exitTo
    public static void set(Activity activity, Fragment fragment) {
        FragmentManager mFragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
        //mFragmentTransaction.setCustomAnimations(enterFrom, exitTo);
        mFragmentTransaction.replace(R.id.fragmentContainer, fragment);

        mFragmentTransaction.commit();
    }
}
