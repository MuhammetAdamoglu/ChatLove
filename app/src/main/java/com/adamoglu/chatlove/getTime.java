package com.adamoglu.chatlove;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class getTime {

    public static String get(){
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(c.getTime());
    }

}
