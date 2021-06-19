package com.example.schoolnote;

import android.widget.Button;

import java.util.List;

public class ListItemSchedule {
    private String Title;
    private String Disc;
    private Integer[] Due;
    private Button btn_complete;

    public void setItem(String title, String disc, Integer[] due){
        this.Title = title;
        this.Disc = disc;
        this.Due = due;
    }

    public String getTitle(){
        return Title;
    }

    public String getDisc(){
        return Disc;
    }

    public String getDueStr(){
        String res = "";

        res += Due[0].toString() + "-" + Due[1].toString() + "-" + Due[2].toString();

        return res;
    }
}
