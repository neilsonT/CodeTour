package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SePosSetting extends AppCompatActivity implements SePosSettingContract.View {
    //사용되는 객체. sePos는 이후 일정에 대한 클래스로 대체 예정
    SePosSettingContract.Presenter presenter;
    Intent seIntent;
    int[] stPosID;
    int[] edPosID;
    Button sePosSettingConfirm;

    TableLayout seEdit;
    TripSchedule tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_pos_setting);
        presenter = new SePosSettingPresenter();
        presenter.setView(this);

        this.InitializeView();
        this.GetDataFromPrevView();
        this.MakeTable();
        this.FillTable();
    }

    public void InitializeView() {
        //테이블 틀 생성
        seEdit = (TableLayout) findViewById(R.id.seEdit);
        sePosSettingConfirm = (Button) findViewById(R.id.sePosSettingConfirm);

        sePosSettingConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText tmp;
                for(int i=0; i<tour.difdays; ++i){
                    tmp = (EditText)findViewById(stPosID[i]);
                    tour.startPoss.set(i, tmp.getText().toString());

                    tmp = (EditText)findViewById(edPosID[i]);
                    tour.endPoss.set(i, tmp.getText().toString());
                }

                Intent intent = new Intent(getApplicationContext(), RouteCheck.class);
                intent.putExtra("tour", tour);
                startActivity(intent);
            }
        });
    }

    public void GetDataFromPrevView(){
        //이전 페이지로부터 데이터들을 받아옴
        seIntent = getIntent();
        tour=(TripSchedule) seIntent.getSerializableExtra("class");

        stPosID = new int[tour.difdays];
        edPosID = new int[tour.difdays];
    }

    public void  MakeTable(){
        int tmpID;
        //InitializeView()에서 생성해 둔 테이블 틀을 채움;
        //날짜 만큼 row를 생성해서 위에 만든 테이블에 추가함
        for(int i=0; i<tour.difdays; ++i){
            //row 생성
            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tmpRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 4);
            tr.setLayoutParams(tmpRowParams);

            //몇일째인지 날짜를 보여주는 텍스트를 row에 추가
            TextView text = new TextView(this);
            text.setText(new String((i+1)+"일째"));
            text.setGravity(Gravity.CENTER_VERTICAL);
            tr.addView(text, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1));

            //아직 위치값을 넣지 않았기 때문에 실행해보기 위해서 주석처리 해놓음

            //i번째날 출발지 입력칸을 row에 추가
            EditText stPos = new EditText(this);
            stPos.setText(tour.startPoss.get(i));
            tmpID = View.generateViewId();
            stPos.setId(tmpID);
            stPosID[i] = tmpID;
            tr.addView(stPos, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3));

            //i번째날 도착지 입력칸을 row에 추가
            EditText edPos = new EditText(this);
            edPos.setText(tour.endPoss.get(i));
            tmpID = View.generateViewId();
            edPos.setId(tmpID);
            edPosID[i] = tmpID;
            tr.addView(edPos, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3));

            //row를 테이블에 추가
            seEdit.addView(tr);
        }
    }

    public void FillTable(){
        EditText tmp;
        for(int i=0; i<this.tour.difdays; ++i){
            tmp = (EditText)findViewById(stPosID[i]);
            tmp.setText(tour.startPoss.get(i));

            tmp = (EditText)findViewById(edPosID[i]);
            tmp.setText(tour.endPoss.get(i));
        }
    }
}
