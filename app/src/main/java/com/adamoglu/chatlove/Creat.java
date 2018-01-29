package com.adamoglu.chatlove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Creat extends AppCompatActivity {

    FirebaseDatabase db;
    EditText et_creat,et_nickname,et_pass;
    Button btn_creat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat);

        et_creat= (EditText) findViewById(R.id.Creat);
        et_nickname= (EditText) findViewById(R.id.NickName);
        et_pass= (EditText) findViewById(R.id.Pass);
        btn_creat= (Button) findViewById(R.id.Button_Creat);
        db=FirebaseDatabase.getInstance();


        btn_creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbRef_Creat = db.getReference("ROOMs/"+et_creat.getText().toString()+"/Control");
                dbRef_Creat.setValue(true);

                DatabaseReference dbRef_Pass= db.getReference("ROOMs/"+et_creat.getText().toString()+"/Pass");
                dbRef_Pass.setValue(et_pass.getText().toString());

                DatabaseReference dbRef_Nickname = db.getReference("ROOMs/"+et_creat.getText().toString()+"/NickName");
                dbRef_Nickname.setValue(et_nickname.getText().toString());
            }
        });


    }
}
