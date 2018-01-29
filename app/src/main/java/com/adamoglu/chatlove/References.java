package com.adamoglu.chatlove;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class References {


    private String Title="ROOMs";
    private FirebaseDatabase db;

    References(){
        CreateRef();
    }


    private void CreateRef(){
        db= FirebaseDatabase.getInstance();
    }
    public DatabaseReference getReference(String ref){
        if(db==null)
            CreateRef();
        return db.getReference(ref);
    }


    public static String getOnlineOrWrite(String Pass,String Who) {
        return "ROOMs/"+Pass+"/Users/"+Who+"/OnlineOrWrite";
    }

    public static String getRef_Name(String Pass,String Who) {
        return "ROOMs/"+Pass+"/Users/"+Who;
    }

    public static String getRef_Message(String Pass,String Who) {
        return "ROOMs/"+Pass+"/Users/"+Who+"/Message";
    }

    public static String getRef_ShowedOrSended(String Pass,String Who) {
        return "ROOMs/"+Pass+"/Users/"+Who+"/Showed_Or_Sended";
    }

    public static String getRef_Users(String Pass) {
        return "ROOMs/"+Pass+"/Users";
    }

    public static String getRef_UserName(String Pass, String Who) {
        return "ROOMs/"+Pass+"/Users/"+Who+"/Name";
    }
}
