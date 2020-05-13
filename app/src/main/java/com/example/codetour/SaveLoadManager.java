package com.example.codetour;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class SaveLoadManager {
    //이 클래스는 두개 이상이 존재할 필요도 없고, 존재해서도 안되므로 필드는 없애고 모든 메소드를 static으로 구현함.

    //TripScheduleManager는 아마도 그럴 일이 없지 싶긴 하지만, 혹시나 하여 save/load 함수의 형태만 잡아 두었음. 아래와 비슷한 모양으로 구현 가능.
    public static TripScheduleManager loadTripScheduleManager(Context context, String key){
        return null;
    }
    public static void saveTripScheduleManager(Context context, String key, TripScheduleManager tripScheduleManager){}

    public static ArrayList<TripSchedule> loadTripScheduleList(Context context, String key){
        ArrayList<TripSchedule> ret = new ArrayList<TripSchedule>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);

        if(json!=null) try{
            JSONArray tripScheduleListTmp = new JSONArray(json);
            int len = tripScheduleListTmp.length();
            for(int i=0; i<len; ++i){
                ret.add(new TripSchedule(tripScheduleListTmp.getJSONObject(i)));
            }
        }
        catch (Exception e){}
        return ret;
    }
    public static void saveTripScheduleList(Context context, String key, List<TripSchedule> tripScheduleList){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray tripScheduleListTmp = new JSONArray();
        final int len = tripScheduleList.size();
        for(int i=0; i<len; ++i){
            tripScheduleListTmp.put(tripScheduleList.get(i).toJSONObj());
        }
        editor.putString(key, tripScheduleListTmp.toString());
        editor.apply();
    }
}
