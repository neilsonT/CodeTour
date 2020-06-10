package com.example.codetour;

import android.content.Context;

import java.util.List;

public interface ScheduleListContract{
    interface View extends BaseContract.View{
        void showScheduleList(List<TripSchedule> trip_schedule_list);
        void showSchedule(Recommend rec);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void getScheduleList();
        void getSchdule(int position);
        void loadScheduleList(Context ctx, String key);
    }
}
