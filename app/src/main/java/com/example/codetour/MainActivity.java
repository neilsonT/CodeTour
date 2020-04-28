package com.example.codetour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class MainActivity extends AppCompatActivity {
        //상수 & 임시 상수 설정
        final int days = 4;
        final int code_sePosSetting = 1;

        //사용되는 객체 선언
        Button btn_temp;
        sePos se;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //se가 내부적으로 Exception을 throw하므로 try-catch를 사용
        //sePos는 이후 일정에 해당하는 class로 대체될 예정
        try{
            se = new sePos(days);
        }
        catch(Exception e){
            se = new sePos();
        }

        //출발지 & 도착지 설정 페이지로 가는 버튼
//        btn_temp = (Button)findViewById(R.id.tempBtn);
//        btn_temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sePosSettingIntent = new Intent(getApplicationContext(), sePosSetting.class);
//                sePosSettingIntent.putExtra("days", se.days);
//                sePosSettingIntent.putExtra("stPos", se.startPos);
//                sePosSettingIntent.putExtra("edPos", se.endPos);
//                //출발지 & 도착지 설정 페이지에 데이터를 보냄; 이미 저장된 데이터가 있을 경우
//
//                startActivityForResult(sePosSettingIntent, code_sePosSetting);
//                //데이터를 받을 것으로 기대하고, 액티비티를 실행
//            }
//        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent intent) {
        super.onActivityResult(reqCode, resCode, intent);
        if (reqCode == code_sePosSetting) {
            if (resCode == RESULT_OK) {
                //TODO: 데이터를 제대로 받아 온 경우에 대한 처리
            }
        }
    }

    public void makeSchedule(View view) {
        Intent intent = new Intent(this,InputActivity.class);
        startActivityForResult(intent,0);
    }

    public void showScheduleList(View view) {
        Intent intent = new Intent(this, ScheduleList.class);
        startActivityForResult(intent,0);
    }
}
