package com.example.codetour;

import java.util.List;

public class Course {
    SpotManager spotManager;
    int order;
    int[] timeList; //소요시간 list
    List<Spot> spotList;
    int[] startTime;
    int[] endTime;
    int[] startPos; //xy 좌표 형태로 받을 것을 예상
    int[] endPos;
    int countSpot;

    public Course(int num, int[] startTime, int[] endTime){
        this.order=num;
        this.startTime=startTime;
        this.endTime=endTime;
        spotManager = new SpotManager();
    }
    public void addSpot(){

    }
    public void deleteSpot(){

    }
    public void changeSpot(){

    }


}
