package com.example.codetour;


import java.util.List;

public interface InputContract {
    interface View extends BaseContract.View{

    }
    interface Presenter extends BaseContract.Presenter<View>{
        void makeTripSchedule(String name, String startDate, String endDate, int pNum,
                              int tourBudget, int accBudget, int[] startTime, int[] endTime, List<Integer> food_selection, List<Integer> theme_selection);
        void clear();
        TripSchedule getTripSchedule();
        
    }
}
