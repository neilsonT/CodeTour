package com.example.codetour;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseManager implements Serializable {
    List<Course> courseList;

    public CourseManager() {
        courseList = new ArrayList<Course>();
    }
    public CourseManager(int difdays, int[] startTime, int[] endTime){
        this();
       for (int i=0;i<difdays;i++){
           courseList.add(new Course(i+1,startTime,endTime));
       }

    }

    //생성자는 load를 위해, toJSONObj는 save를 위해 사용.
    public CourseManager(JSONObject obj){
//System.out.println("    Loading CourseManager...");
        courseList = new ArrayList<Course>();
        try {
            JSONArray courseListTmp = obj.getJSONArray("courseList");
            int len = courseListTmp.length();
            for(int i=0; i<len; ++i){
                courseList.add(new Course(courseListTmp.getJSONObject(i)));
            }
//System.out.println("    Complete");
        }
        catch(Exception e){ System.out.println("Error at Loading CourseManager"); }
    }
    public JSONObject toJSONObj(){
//System.out.println("    Saving CourseManager...");
        JSONObject ret = new JSONObject();
        JSONArray courseListTemp = new JSONArray();
        try{
            int len = courseList.size();
            for(int i=0; i<len; ++i){
                courseListTemp.put(courseList.get(i).toJSONObj());
            }
            ret.put("courseList", courseListTemp);
//System.out.println("    Complete");
        }
        catch(Exception e){ System.out.println("Error at Saving CourseManager"); }
        return ret;
    }

    public void addSpotAt(int dIdx, int sIdx, Spot spot){
        courseList.get(dIdx).addSpotAt(sIdx, spot);
    }

    public List<Course> getCourseList() {
        return courseList;
    }
}
