package com.example.codetour;

import java.util.List;

public interface ScheduleListContract{
    interface View extends BaseContract.View{
        void showScheduleList(List<TripSchedule> trip_schedule_list);
        void showSchedule(TripSchedule trip_schedule);
    }

    interface Presenter extends BaseContract.Presenter<View>{
        void getScheduleList();
        void getSchdule(int position);
    }
}
