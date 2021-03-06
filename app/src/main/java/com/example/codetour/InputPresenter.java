package com.example.codetour;


import java.util.List;

public class InputPresenter implements InputContract.Presenter{
    InputContract.View view;
    ScheduleService scheduleService;
    TripSchedule tour;

    public InputPresenter(){}

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
                                 int tourBudget, int accBudget, int[] startTime, int[] endTime, List<Integer> food_selection, List<Integer> theme_selection){
        this.tour = new TripSchedule("null",startDate,endDate,pNum,tourBudget,accBudget,startTime,endTime,food_selection,theme_selection);

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
