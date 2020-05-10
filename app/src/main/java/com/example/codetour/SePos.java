package com.example.codetour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//출발지&도착지에 대한 임시 클래스. 이후 일정에 대한 클래스로 대체 예정
public class SePos implements Serializable {
    public int days;
    public List<String> startPos;
    public List<String> endPos;

    public SePos(){
        days = 4;
        startPos = new ArrayList<String>();
        endPos = new ArrayList<String>();
        for(int i=0; i<days; ++i){
            startPos.add("");
            endPos.add("");
        }
    }

    public SePos(int d) throws Exception{
        if(d<=0) throw new Exception();

        this.days = d;
        startPos = new ArrayList<String>();
        endPos = new ArrayList<String>();
        for(int i=0; i<days; ++i){
            startPos.add("");
            endPos.add("");
        }
    }

    public SePos(int d, List<String> st, List<String> ed) throws Exception{
        this.days = d;
        this.startPos = st;
        this.endPos = ed;

        if(d<=0 || st.size() != d || ed.size() != d) throw new Exception();
    }
}
