package com.example.codetour;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    List<String> food_selection;
    List<String> theme_selection;
    int[] startTime;
    int[] endTime;
    List<String> startPoss;
    List<String> endPoss;

    public TripSchedule(String name,String startDate,String endDate){   //임시생성자입니다 SchduleListPresenter에서 사용합니다.
        this.name=name;
        this.startDate=startDate;
        this.endDate=endDate;
    }
    public TripSchedule(String name, String startDate, String endDate, int pNum,
                int tourBudget, int accBudget, int[] startTime, int[] endTime, List<String> food_selection, List<String> theme_selection){
        this.name=name;
        this.startDate=startDate;
        this.endDate=endDate;
        this.pNum=pNum;
        this.tourBudget=tourBudget;
        this.accBudget=accBudget;
        this.startTime=startTime;
        this.endTime=endTime;
        this.food_selection=food_selection;
        this.theme_selection=theme_selection;
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
    public void makeCourse(){

    }
    public void AddCourse(){

    }
    public void DeleteCourse(){

    }

    public String getName(){return name;}
    public String getStartDate(){return startDate;}
    public String getEndDate(){return endDate;}
}
