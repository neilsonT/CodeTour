package com.example.codetour.fragment;

import com.example.codetour.BaseContract;

public interface ScheduleListItemContract {

    interface Presenter extends  BaseContract.Presenter<View>{
        public void Delete_Schedule(int position);
    }

    interface  View extends BaseContract.View {
        public void UpdateView();
    }
}
