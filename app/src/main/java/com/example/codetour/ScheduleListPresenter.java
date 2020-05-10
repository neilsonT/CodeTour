package com.example.codetour;

import java.util.ArrayList;
import java.util.List;

public class ScheduleListPresenter implements ScheduleListContract.Presenter {

    private ScheduleListContract.View scheduleView;
    //모델 결정 후 모델연결


    List<TripSchedule> trip_schdule_list; //모델 확정전 임시모델입니다(추후삭제)

    public ScheduleListPresenter(ScheduleListContract.View view){
        // View 연결
        scheduleView = view;
        // Model 연결


        /////모델확정전 임시모델입니다(추후삭제)
        trip_schdule_list = new ArrayList<TripSchedule>();

        trip_schdule_list.add(new TripSchedule("일정1","2020-01-03","2020-01-05"));
        trip_schdule_list.add(new TripSchedule("일정2","2020-03-03","2020-03-05"));
        trip_schdule_list.add(new TripSchedule("일정3","2020-04-01","2020-04-05"));
        trip_schdule_list.add(new TripSchedule("일정4","2020-04-02","2020-04-04"));
        trip_schdule_list.add(new TripSchedule("일정5","2020-04-06","2020-05-08"));
        trip_schdule_list.add(new TripSchedule("일정6","2020-03-06","2020-05-07"));
        trip_schdule_list.add(new TripSchedule("일정7","2020-06-06","2020-07-05"));
        trip_schdule_list.add(new TripSchedule("일정8입니다.","2020-07-03","2020-07-05"));
    }

    @Override
    public void getScheduleList(){

        //모델에서 schduleList전체를 받아옵니다.

        scheduleView.showScheduleList(trip_schdule_list);

    }

    @Override
    public void getSchdule(int position){
        //tripSchduleList에서 position위치의 tripSchdule을 가져옵니다.

        scheduleView.showSchedule(trip_schdule_list.get(position));

    }
}
