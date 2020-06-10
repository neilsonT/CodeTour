package com.example.codetour.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.codetour.Delete_Popup;
import com.example.codetour.R;
import com.example.codetour.ScheduleListPresenter;
import com.example.codetour.ScheduleService;
import com.example.codetour.TripSchedule;

import java.util.List;

public class ScheduleListAdapter extends BaseAdapter implements ScheduleListItemContract.View{  //ScheduleListView와 연결되는 Adapter
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<TripSchedule> sample;
    ScheduleListItemContract.Presenter scheduleListItemPresenter;

    public ScheduleListAdapter(Context context, List<TripSchedule> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
        scheduleListItemPresenter = new ScheduleListItemPresenter(this);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public TripSchedule getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(final int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.schedule_list_item, null);

        TextView scheduleName = (TextView)view.findViewById(R.id.scheduleName);
        TextView dateTextView = (TextView)view.findViewById(R.id.datetextview);
        Button delete_btn=(Button)view.findViewById(R.id.delete_btn);

        scheduleName.setText(sample.get(position).getName());   //List에서 index받아 해당위치에 있는 일정이름 TextView에 표시
        dateTextView.setText(sample.get(position).getStartDate()+"~"+sample.get(position).getEndDate());


        delete_btn.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    String name= ScheduleService.getInstance().tripScheduleList.get(position).getName();
                    scheduleListItemPresenter.Delete_Schedule(position);
                    Intent intent = new Intent(mContext, Delete_Popup.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name",name);
                    mContext.startActivity(intent);
                }
        });

        return view;
    }


    public void UpdateView(){
        notifyDataSetChanged();
    }

}
