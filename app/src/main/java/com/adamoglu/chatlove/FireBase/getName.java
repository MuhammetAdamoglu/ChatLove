package com.adamoglu.chatlove.FireBase;

import android.widget.TextView;
import android.widget.Toast;

import com.adamoglu.chatlove.ChatMain;
import com.adamoglu.chatlove.References;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class getName {



    public void get(final ChatMain chatMain, final TextView tv_UserName){


        DatabaseReference dbRef_UserMacc = chatMain.references.getReference(References.getRef_Users(chatMain.Pass));
        dbRef_UserMacc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    for (DataSnapshot macc: dataSnapshot.getChildren()) {

                        if(!chatMain.MyMacc.equals(macc.getKey())){

                            chatMain.UserMacc= macc.getKey();
                            chatMain.Editor().putString("UserMacc", chatMain.UserMacc).commit();

                        }else {
                            chatMain.Name= macc.child("Name").getValue().toString();
                            chatMain.Editor().putString("Name", chatMain.UserMacc).commit();
                        }

                    }


                }
                catch (Exception ignored){
                }

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(chatMain.UserMacc!=null)
            if(!chatMain.UserMacc.equals("")){
                DatabaseReference dbRef_UserName = chatMain.references.getReference(References.getRef_UserName(chatMain.Pass,chatMain.UserMacc));
                dbRef_UserName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try{

                            chatMain.UserName=dataSnapshot.getValue().toString();
                            chatMain.Editor().putString("UserName", chatMain.UserName).commit();

                        }
                        catch (Exception ignored){
                        }

                        chatMain.UserName=chatMain.SharedPreferences().getString("UserName", "").trim();
                        tv_UserName.setText(chatMain.SharedPreferences().getString("UserName", "").trim());


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        DatabaseReference dbRef_UserName = chatMain.references.getReference(References.getRef_UserName(chatMain.Pass,chatMain.MyMacc));
        dbRef_UserName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    chatMain.Name=dataSnapshot.getValue().toString();
                    chatMain.Editor().putString("Name", chatMain.Name).commit();


                }
                catch (Exception ignored){
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
