package com.example.codetour.fragment;


import com.example.codetour.ScheduleService;
import com.example.codetour.TripSchedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleListItemPresenter implements ScheduleListItemContract.Presenter {

    private ScheduleListItemContract.View scheduleListItemView;

    //모델 결정 후 모델연결


    @Override
    public void setView(ScheduleListItemContract.View scheduleView){
        this.scheduleListItemView =scheduleView;
    }

    @Override
    public void releaseView(){this.scheduleListItemView =null;}

    public ScheduleListItemPresenter(ScheduleListItemContract.View view){
        // View 연결
        scheduleListItemView = view;
        // Model 연결

    }

    @Override
    public void Delete_Schedule(int position) {

        ScheduleService.getInstance().tripScheduleList.remove(position);
        for(int i=position;i<ScheduleService.getInstance().tripScheduleList.size()-2;i++){
            ScheduleService.getInstance().tripScheduleList.get(i+1).setList_pos(i);
            ScheduleService.getInstance().tripScheduleList.set(i, ScheduleService.getInstance().tripScheduleList.get(i+1));
        }

        scheduleListItemView.UpdateView();
    }
}
