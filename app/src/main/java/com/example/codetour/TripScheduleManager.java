package com.example.codetour;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TripScheduleManager {

    public List<TripSchedule> tripScheduleList;

    public TripScheduleManager(){
        tripScheduleList=new ArrayList<>();
    }

        public void initSchedule(){}
        public void addSchedule(TripSchedule tripSchedule){
            tripScheduleList.add(tripSchedule);
        }
        public void deleteSchedule(){}
        public void loadSchedule(){}

    //생성자는 load를 위해, toJSONObj는 save를 위해 사용.
    public TripScheduleManager(JSONObject obj){
        try {
            tripScheduleList = new ArrayList<TripSchedule>();
            JSONArray tripScheduleListTmp = obj.getJSONArray("tripScheduleList");
            int len = tripScheduleListTmp.length();
            for(int i=0; i<len; ++i){
                tripScheduleList.add(new TripSchedule(tripScheduleListTmp.getJSONObject(i)));
            }
        }
        catch(Exception e){}
    }
    public JSONObject toJSONObj(){
        JSONObject ret = new JSONObject();
        JSONArray tripScheduleListTmp = new JSONArray();
        try{
            int len = tripScheduleList.size();
            for(int i=0; i<len; ++i){
                tripScheduleListTmp.put(tripScheduleList.get(i).toJSONObj());
            }
            ret.put("tripScheduleList", tripScheduleListTmp);
        }
        catch(Exception e){}
        return  ret;
    }
}


