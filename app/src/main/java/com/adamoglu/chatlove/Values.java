package com.adamoglu.chatlove;


public class Values {

    private String message,time,name,mac;


    public Values(String message, String time, String mac,String name){

        this.message=message;
        this.time=time;
        this.name=name;
        this.mac=mac;

    }

    public String getMessage(){return message;}
    public String getTime(){return time;}
    public String getMac(){return mac;}
    public String getName() {
        return name;
    }

    public void setMessage(String message){ this.message=message;}
    public void setTime(String time){ this.time=time;}
    public void setMac(String name){ this.mac=name;}
    public void setName(String name) {
        this.name = name;
    }
}
