package com.example.codetour;

import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    List<Course> courseList;

    public CourseManager() {
        courseList = new ArrayList<Course>();
    }
    public CourseManager(int difdays,int[] startTime, int[] endTime){
        this();
       for (int i=0;i<difdays;i++){
           courseList.add(new Course(i+1,startTime,endTime));
       }

    }
}
