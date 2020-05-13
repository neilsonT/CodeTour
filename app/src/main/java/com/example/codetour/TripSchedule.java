package com.example.codetour;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripSchedule implements Serializable{
    CourseManager courseManager;
    String name;
    String startDate;
    String endDate;
    Date sd;
    Date ed;
    int difdays;
    int pNum;
    int tourBudget;
    int accBudget;
    List<String> food_selection;
    List<String> theme_selection;
    int[] startTime;
    int[] endTime;
    List<String> startPoss;
    List<String> endPoss;

    public TripSchedule(){}
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
//<<<<<<< HEAD
//        courseList = new Course[difdays];
        this.startPoss = new ArrayList<String>();
        this.endPoss = new ArrayList<String>();
        for(int i=0; i<difdays; ++i){
            this.startPoss.add("");
            this.endPoss.add("");
        }
//=======
        courseManager = new CourseManager(difdays, startTime, endTime);

//>>>>>>> e0c04da052e7c5e8056f596d75eb26ae5b2e6f64
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

    //생성자는 load를 위해, toJSONObj는 save를 위해 사용.
    public TripSchedule(JSONObject obj){
        try {
            courseManager = new CourseManager(obj.getJSONObject("courseManager"));
            name = obj.getString("name");
            startDate = obj.getString("startDate");
            endDate = obj.getString("endDate");

            // 대양: sd와 ed가 모두 Date형 자료형인데, 이 클래스 내에서는 이들을 다루는 메소드가 보이지 않음.
            //      및, JSONObject로부터 Date를 바로 받아올 수 있는지 확인 필요.
            //sd = ???
            //ed = ???

            difdays = obj.getInt("difdays");
            pNum = obj.getInt("pNum");
            tourBudget = obj.getInt("tourBudget");
            accBudget = obj.getInt("accBudget");

            food_selection = new ArrayList<String>();
            JSONArray food_selection_tmp = obj.getJSONArray("food_selection");
            int len_food = food_selection_tmp.length();
            for(int i=0; i<len_food; ++i){
                food_selection.add(food_selection_tmp.getString(i));
            }

            theme_selection = new ArrayList<String>();
            JSONArray theme_selection_tmp = obj.getJSONArray("theme_selection");
            int len_theme = theme_selection_tmp.length();
            for(int i=0; i<len_theme; ++i){
                theme_selection.add(theme_selection_tmp.getString(i));
            }

            startTime = new int[difdays];
            JSONArray startTimeTmp = obj.getJSONArray("startTime");
            endTime = new int[difdays];
            JSONArray endTimeTmp = obj.getJSONArray("endTime");
            startPoss = new ArrayList<String>();
            JSONArray startPossTmp = obj.getJSONArray("startPoss");
            endPoss = new ArrayList<String>();
            JSONArray endPossTmp = obj.getJSONArray("endPoss");
            for(int i=0; i<difdays; ++i){
                startTime[i] = startTimeTmp.getInt(i);
                endTime[i] = endTimeTmp.getInt(i);
                startPoss.add(startPossTmp.getString(i));
                endPoss.add(endPossTmp.getString(i));
            }
        }
        catch(Exception e){}
    }
    public JSONObject toJSONObj(){
        JSONObject ret = new JSONObject();
        JSONArray food_selection_tmp = new JSONArray();
        JSONArray theme_selection_tmp = new JSONArray();
        JSONArray startTimeTmp = new JSONArray();
        JSONArray endTimeTmp = new JSONArray();
        JSONArray startPossTmp = new JSONArray();
        JSONArray endPossTmp = new JSONArray();
        try{
            ret.put("courseManager", courseManager.toJSONObj());
            ret.put("name", name);
            ret.put("startDate", startDate);
            ret.put("endDate", endDate);
            //sd ??
            //ed ??
            ret.put("difdays", difdays);
            ret.put("pNum", pNum);
            ret.put("tourBudget", tourBudget);
            ret.put("accBudget", accBudget);

            int food_selection_len = food_selection.size();
            for(int i=0; i<food_selection_len; ++i){
                food_selection_tmp.put(food_selection.get(i));
            }
            ret.put("food_selection", food_selection_tmp);

            int theme_selection_len = theme_selection.size();
            for(int i=0; i<theme_selection_len; ++i){
                theme_selection_tmp.put(theme_selection.get(i));
            }
            ret.put("theme_selection", theme_selection_tmp);

            for(int i=0; i<difdays; ++i){
                startTimeTmp.put(startTime[i]);
                endTimeTmp.put(endTime[i]);
                startPossTmp.put(startPoss.get(i));
                endPossTmp.put(endPoss.get(i));
            }
            ret.put("startTime", startTimeTmp);
            ret.put("endTime", endTimeTmp);
            ret.put("startPoss", startPossTmp);
            ret.put("endPoss", endPossTmp);
        }
        catch(Exception e){}
        return ret;
    }
}
