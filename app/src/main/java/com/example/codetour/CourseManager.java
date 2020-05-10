package com.example.codetour;

import java.util.List;

public class CourseManager {
    List<Course> courseList;
    public CourseManager(int difdays,int[] startTime, int[] endTime){
       for (int i=0;i<difdays;i++){
           courseList.add(new Course(i+1,startTime,endTime));
       }

    }
}
