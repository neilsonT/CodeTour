package com.example.codetour;

import java.io.Serializable;
import java.util.List;

//출발지&도착지에 대한 임시 클래스. 이후 일정에 대한 클래스로 대체 예정
public class sePos implements Serializable {
    public int days;
    public List<String> startPos;
    public List<String> endPos;

    public sePos(){
        days = 4;
        for(int i=0; i<days; ++i){
            startPos.add("");
            endPos.add("");
        }
    }

    public sePos(int d) throws Exception{
        if(d<=0) throw new Exception();

        this.days = d;
        for(int i=0; i<days; ++i){
            startPos.add("");
            endPos.add("");
        }
    }

    public sePos(int d, List<String> st, List<String> ed) throws Exception{
        this.days = d;
        this.startPos = st;
        this.endPos = ed;

        if(d<=0 || st.size() != d || ed.size() != d) throw new Exception();
    }
}
