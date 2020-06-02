package com.example.codetour;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import com.example.codetour.fragment.ScheduleListAdapter;


public class ScheduleList extends AppCompatActivity implements ScheduleListContract.View {   //ScheduleListView의 생성(+Data받아오기, 전달 추가 필요)

    ScheduleListContract.Presenter scheduleListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        scheduleListPresenter = new ScheduleListPresenter(this);

        scheduleListPresenter.getScheduleList();

    }

    @Override
    public void showScheduleList(List<TripSchedule> trip_schedule_list){
        ListView listView = (ListView)findViewById(R.id.listView);

        if(trip_schedule_list!=null){
            ScheduleListAdapter myAdapter = new ScheduleListAdapter(this,trip_schedule_list);

            listView.setAdapter(myAdapter); //listView와 Adapter연결
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id){ //item click시 동작
                   scheduleListPresenter.getSchdule(position);
                }
            });
        }
        else {

        }
    }

    @Override
    public void showSchedule(TripSchedule trip_schedule){
        Toast.makeText(getApplicationContext(),trip_schedule.getName(),Toast.LENGTH_LONG).show();

        //RouteCheck로 이동합니다.
        //Intent intent= new Intent(this, RouteCheck.class);
        //intent.putExtra("tripSchedule",trip_schedule);
        //startActivityForResult(intent,0);
    }
}
