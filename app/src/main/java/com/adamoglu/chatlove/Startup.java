package com.adamoglu.chatlove;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Startup extends AppCompatActivity {

    private SharedPreferences SharedPreferences(){
        final SharedPreferences prefSettings =  getSharedPreferences("", Context.MODE_PRIVATE);
        return prefSettings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        if(SharedPreferences().getString("Name", "null").trim().equals("null")){
            Intent i = new Intent(getApplicationContext(),EnterName.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
        else {
            Intent i = new Intent(getApplicationContext(),ChatMain.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }


    }
}
