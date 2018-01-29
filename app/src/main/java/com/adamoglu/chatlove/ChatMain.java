package com.adamoglu.chatlove;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adamoglu.chatlove.FireBase.getFirebase;
import com.adamoglu.chatlove.FireBase.getKey;
import com.adamoglu.chatlove.FireBase.getName;
import com.adamoglu.chatlove.FireBase.getOnline;
import com.adamoglu.chatlove.FireBase.getShowed;
import com.adamoglu.chatlove.FireBase.sendFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;



public class ChatMain extends AppCompatActivity {

    public ImageView send;
    public EditText write;
    public ListView listView;
    public TextView tv_UserName, tv_online, AppName;
    public TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;


    public database myDb = new database(this);

    public Adapter myadapter;

    public ArrayList<String> messages = new ArrayList<>();
    public ArrayList<String> message_time_array = new ArrayList<>();
    public ArrayList<String> message_send_control_array = new ArrayList<>();
    public ArrayList<String> message_array_name = new ArrayList<>();
    public ArrayList<String> messages_id = new ArrayList<>();

    public String Name;
    public String Pass;
    public String UserName;
    public String UserMacc;
    public String MyMacc;

    boolean control_sended_message = false;
    public boolean control_Scrool = true;


    public getFirebase getfirebase;
    public listview_Scroll listview_scroll;
    public sendFirebase sendfirebase;
    public UnSentMessages unsentmessages;
    public getKey getkey;
    public getOnline online;
    public getName getname;
    public getShowed getshowed;
    public getSQLite getsqlite;
    public References references;


    public void IDs() {

        send = (ImageView) findViewById(R.id.Button_Send);
        listView = (ListView) findViewById(R.id.ListView);
        write = (EditText) findViewById(R.id.Chat);
        tv_online = (TextView) findViewById(R.id.Online);
        tv_UserName = (TextView) findViewById(R.id.Username);
        AppName = (TextView) findViewById(R.id.AppName);

        write.addTextChangedListener(wacher);


        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        tv6 = (TextView) findViewById(R.id.textView6);
        tv7 = (TextView) findViewById(R.id.textView7);
        tv8 = (TextView) findViewById(R.id.textView8);
        tv9 = (TextView) findViewById(R.id.textView9);
        tv10 = (TextView) findViewById(R.id.textView10);
    }

