package com.example.codetour;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
        //Text에 Bold효과 주기위한 코드입니다.

        TextView textView = (TextView)findViewById(R.id.schedulelist_toptext);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);

        scheduleListPresenter = new ScheduleListPresenter(this);
        scheduleListPresenter.getScheduleList();

    }

    public void backMainActivity(View view){    //홈으로 돌아가는 버튼 onClick
        Intent intent = new Intent(this,MainActivity.class);
        startActivityForResult(intent,0);
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
    }

    @Override
    public void showSchedule(Recommend rec){
        Toast.makeText(getApplicationContext(),rec.tripSchedule.getName(),Toast.LENGTH_LONG).show();

        //RouteCheck로 이동합니다.
        Intent intent= new Intent(this, RouteCheck.class);
        intent.putExtra("rec",rec);
        startActivityForResult(intent,0);
    }
}
