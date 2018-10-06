package com.back.vom.services;

import android.content.Context;

public class SFHandler {

    public static  void save(Context context, String k, String v) {
        context.getSharedPreferences("user",Context.MODE_PRIVATE).edit().putString(k,v).commit();
    }
    public static  String get(Context context, String k) {
        return context.getSharedPreferences("user",Context.MODE_PRIVATE).getString(k,"");
    }
}
