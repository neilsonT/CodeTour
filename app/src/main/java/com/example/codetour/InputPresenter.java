package com.example.codetour;

import android.content.Context;
import android.content.Intent;

public class InputPresenter implements InputContract.Presenter{
    InputContract.View view;
    ScheduleService scheduleService;
    TripSchedule tour;
    @Override
    public void setView(InputContract.View view) {
        this.view = view;
    }
    @Override
    public void releaseView() {
        view=null;
    }
    @Override
    public void makeTripSchedule(String name, String startDate, String endDate, int pNum,
                          int tourBudget, int accBudget, int[] startTime, int[] endTime){
        this.tour = new TripSchedule("null",startDate,endDate,pNum,tourBudget,accBudget,startTime,endTime);
        System.out.println("객체 생성 완료");
    }

    @Override
    public void clear() {
        tour=null;
    }

    @Override
    public TripSchedule getTripSchedule() {
        return tour;
    }

}
