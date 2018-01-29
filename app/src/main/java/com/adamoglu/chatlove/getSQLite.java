package com.adamoglu.chatlove;

import android.database.Cursor;


public class getSQLite {
    void get(ChatMain chatMain,database myDb, Adapter adapter){
        Cursor res = myDb.getAllData();

        if(res.getCount()!=0){

            while (res.moveToNext()){
                Arrays.addArray(chatMain,res.getString(0),res.getString(1),res.getString(2),res.getString(4),res.getLong(3));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
