package com.adamoglu.chatlove;

import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;


public class listview_Scroll {


    void start(final ChatMain chatMain, final ListView listView){


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                if(firstVisibleItem==0){
                    //En Üst
                    chatMain.control_Scrool=false;
                    listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                }
                else if(firstVisibleItem+visibleItemCount>=totalItemCount-1) {
                    //En Alt
                    chatMain.control_Scrool = true;
                    listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
                }
                else {
                    //Orta
                    chatMain.control_Scrool=false;
                    listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                }
            }
        });
    }
}
