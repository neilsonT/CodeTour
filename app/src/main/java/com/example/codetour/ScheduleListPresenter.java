package com.example.codetour;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ScheduleListPresenter implements ScheduleListContract.Presenter {

    private ScheduleListContract.View scheduleListView;


    public ScheduleListPresenter(ScheduleListContract.View view){
        // View 연결
        scheduleListView = view;
    }


    @Override
    public void setView(ScheduleListContract.View scheduleView){
        this.scheduleListView =scheduleView;
    }

    @Override
    public void releaseView(){this.scheduleListView =null;}

    @Override
    public void getScheduleList(){

        scheduleListView.showScheduleList(ScheduleService.getInstance().tripScheduleList);

    }

    @Override
    public void getSchdule(int position){
        //tripSchduleList에서 position위치의 tripSchdule을 가져옵니다.
        Recommend rec=new Recommend();
        rec.setTripSchedule(ScheduleService.getInstance().tripScheduleList.get(position));
        scheduleListView.showSchedule(rec);

    }

    @Override
    public void loadScheduleList(Context ctx, String key){
        ScheduleService.getInstance().tripScheduleList = SaveLoadManager.loadTripScheduleList(ctx, key);
    }
}
