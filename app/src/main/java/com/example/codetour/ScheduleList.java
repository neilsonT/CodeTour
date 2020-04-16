package com.example.codetour;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import com.example.codetour.fragment.DataAdapter;
import com.example.codetour.vo.Datatmp;


public class ScheduleList extends AppCompatActivity {   //ScheduleListView의 생성(+Data받아오기, 전달 추가 필요)

    ArrayList<Datatmp> scheduleDataList;    //임시데이터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        this.InitializescheduleData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final DataAdapter myAdapter = new DataAdapter(this,scheduleDataList);

        listView.setAdapter(myAdapter); //listView와 Adapter연결
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){ //item click시 동작
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(position).getScheduleName(),
                        Toast.LENGTH_LONG).show();  //해당 item을 받아와 이름을 보여줍니다(차후 페이지 이동&data 전달로로 수정)
            }
        });


    }

    public void InitializescheduleData()
    {
        scheduleDataList = new ArrayList<Datatmp>();

        scheduleDataList.add(new Datatmp("일정1","2020-01-03","2020-01-05"));
        scheduleDataList.add(new Datatmp("일정2","2020-03-03","2020-03-05"));
        scheduleDataList.add(new Datatmp("일정3","2020-04-01","2020-04-05"));
        scheduleDataList.add(new Datatmp("일정4","2020-04-02","2020-04-04"));
        scheduleDataList.add(new Datatmp("일정5","2020-04-06","2020-05-08"));
        scheduleDataList.add(new Datatmp("일정6","2020-03-06","2020-05-07"));
        scheduleDataList.add(new Datatmp("일정7","2020-06-06","2020-07-05"));
        scheduleDataList.add(new Datatmp("일정8입니다.","2020-07-03","2020-07-05"));

    }
}
