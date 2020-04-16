package com.example.codetour;

//출발지&도착지에 대한 임시 클래스. 이후 일정에 대한 클래스로 대체 예정
public class sePos {
    public int days;
    public String[] startPos;
    public String[] endPos;

    public sePos(){
        days = 1;
        startPos = new String[1];
        endPos = new String[1];
    }

    public sePos(int d) throws Exception{
        if(d<=0) throw new Exception();

        this.days = d;
        startPos = new String[this.days];
        endPos = new String[this.days];
    }

    public sePos(int d, String[] st, String[] ed) throws Exception{
        this.days = d;
        this.startPos = st;
        this.endPos = ed;

        if(d<=0 || st.length != d || ed.length != d) throw new Exception();
    }
}
