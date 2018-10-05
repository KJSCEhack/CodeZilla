package com.back.vom.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * The type Dialog box builder.
 */
public class DialogBoxBuilder {

    /**
     * The constant TAG.
     */
    private static final String TAG = DialogBoxBuilder.class.getSimpleName();


    /**
     * Create dialog box alert dialog.
     *
     * @param activity            the activity
     * @param title               the title
     * @param cancelable          the cancelable
     * @param promptsView         the prompts view
     * @param positiveButtonTitle the positive button title
     * @param positiveButton      the positive button
     * @param neutralButtonTitle  the neutral button title
     * @param neutralButton       the neutral button
     * @param negativeButtonTitle the negative button title
     * @param negativeButton      the negative button
     * @param resources           the resources
     * @return the alert dialog
     */
    public static AlertDialog createDialogBox(Activity activity, String title, boolean cancelable, View promptsView,
                                              String positiveButtonTitle, DialogInterface.OnClickListener positiveButton,
                                              String neutralButtonTitle, DialogInterface.OnClickListener neutralButton,
                                              String negativeButtonTitle, DialogInterface.OnClickListener negativeButton,
                                              Resources resources) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setTitle(title)
                .setCancelable(cancelable);


        if (negativeButton != null && negativeButtonTitle != null)
            alertDialogBuilder.setNegativeButton(negativeButtonTitle, negativeButton);
        if (positiveButton != null && positiveButtonTitle != null)
            alertDialogBuilder.setPositiveButton(positiveButtonTitle, positiveButton);
        if (neutralButton != null && neutralButtonTitle != null)
            alertDialogBuilder.setNeutralButton(neutralButtonTitle, neutralButton);

        return alertDialogBuilder.create(); // this is combined of above 2 lines
    }
}
