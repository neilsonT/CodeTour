package com.example.codetour.fragment;


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

        //연결 된 모델에서 position위치의 tripSchedule삭제
        System.out.println(position+"번 째가 삭제되었습니다.");
        scheduleListItemView.UpdateView();
    }
}
