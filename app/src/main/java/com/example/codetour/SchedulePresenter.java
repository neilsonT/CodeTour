package com.example.codetour;

import com.example.codetour.ScheduleContract;

public class SchedulePresenter implements ScheduleContract.Presenter {

    private ScheduleContract.View scheduleView;
//    private ScheduleService scheduleService;

    public SchedulePresenter(ScheduleContract.View view){
        this. scheduleView = view;
        // scheduleService 객체 생성 혹은 가져오기
    }

    @Override
    public void saveSchedule(TripSchedule tripSchedule) {

    }

    @Override
    public void setMarkerOverlay(String name) {

    }
}
