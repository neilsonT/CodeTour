package com.example.codetour.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.codetour.R;
import com.example.codetour.vo.Datatmp;
import java.util.ArrayList;

public class DataAdapter extends BaseAdapter {  //ScheduleListView와 연결되는 Adapter
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Datatmp> sample;

    public DataAdapter(Context context, ArrayList<Datatmp> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
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
    public Datatmp getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(final int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.content_layout, null);

        TextView scheduleName = (TextView)view.findViewById(R.id.scheduleName); //
        TextView dateTextView = (TextView)view.findViewById(R.id.datetextview); //
        Button del_btn=(Button)view.findViewById(R.id.delete_btn);  //

        try{    //delete버튼 클릭 시 동작(데이터 삭제 좀 더 보정해야합니다...)
            del_btn.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){
                    sample.remove(position);
                    notifyDataSetChanged();
                }
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        scheduleName.setText(sample.get(position).getScheduleName());   //List에서 index받아 해당위치에 있는 일정이름 TextView에 표시
        dateTextView.setText(sample.get(position).getStartDate()+"~"+sample.get(position).getFinishDate());

        return view;
    }
    
}
