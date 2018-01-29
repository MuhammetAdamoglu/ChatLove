package com.adamoglu.chatlove.FireBase;

import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.adamoglu.chatlove.ChatMain;
import com.adamoglu.chatlove.References;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class getOnline {


    TextView online;

    public void get(final ChatMain chatMain, final TextView online){

        this.online=online;


        DatabaseReference dbRef_User = chatMain.references.getReference(References.getOnlineOrWrite(chatMain.Pass,chatMain.UserMacc));
        dbRef_User.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(!chatMain.UserName.equals(""))
                {
                    try{
                        if(dataSnapshot.getValue().toString().trim().equals("Yaziyor..."))
                        {
                            online.setText("Yazıyor...");
                        }
                        else if(dataSnapshot.getValue().toString().trim().equals("true")) {
                            online.setText("Çevrimiçi");
                        }else {
                            online.setText("");
                        }

                    }
                    catch (Exception ex){

                        online.setText("");
                    }
                }else {
                    online.setText("");
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
