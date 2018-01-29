package com.adamoglu.chatlove;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.adamoglu.chatlove.FireBase.getKey;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;


public class Notifications extends Service {
    Context context ;

    database myDb;

    String time;

    private String Name;
    private String UserName;
    private String Pass;
    private String MyMacc;
    private String UserMacc;

    References references;


    public SharedPreferences.Editor Editor(){
        final SharedPreferences prefSettings =  getSharedPreferences("", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefSettings.edit();
        return editor;
    }
    public SharedPreferences SharedPreferences(){
        final SharedPreferences prefSettings =  getSharedPreferences("", Context.MODE_PRIVATE);
        return prefSettings;
    }

    public static boolean isAppRunning(Context context) {

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        return services.get(0).topActivity.getPackageName()
                .equalsIgnoreCase(context.getPackageName());
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {//Servis startService(); metoduyla çağrıldığında çalışır
        context = getApplicationContext();

        myDb = new database(getApplicationContext());
        references = new References();

        getName();

        Name=SharedPreferences().getString("Name","").trim();
        MyMacc=SharedPreferences().getString("MyMacc", "").trim();
        Pass=SharedPreferences().getString("Pass","null").trim();
        UserName=SharedPreferences().getString("UserName", "").trim();
        UserMacc=SharedPreferences().getString("UserMacc", "").trim();




        Sended();
        Control_Send_Message(Editor());

        DatabaseReference read = references.getReference(References.getRef_Message(Pass,MyMacc));

        read.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                try {
                    if(!isAppRunning(context)){
                        Name=SharedPreferences().getString("Name","").trim();
                        UserName=SharedPreferences().getString("UserName", "").trim();

                        Editor().putBoolean("BackgrondIsGet",true).commit();

                        String message=dataSnapshot.child("message").getValue().toString();
                        String mac=dataSnapshot.child("mac").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String time=dataSnapshot.child("time").getValue().toString();


                        if(!mac.equals("")){
                            bildirimGonder(message, name);

                            long id= getID.get(getApplicationContext());
                            myDb.ekle(message,time,"1",mac,id);

                            dataSnapshot.getRef().removeValue();
                        }

                        Editor().putBoolean("BackgrondIsGet",false).commit();
                    }
                }catch (Exception ex){
                    Editor().putBoolean("BackgrondIsGet",false).commit();
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


    public void Send(final String message, int key, final int id, final SharedPreferences.Editor editor){

        try {

            time= getTime.get();

            DatabaseReference dbRef_Message = references.getReference(References.getRef_Message(Pass,UserMacc));
            dbRef_Message.child(String.valueOf(key)).setValue(new Values(message, time, Name ,MyMacc), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError==null){

                        long new_id= getID.get(getApplication());

                        myDb.Sended_NoSendMessage(String.valueOf(id));
                        myDb.Sended(String.valueOf(id));

                        myDb.ekle(message,time,"1",MyMacc,new_id);

                    }else {

                    }

                    Control_Send_Message(editor);

                }
            });
        }
        catch (Exception ignored){
        }
    }


    private void getKey(final Cursor res, final SharedPreferences.Editor editor){
        DatabaseReference read_nolimit = references.getReference(References.getRef_Message(Pass,UserMacc));
        Query read;

        final int[] key = {0};

        read = read_nolimit.orderByValue().limitToLast(1);

        read.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    key[0] = Integer.parseInt(message.getKey());
                }

                Send(res.getString(0),key[0]+1, Integer.parseInt(res.getString(3)),editor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Control_Send_Message(SharedPreferences.Editor editor){

        try {

            final Cursor res = myDb.getAllData_NoSend();

            if(res.getCount()!=0){
                while(res.moveToNext()){
                    getKey(res,editor);return;
                }
            }

        }catch (Exception ignored){

        }
    }


    public void Sended(){


        DatabaseReference dbRef_User = references.getReference(References.getRef_ShowedOrSended(Pass,MyMacc));
        dbRef_User.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    if(dataSnapshot.getValue().toString().trim().equals("showed"))
                        Showed();
                    else
                        Gone();

                }catch (Exception ignored){
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    void Showed(){

        Editor().putInt("showed",2).commit();
        DatabaseReference dbRef_Message = references.getReference(References.getRef_ShowedOrSended(Pass,MyMacc));
        dbRef_Message.setValue(false);
    }

    void Gone() {
        Editor().putInt("sended", 1).commit();
        DatabaseReference dbRef_Message = references.getReference(References.getRef_ShowedOrSended(Pass,MyMacc));
        dbRef_Message.setValue(false);
    }

    public void getName(){


        DatabaseReference dbRef_UserMacc = references.getReference(References.getRef_Users(Pass));
        dbRef_UserMacc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    for (DataSnapshot macc: dataSnapshot.getChildren()) {

                        if(!MyMacc.equals(macc.getKey())){

                            UserMacc= macc.getKey();
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


        if(UserMacc!=null)
            if(!UserMacc.equals("")){
                DatabaseReference dbRef_UserName = references.getReference(References.getRef_UserName(Pass,UserMacc));
                dbRef_UserName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        try{

                            UserName=dataSnapshot.getValue().toString();
                        }
                        catch (Exception ignored){
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        DatabaseReference dbRef_UserName = references.getReference(References.getRef_UserName(Pass,MyMacc));
        dbRef_UserName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    Name=dataSnapshot.getValue().toString();
                    Editor().putString("Name", Name).commit();


                }
                catch (Exception ignored){
                }


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void bildirimGonder(String message , String name){// Burda servis class dan post edip sunucudan aldığımız değeri bildirim gönderiyoruz.

        if(!UserName.equals("")){
            DatabaseReference dbRef_Message = references.getReference(References.getRef_ShowedOrSended(Pass,UserMacc));
            dbRef_Message.setValue("sended");
        }

        Intent i = new Intent(this,ChatMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(name)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.FLAG_SHOW_LIGHTS)
                .setLights(0xff00ff00, 300, 100)
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());

    }

}

