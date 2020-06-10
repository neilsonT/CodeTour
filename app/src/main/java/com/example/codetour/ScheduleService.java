package com.example.codetour;

public class ScheduleService {

    static TripScheduleManager tripScheduleManager=null;
    public static TripScheduleManager getInstance(){
        if(tripScheduleManager == null) tripScheduleManager = new TripScheduleManager();
        return tripScheduleManager;
    }

    public ScheduleService(){

    }

    public void initSchedule(){}
    public void addSchedule(TripSchedule tripSchedule){
        tripScheduleManager.addSchedule(tripSchedule);
    }
    public void setSchedule(int idx, TripSchedule tripSchedule){ tripScheduleManager.setSchedule(idx, tripSchedule); }
    public void deleteSchedule(){}
    public void loadSchedule(){}
}
