package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
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

    String[] stAddr, edAddr;
    double[][] stPosVal;
    double[][] edPosVal;

    final int backgroundColor = 0xffdddddd;
    final float fontSize = 22.f;
    final String hintStr = "입력하세요";
    TableRow.LayoutParams seParams;

    /*public void TEMP(){
        tour.areacode = 1;
        tour.contentTypeID = "12";
    }*/

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
            if(requestCode>0 && requestCode<=2*tour.difdays){
                ((TextView)findViewById((requestCode%2==1?stPosID:edPosID)[(requestCode-1)/2])).setText(data.getStringExtra("title"));
                (requestCode%2==1?stAddr:edAddr)[(requestCode-1)/2] = data.getStringExtra("addr");
                (requestCode%2==1?stPosVal:edPosVal)[(requestCode-1)/2] = data.getDoubleArrayExtra("point");
            }
        }
    }

    public void InitializeView() {
        //테이블 틀 생성
        seEdit = (TableLayout) findViewById(R.id.seEdit);
        sePosSettingConfirm = (Button) findViewById(R.id.sePosSettingConfirm);

        seParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3);
        seParams.setMargins(1,0,1,0);

        sePosSettingConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView tmp;
                for(int i=0; i<tour.difdays; ++i){
                    tmp = (TextView)findViewById(stPosID[i]);
                    tour.startPoss.set(i, tmp.getText().toString());
                    tour.startAddr.set(i, stAddr[i]);
                    tour.startPosVal[i] = stPosVal[i];
                    String[] add = stAddr[i].split(" ");
                    int[] temp= TourApiManager.getInstance().getAddtoCode(add[0],add[1]);
                    tour.areacode[0][i] = temp[0];
                    tour.sigungucode[0][i] = temp[1];
                    tmp = (TextView)findViewById(edPosID[i]);
                    tour.endPoss.set(i, tmp.getText().toString());
                    tour.endAddr.set(i, edAddr[i]);
                    tour.endPosVal[i] = edPosVal[i];
                    add = edAddr[i].split(" ");
                    temp= TourApiManager.getInstance().getAddtoCode(add[0],add[1]);
                    tour.areacode[1][i] = temp[0];
                    tour.sigungucode[1][i] = temp[1];
                }
                System.out.println("출발도착지 test");
                System.out.println(tour.areacode[0][0]+", "+tour.areacode[1][0]);
                System.out.println(tour.areacode[0][1]+", "+tour.areacode[1][1]);
                //TEMP();
                //TODO: 현재 TEMP() 함수를 이용하여 areacode와 contentTypeID를 강제로 넘겨 주고 있으므로, 받아온 값을 넘겨주도록 

                Recommend rec = new Recommend();

                rec.setTripSchedule(tour);
                //rec.setRecommendSpotList();
                //rec.RecWithClustering();
                rec.recommendCourse();
                //rec.makeCourses();
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
        stAddr = new String[tour.difdays];
        edAddr = new String[tour.difdays];

        stPosVal = new double[tour.difdays][];
        edPosVal = new double[tour.difdays][];
        for(int i=0; i<tour.difdays; ++i){
            stPosVal[i] = new double[2];
            edPosVal[i] = new double[2];
        }
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
            tmpRowParams.setMargins(0, 10, 0, 10);
            tr.setLayoutParams(tmpRowParams);

            //몇일째인지 날짜를 보여주는 텍스트를 row에 추가
            TextView text = new TextView(this);
            text.setText(new String((i+1)+"일째"));
            text.setGravity(Gravity.CENTER_VERTICAL);
            text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);
            text.setHeight(100);
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
            stPos.setHeight(100);
            stPos.setTextSize(fontSize);
            stPos.setBackgroundColor(backgroundColor);
            if(i==0) stPos.setHint(hintStr);
            stPos.setGravity(Gravity.CENTER);
            tr.addView(stPos, seParams);

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
            edPos.setHeight(100);
            edPos.setTextSize(fontSize);
            edPos.setBackgroundColor(backgroundColor);
            if(i==0) edPos.setHint(hintStr);
            edPos.setGravity(Gravity.CENTER);
            tr.addView(edPos, seParams);

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
