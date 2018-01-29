package com.adamoglu.chatlove;

/**
 * Created by Abra on 20.01.2018.
 */

public class Arrays {
    public static void removeArray(ChatMain chatMain, int id){
        int index=chatMain.messages_id.indexOf(String.valueOf(id));

        chatMain.messages.remove(index);
        chatMain.message_array_name.remove(index);
        chatMain.message_send_control_array.remove(index);
        chatMain.message_time_array.remove(index);
        chatMain.messages_id.remove(index);
    }

    public static void addArray(ChatMain chatMain, String message, String time, String control, String Name, long new_id){
        chatMain.messages.add(message);
        chatMain.message_time_array.add(time);
        chatMain.message_send_control_array.add(control);
        chatMain.message_array_name.add(Name);
        chatMain.messages_id.add(String.valueOf(new_id));
    }

    public static void setArray(ChatMain chatMain, String message, String time, String control, String Name, long new_id, int index){
        chatMain.messages.add(index,message);
        chatMain.message_time_array.add(index,time);
        chatMain.message_send_control_array.add(index,control);
        chatMain.message_array_name.add(index,Name);
        chatMain.messages_id.add(index,String.valueOf(new_id));
    }
}
