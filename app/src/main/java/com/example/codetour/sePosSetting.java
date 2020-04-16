package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class sePosSetting extends AppCompatActivity {
    //사용되는 객체. sePos는 이후 일정에 대한 클래스로 대체 예정
    sePos sepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_pos_setting);

        //이전 페이지로부터 데이터들을 받아옴
        Intent seIntent = getIntent();

        //sePos가 내부적으로 Exception을 throw하므로, try-catch를 사용
        try {
            sepos = new sePos(seIntent.getIntExtra("days", -1));
            sepos.startPos = seIntent.getStringArrayExtra("stPos");
            sepos.endPos = seIntent.getStringArrayExtra("edPos");
        }
        catch(Exception e){
            sePos sepos = new sePos();
        }

        //동적으로 테이블 생성.
        TableLayout seEdit = (TableLayout)findViewById(R.id.seEdit);

        //날짜 만큼 row를 생성해서 위에 만든 테이블에 추가함
        for(int i=0; i<sepos.days; ++i){
            //row 생성
            TableRow tr = new TableRow(this);

            //몇일째인지 날짜를 보여주는 텍스트를 row에 추가
            TextView text = new TextView(this);
            text.setText(new String((i+1)+"일째"));
            tr.addView(text);

            //i번째날 출발지 입력칸을 row에 추가
            EditText stPos = new EditText(this);
            stPos.setText(sepos.startPos[i]);
            stPos.setId(2*i);
            tr.addView(stPos);

            //i번째날 도착지 입력칸을 row에 추가
            EditText edPos = new EditText(this);
            edPos.setText(sepos.endPos[i]);
            edPos.setId(2*i+1);
            tr.addView(edPos);

            //row를 테이블에 추가
            seEdit.addView(tr);
        }
    }
}
