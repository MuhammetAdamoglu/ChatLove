package com.adamoglu.chatlove;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<String> {
    private final ChatMain chatMain;
    private final ArrayList values_message;
    private final ArrayList values_time;
    private final ArrayList control;
    private final ArrayList name;
    private final String myname;
    private int sended_or_showed;

    private SparseBooleanArray mSelectedItemsIds;

    public Adapter(ChatMain chatMain, ArrayList values_message, ArrayList values_time, ArrayList control, ArrayList name, String myname) {
        super(chatMain, R.layout.listview, values_message);
        this.chatMain = chatMain;
        this.values_message = values_message;
        this.values_time = values_time;
        this.control=control;
        this.name=name;
        this.myname=myname;
        mSelectedItemsIds = new SparseBooleanArray();

    }


    public void setSended_or_showed(int sended_or_showed) {
        this.sended_or_showed = sended_or_showed;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) chatMain
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;


            if(control.get(position).toString().trim().equals("1"))
            {
                if(name.get(position).toString().trim().equals(myname))
                {
                    if(values_message.size()-1==position){
                        rowView = inflater.inflate(R.layout.listview_last, parent, false);

                        TextView textView_showed = (TextView) rowView.findViewById(R.id.showed);

                        if (sended_or_showed==2){
                           textView_showed.setText("Görüldü");
                        }
                        else if (sended_or_showed==1){
                            textView_showed.setText("İletildi");
                        }
                        else {
                            textView_showed.setText("");
                        }




                    }
                    else {
                        rowView = inflater.inflate(R.layout.listview, parent, false);
                    }


                }
                else {
                    rowView = inflater.inflate(R.layout.listview_other_message, parent, false);
                }


            }
            else {
                rowView = inflater.inflate(R.layout.listview_sending, parent, false);
            }

        TextView textView_message = (TextView) rowView.findViewById(R.id.Textview_Message);
        TextView textView_time = (TextView) rowView.findViewById(R.id.TextView_Time);

        textView_time.setText(values_time.get(position).toString());
        textView_message.setText(values_message.get(position).toString());

        return rowView;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {

        try {
            if (value)
                mSelectedItemsIds.put(position, value);
            else
                mSelectedItemsIds.delete(position);
            notifyDataSetChanged();
        }catch (Exception ex){
            Toast.makeText(chatMain, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }


}