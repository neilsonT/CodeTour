package com.example.codetour;

import android.content.Context;

public interface InputContract {
    interface View extends BaseContract.View{

    }
    interface Presenter extends BaseContract.Presenter<View>{
        void makeTripSchedule(String name, String startDate, String endDate, int pNum,
                                     int tourBudget, int accBudget, int[] startTime, int[] endTime);
        void clear();
        TripSchedule getTripSchedule();
    }
}
