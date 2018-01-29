package com.adamoglu.chatlove.FireBase;

import android.database.Cursor;

import com.adamoglu.chatlove.Adapter;
import com.adamoglu.chatlove.ChatMain;
import com.adamoglu.chatlove.References;
import com.adamoglu.chatlove.UnSentMessages;
import com.adamoglu.chatlove.database;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class getKey {


    public void get(final ChatMain chatMain, final sendFirebase sendfirebase, final UnSentMessages unsentmessages, final Adapter adapter, final database myDb, final Cursor res){


        DatabaseReference read_nolimit = chatMain.references.getReference(References.getRef_Message(chatMain.Pass,chatMain.UserMacc));
        Query read;

        final int[] key = {0};

        read = read_nolimit.orderByValue().limitToLast(1);

        read.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    key[0] = Integer.parseInt(message.getKey());
                }

                sendfirebase.send(adapter,chatMain,unsentmessages,sendfirebase,getKey.this,myDb,res.getString(0),key[0]+1, Integer.parseInt(res.getString(3)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
