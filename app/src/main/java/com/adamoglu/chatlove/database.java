package com.adamoglu.chatlove;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class database extends SQLiteOpenHelper {

    private static final String VERITABANI = "Chat";
    private static final String TABLE = "chat";
    private static final String TABLE2 = "NoSend";
    private static String MESSAGE = "message";
    private static String TIME = "time";
    private static String CONTROL = "control";
    private static String ID = "ID";
    private static String NAME = "name";


    Context context;
    public database(Context context) {
        super(context, VERITABANI, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + TABLE + " ( "

                        + MESSAGE + " TEXT, "
                        + TIME + " TEXT, "
                        + CONTROL + " TEXT, "
                        + ID + " TEXT, "
                        + NAME + " TEXT "
                        + " )"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE2 + " ( "

                        + MESSAGE + " TEXT, "
                        + TIME + " TEXT, "
                        + CONTROL + " TEXT, "
                        + ID + " TEXT, "
                        + NAME + " TEXT "
                        + " )"
        );

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXITS" + TABLE);
        db.execSQL("DROP TABLE IF EXITS" + TABLE2);
        onCreate(db);

    }


    public boolean ekle(String message, String time,String control, String name, long id) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues veriler = new ContentValues();

        veriler.put(MESSAGE, message);
        veriler.put(TIME, time);
        veriler.put(CONTROL, control);
        veriler.put(NAME, name);
        veriler.put(ID,id);



        long result = db.insert(TABLE, null, veriler);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean ekle_NoSend(String message, String time,String control, String name, long id) {

        //Günlük veri eklemek için

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues veriler = new ContentValues();

        veriler.put(MESSAGE, message);
        veriler.put(TIME, time);
        veriler.put(CONTROL, control);
        veriler.put(NAME, name);
        veriler.put(ID,id);

        long result = db.insert(TABLE2, null, veriler);
        if (result == -1)
            return false;
        else
            return true;

    }



    public Cursor getAllData() {

        //günlük verileri çekmek için

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from " + TABLE, null);

        return result;
    }


    public Cursor getAllData_NoSend() {

        //günlük verileri çekmek için

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from " + TABLE2, null);

        return result;
    }

    public void Sended_NoSendMessage(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE2, "ID = ?", new String[]{id});
    }
    public void Sended(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, "ID = ?", new String[]{id});
    }


    public void updateMessage(String id, String mesaj) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(MESSAGE, mesaj);


        db.update(TABLE, contentValues, "ID = ?", new String[]{id});

    }


    /*public void updateKontrol(String id, String kontrol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("control", kontrol);

        db.update(TABLE, contentValues, "ID = ?", new String[]{id});
    }*/

    /*public void updateKontrol_NoSend(String id, String kontrol) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("control", kontrol);

        db.update(TABLE2, contentValues, "ID = ?", new String[]{id});
    }*/






    public void DeleteAllData_NoSend(){
        //
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE2,null,null);
        db.close();

    }

    public void DeleteAllData(){
        //
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();

    }



}