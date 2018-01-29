package com.adamoglu.chatlove;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abra on 20.01.2018.
 */

public class getID {

    public static SharedPreferences.Editor Editor(){
        final SharedPreferences prefSettings =  context.getSharedPreferences("", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefSettings.edit();
        return editor;
    }
    public static SharedPreferences SharedPreferences(){
        final SharedPreferences prefSettings =  context.getSharedPreferences("", Context.MODE_PRIVATE);
        return prefSettings;
    }

    @SuppressLint("StaticFieldLeak")
    static Context context;
    public static long get(Context c){
        context=c;
        Editor().putLong("ID",SharedPreferences().getLong("ID",0)+1).commit();
        return SharedPreferences().getLong("ID",0);
    }
}
