package com.example.codetour.fragment;

public interface ScheduleListItemContract {

    interface Presenter{
        public void Delete_Schedule(int position);
    }

    interface  View {
        public void UpdateView();
    }
}
