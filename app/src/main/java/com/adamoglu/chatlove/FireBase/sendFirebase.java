package com.adamoglu.chatlove.FireBase;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Toast;

import com.adamoglu.chatlove.Adapter;
import com.adamoglu.chatlove.Arrays;
import com.adamoglu.chatlove.ChatMain;
import com.adamoglu.chatlove.References;
import com.adamoglu.chatlove.UnSentMessages;
import com.adamoglu.chatlove.Values;
import com.adamoglu.chatlove.database;
import com.adamoglu.chatlove.getID;
import com.adamoglu.chatlove.getTime;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


public class sendFirebase {

    public ChatMain chatMain;
    private String time;

    public void send(final Adapter adapter, final ChatMain chatMain, final UnSentMessages unsentmessages, final sendFirebase sendfirebase, final getKey getkey, final database myDb, final String message, final int key, final int id){


        this.chatMain=chatMain;

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                try {

                    time= getTime.get();

                    DatabaseReference dbRef_Message = chatMain.references.getReference(References.getRef_Message(chatMain.Pass,chatMain.UserMacc));
                    dbRef_Message.child(String.valueOf(key)).setValue(new Values(message, time, chatMain.MyMacc, chatMain.Name), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError==null){

                                long new_id= getID.get(chatMain);

                                myDb.Sended_NoSendMessage(String.valueOf(id));
                                myDb.Sended(String.valueOf(id));

                                myDb.ekle(message,time,"1",chatMain.MyMacc,new_id);


                                try {
                                    Arrays.removeArray(chatMain,id);

                                    int index=chatMain.message_send_control_array.indexOf("0");

                                    if(index==-1){
                                        Arrays.addArray(chatMain,message,time,"1",chatMain.MyMacc,new_id);
                                    }else {
                                        Arrays.setArray(chatMain,message,time,"1",chatMain.MyMacc,new_id,index);
                                    }

                                }catch (Exception ex){
                                    Toast.makeText(chatMain, ex.toString(), Toast.LENGTH_SHORT).show();
                                }

                                adapter.notifyDataSetChanged();

                            }else {
                                Toast.makeText(chatMain, "Eror Send Message", Toast.LENGTH_SHORT).show();
                            }

                            unsentmessages.start(chatMain,getkey,unsentmessages,sendfirebase,myDb,adapter);
                        }
                    });
                }
                catch (Exception ignored){
                    Toast.makeText(chatMain, "Arrays Eror", Toast.LENGTH_SHORT).show();
                }

                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }

        }

        new SendPostReqAsyncTask().execute(message, String.valueOf(id));
    }


}
