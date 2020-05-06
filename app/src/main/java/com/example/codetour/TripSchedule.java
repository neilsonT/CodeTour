package com.example.codetour;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripSchedule implements Serializable{
    CourseManager courseManager;
    String name;
    Course[] courseList;
    String startDate;
    String endDate;
    Date sd;
    Date ed;
    int difdays;
    String destination;
    int pNum;
    int tourBudget;
    int accBudget;
    //음식 취향
    //여행 테마
    int[] startTime;
    int[] endTime;

    public TripSchedule(String name, String startDate, String endDate, int pNum,
                int tourBudget, int accBudget, int[] startTime, int[] endTime){
        this.name=name;
        this.startDate=startDate;
        this.endDate=endDate;
        this.pNum=pNum;
        this.tourBudget=tourBudget;
        this.accBudget=accBudget;
        this.startTime=startTime;
        this.endTime=endTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            sd=sdf.parse(startDate);
            ed=sdf.parse(endDate);
            difdays= (int) (ed.getTime()-sd.getTime())/(24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        courseList = new Course[difdays];

    }
    public void AddCourse(){

    }
    public void DeleteCourse(){

    }
}
