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

    public void TEMP(){
        tour.areacode = 1;
        tour.contentTypeID = "12";
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode>0 && requestCode<=tour.difdays){
                if(requestCode%2 == 1){
                    ((TextView)findViewById(stPosID[(requestCode-1)/2])).setText(data.getStringExtra("title"));
                    // TODO: address를 받아와서 어디에 저장할지? 나중에 넘겨줄땐 어떻게 넘겨줄지?
                }
                else{
                    ((TextView)findViewById(edPosID[(requestCode-1)/2])).setText(data.getStringExtra("title"));
                    // TODO: 위와 같음
                }
            }
        }
    }

    public void InitializeView() {
        //테이블 틀 생성
        seEdit = (TableLayout) findViewById(R.id.seEdit);
        sePosSettingConfirm = (Button) findViewById(R.id.sePosSettingConfirm);

        sePosSettingConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView tmp;
                for(int i=0; i<tour.difdays; ++i){
                    tmp = (TextView)findViewById(stPosID[i]);
                    tour.startPoss.set(i, tmp.getText().toString());

                    tmp = (TextView)findViewById(edPosID[i]);
                    tour.endPoss.set(i, tmp.getText().toString());
                }

                TEMP();
                //TODO: 현재 TEMP() 함수를 이용하여 areacode와 contentTypeID를 강제로 넘겨 주고 있으므로, 받아온 값을 넘겨주도록 

                Recommend rec = new Recommend();
                rec.setTripSchedule(tour);
                rec.setRecommendSpotList();
                rec.makeCourses();

                Intent intent = new Intent(getApplicationContext(), RouteCheck.class);
                intent.putExtra("rec", rec);
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
            final int finalI = 2*i;

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
            TextView stPos = new TextView(this);
            stPos.setText(tour.startPoss.get(i));
            tmpID = View.generateViewId();
            stPos.setId(tmpID);
            stPosID[i] = tmpID;
            stPos.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), SearchView.class);
                    startActivityForResult(intent, finalI +1);
                }
            });
            tr.addView(stPos, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3));

            //i번째날 도착지 입력칸을 row에 추가
            TextView edPos = new TextView(this);
            edPos.setText(tour.endPoss.get(i));
            tmpID = View.generateViewId();
            edPos.setId(tmpID);
            edPosID[i] = tmpID;
            edPos.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(getApplicationContext(), SearchView.class);
                    startActivityForResult(intent, finalI +2);
                }
            });
            tr.addView(edPos, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3));

            //row를 테이블에 추가
            seEdit.addView(tr);
        }
    }

    public void FillTable(){
        TextView tmp;
        for(int i=0; i<this.tour.difdays; ++i){
            tmp = (TextView)findViewById(stPosID[i]);
            tmp.setText(tour.startPoss.get(i));

            tmp = (TextView) findViewById(edPosID[i]);
            tmp.setText(tour.endPoss.get(i));
        }
    }
}
