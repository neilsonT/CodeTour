package com.example.codetour;

public class ScheduleService {
    TripScheduleManager tripScheduleManager;

    public ScheduleService(){

    }

    public void initSchedule(){}
    public void addSchedule(TripSchedule tripSchedule){
        tripScheduleManager.addSchedule(tripSchedule);
    }
    public void deleteSchedule(){}
    public void loadSchedule(){}
}
