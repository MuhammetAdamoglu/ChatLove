package com.adamoglu.chatlove;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class EnterName extends AppCompatActivity {

    EditText Et_NickName,Et_Pass;
    Button next;

    FirebaseDatabase db;


    private String NickName="";
    private String Pass="";

    private int i=0;
    private void ControlNameCount(){

        dialog.setMessage("Oda Sayısı Kontrol Ediliyor");
        dialog.setCancelable(false);
        dialog.show();

        DatabaseReference dbRef_User = db.getReference("ROOMs/"+Pass+"/Users");
        dbRef_User.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    for (DataSnapshot names: dataSnapshot.getChildren()) {
                        if(!getMac().equals(names.getKey())){
                            i++;
                        }
                    }

                    if(i>=2){
                        dialog.setMessage("Oda Dolu");
                        dialog.setCancelable(false);
                        dialog.show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        }, 1000);

                        return;
                    }

                    Control_Room();
                }
                catch (Exception ignored){
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public void Control_Room(){


        dialog.setMessage("Kontrol Ediliyor");
        dialog.setCancelable(false);
        dialog.show();

        DatabaseReference okuma = db.getReference("ROOMs/"+Pass);
        okuma.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final SharedPreferences prefSettings =  getSharedPreferences("", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefSettings.edit();


                try {

                    dataSnapshot.getValue().toString();

                    Enter_Room(editor);


                }catch (NullPointerException ex){

                    dialog.setMessage("Parola Yanlış");
                    dialog.setCancelable(false);
                    dialog.show();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }, 1000);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.setMessage("İptal Edildi");
                dialog.setCancelable(false);
                dialog.show();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }, 1000);
            }
        });

    }

    private void Enter_Room(SharedPreferences.Editor editor){
        DatabaseReference dbRef = db.getReference("ROOMs/"+Pass+"/Users/"+getMac());
        dbRef.setValue(true);

        DatabaseReference dbRef2 = db.getReference("ROOMs/"+Pass+"/Users/"+getMac()+"/Name");
        dbRef2.setValue(NickName);

        editor.putString("MyMacc",getMac());
        editor.putString("Name",NickName);
        editor.putString("Pass",Pass);
        editor.commit();

        dialog.setMessage("İçeri Giriliyor");
        dialog.setCancelable(false);
        dialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                Intent i = new Intent(getApplicationContext(),ChatMain.class);
                startActivity(i);
            }
        }, 1500);
    }

    public static String getMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ignored) {
        }
        return "02:00:00:00:00:00";
    }


    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        dialog = new ProgressDialog(this);

        Et_NickName = (EditText) findViewById(R.id.NickName);
        Et_Pass = (EditText) findViewById(R.id.Pass);
        next = (Button) findViewById(R.id.Button_Next);

        db=FirebaseDatabase.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NickName=Et_NickName.getText().toString().trim();
                Pass=Et_Pass.getText().toString().trim();

                if(!NickName.equals("") && !Pass.equals("")){

                    ControlNameCount();

                }

            }
        });



    }
}
