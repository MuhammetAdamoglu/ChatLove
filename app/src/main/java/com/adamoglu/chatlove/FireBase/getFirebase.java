package com.adamoglu.chatlove.FireBase;

import android.widget.ListView;
import android.widget.Toast;

import com.adamoglu.chatlove.Adapter;
import com.adamoglu.chatlove.Arrays;
import com.adamoglu.chatlove.ChatMain;
import com.adamoglu.chatlove.References;
import com.adamoglu.chatlove.getID;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class getFirebase {

    private ChatMain chatMain;
    public void get(final ChatMain chatMain, final ListView listView, final Adapter adapter){

        try {

            this.chatMain=chatMain;

            DatabaseReference read = chatMain.references.getReference(References.getRef_Message(chatMain.Pass,chatMain.MyMacc));

            read.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    try {

                        if(!chatMain.SharedPreferences().getBoolean("BackgrondIsGet",false)){
                            String message=dataSnapshot.child("message").getValue().toString();
                            String name=dataSnapshot.child("mac").getValue().toString();
                            String time=dataSnapshot.child("time").getValue().toString();

                            long id= getID.get(chatMain);
                            chatMain.myDb.ekle(message,time,"1",name,id);

                            Arrays.addArray(chatMain,message,time,"1",name,id);
                            dataSnapshot.getRef().removeValue();


                            control_listview(listView,adapter);


                            if(!chatMain.UserName.equals("")){
                                DatabaseReference dbRef_Message = chatMain.references.getReference(References.getRef_ShowedOrSended(chatMain.Pass,chatMain.UserMacc));
                                dbRef_Message.setValue("showed");
                            }
                        }

                    }
                    catch (Exception ex){
                        Toast.makeText(chatMain, String.valueOf(ex.toString()), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception ignored){

        }
    }

    private void control_listview(ListView listView, Adapter adapter){
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        adapter.notifyDataSetChanged();
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);

        if (chatMain.control_Scrool){
            listView.smoothScrollToPosition(adapter.getCount());
        }

    }
}