    public SharedPreferences.Editor Editor() {
        final SharedPreferences prefSettings = getSharedPreferences("", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefSettings.edit();
        return editor;
    }

    public SharedPreferences SharedPreferences() {
        final SharedPreferences prefSettings = getSharedPreferences("", Context.MODE_PRIVATE);
        return prefSettings;
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (!MyMacc.equals("")) {
            DatabaseReference dbRef_Message = references.getReference(References.getOnlineOrWrite(Pass, MyMacc));
            dbRef_Message.setValue(false);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!MyMacc.equals("")) {
            DatabaseReference dbRef_Message = references.getReference(References.getOnlineOrWrite(Pass, MyMacc));
            dbRef_Message.setValue(true);
        }
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chat_main);

        IDs();

        getfirebase = new getFirebase();
        listview_scroll = new listview_Scroll();
        sendfirebase = new sendFirebase();
        unsentmessages = new UnSentMessages();
        getkey = new getKey();
        online = new getOnline();
        getname = new getName();
        getshowed = new getShowed();
        getsqlite = new getSQLite();
        references = new References();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.parseColor("#FFFFFF"));
        }



        Intent intent = new Intent(getApplicationContext(),Notifications.class);
        startService(intent);

        Name=SharedPreferences().getString("Name","").trim();
        MyMacc=SharedPreferences().getString("MyMacc", "").trim();
        Pass=SharedPreferences().getString("Pass","null").trim();
        UserName=SharedPreferences().getString("UserName", "").trim();
        UserMacc=SharedPreferences().getString("UserMacc", "").trim();


        myadapter = new Adapter(this,messages,message_time_array,message_send_control_array,message_array_name,MyMacc);
        getshowed.getSave(this,myadapter);
        listView.setAdapter(myadapter);


        getfirebase.get(this,listView,myadapter);

        unsentmessages.start(this,getkey,unsentmessages,sendfirebase,myDb,myadapter);

        DatabaseReference dbRef_User = references.getReference(References.getOnlineOrWrite(Pass,MyMacc));
        dbRef_User.onDisconnect().setValue(false);


        getname.get(this,tv_UserName);
        online.get(this,tv_online);
        getshowed.get(this,myadapter);

        getsqlite.get(this,myDb,myadapter);

        listview_scroll.start(this,listView);


        AppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                myDb.DeleteAllData();
                myDb.DeleteAllData_NoSend();

                DatabaseReference dbRef_Message = references.getReference(References.getRef_Name(Pass,MyMacc));
                dbRef_Message.removeValue();

                Editor().clear().commit();

                Intent intent = new Intent(getApplicationContext(),Notifications.class);
                stopService(intent);

                Intent i = new Intent(getApplicationContext(),EnterName.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(!write.getText().toString().trim().equals("")){
                        String time = getTime.get();

                        Editor().putLong("ID",SharedPreferences().getLong("ID",0)+1).commit();
                        long id=SharedPreferences().getLong("ID",0);

                        myDb.ekle_NoSend(write.getText().toString().trim(),time,"0",MyMacc,id);
                        myDb.ekle(write.getText().toString().trim(),time,"0",MyMacc,id);

                        try{
                            Arrays.addArray(ChatMain.this,write.getText().toString().trim(),time,"0",MyMacc,id);

                            if(!control_sended_message)
                                unsentmessages.start(ChatMain.this,getkey,unsentmessages,sendfirebase,myDb,myadapter);

                            myadapter.setSended_or_showed(0);

                            myadapter.notifyDataSetChanged();
                            listView.smoothScrollToPosition(myadapter.getCount());


                        }catch (Exception ex){
                            Toast.makeText(ChatMain.this, ex.toString(), Toast.LENGTH_SHORT).show();
                        }

                        Editor().putInt("showed",0).commit();

                        write.setText("");
                    }
                }catch (Exception ignored){

                }

            }
        });


        tv1.setText(new String(Character.toChars(0x2764)));
        tv2.setText(new String(Character.toChars(0x1F495)));
        tv3.setText(new String(Character.toChars(0x1F496)));
        tv4.setText(new String(Character.toChars(0x1F494)));
        tv5.setText(new String(Character.toChars(0x1F60D)));
        tv6.setText(new String(Character.toChars(0x1F602)));
        tv7.setText(new String(Character.toChars(0x1F601)));
        tv8.setText(new String(Character.toChars(0x1F605)));
        tv9.setText(new String(Character.toChars(0x1F606)));
        tv10.setText(new String(Character.toChars(0x1F60A)));

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv1.getText());
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv2.getText());
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv3.getText());
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv4.getText());
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv5.getText());
            }
        });

        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv6.getText());
            }
        });

        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv7.getText());
            }
        });

        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv8.getText());
            }
        });

        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv9.getText());
            }
        });

        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write.getText().insert(write.getSelectionStart(),tv10.getText());
            }
        });





    }


    TextWatcher wacher = new TextWatcher() {

        boolean control_write=true;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            listView.smoothScrollToPosition(myadapter.getCount());

            if(write.getText().toString().trim().equals("")){

                DatabaseReference dbRef_Writing = references.getReference(References.getOnlineOrWrite(Pass,MyMacc));
                dbRef_Writing.setValue(true);
                control_write=true;

            }else {
                if(control_write){
                DatabaseReference dbRef_Writing = references.getReference(References.getOnlineOrWrite(Pass,MyMacc));
                dbRef_Writing.setValue("Yaziyor...");
                    control_write=false;
                }

            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
