package com.adamoglu.chatlove.FireBase;

import com.adamoglu.chatlove.Adapter;
import com.adamoglu.chatlove.ChatMain;
import com.adamoglu.chatlove.References;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Abra on 20.01.2018.
 */

public class getShowed {

    public void get(final ChatMain chatMain, final Adapter adapter){


        DatabaseReference dbRef_User = chatMain.references.getReference(References.getRef_ShowedOrSended(chatMain.Pass,chatMain.MyMacc));
        dbRef_User.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    if(dataSnapshot.getValue().toString().trim().equals("showed"))
                        Showed(adapter,chatMain);
                    else if(dataSnapshot.getValue().toString().trim().equals("sended"))
                        Gone(adapter,chatMain);

                }catch (Exception ignored){
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getSave(ChatMain chatMain, Adapter adapter){
        if(chatMain.SharedPreferences().getInt("showed",0)==2){
            adapter.setSended_or_showed(2);
        }
        else if(chatMain.SharedPreferences().getInt("sended",0)==1){
            adapter.setSended_or_showed(1);
        }
        else {
            adapter.setSended_or_showed(0);
        }
    }

    void Showed(Adapter adapter, ChatMain chatMain){
        adapter.setSended_or_showed(2);
        adapter.notifyDataSetChanged();
        chatMain.Editor().putInt("showed",2).commit();
        DatabaseReference dbRef_Message = chatMain.references.getReference(References.getRef_ShowedOrSended(chatMain.Pass,chatMain.MyMacc));
        dbRef_Message.setValue(false);
    }

    void Gone(Adapter adapter, ChatMain chatMain) {
        adapter.setSended_or_showed(1);
        adapter.notifyDataSetChanged();
        chatMain.Editor().putInt("sended", 1).commit();
        DatabaseReference dbRef_Message = chatMain.references.getReference(References.getRef_ShowedOrSended(chatMain.Pass,chatMain.MyMacc));
        dbRef_Message.setValue(false);
    }
}
