package com.adamoglu.chatlove;

import android.database.Cursor;

import com.adamoglu.chatlove.FireBase.getKey;
import com.adamoglu.chatlove.FireBase.sendFirebase;


public class UnSentMessages {

    public void start(ChatMain chatMain, getKey getkey,UnSentMessages unsentmessages, sendFirebase sendfirebase, database myDb, Adapter adapter){
        try {
            chatMain.control_sended_message=true;


            final Cursor res = myDb.getAllData_NoSend();

            if(res.getCount()==0){
                chatMain.control_sended_message=false;
            }
            else
            {
                while(res.moveToNext()){
                    getkey.get(chatMain,sendfirebase,unsentmessages,adapter,myDb,res);return;
                }
            }

        }catch (Exception ignored){

        }
    }
}
