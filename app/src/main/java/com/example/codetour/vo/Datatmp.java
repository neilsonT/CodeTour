package com.example.codetour.vo;

public class Datatmp {  //ScheduleListView에서 사용할 데이터 내용들입니다.
    private int poster;
    private String schedulename;
    private String startdate;
    private String finishdate;

    public Datatmp(String schedulename, String startdate,String finishdate){
        this.schedulename = schedulename;
        this.startdate=startdate;
        this.finishdate=finishdate;
    }
    public String getScheduleName()
    {
        return this.schedulename;
    }

    public String getStartDate() {return this.startdate; }

    public String getFinishDate()
    {
        return this.finishdate;
    }
}
