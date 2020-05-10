package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class sePosSetting extends AppCompatActivity implements SePosSettingContract.View {
    //사용되는 객체. sePos는 이후 일정에 대한 클래스로 대체 예정
    sePos sepos;
    SePosSettingContract.Presenter presenter;
    Intent seIntent;

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
        this.FillseEdit();
    }

    public void InitializeView() {
        //테이블 틀 생성
        seEdit = (TableLayout) findViewById(R.id.seEdit);
    }

    public void GetDataFromPrevView(){
        //이전 페이지로부터 데이터들을 받아옴
        seIntent = getIntent();
        tour=(TripSchedule) seIntent.getSerializableExtra("class");

        //sePos가 내부적으로 Exception을 throw하므로, try-catch를 사용
        try {
            //정보들을 따로 getExtra로 안건넨받고 Tour class에 담아서 객체를 위에서 받았다. 따라서 여기부터 tour의 필드값 이용하면 된다.
            sepos = new sePos(tour.difdays);
            //startPos 랑 endPos도 tour 인스턴스 필드 참조하면 되는데 아직 어떤 타입으로 필드 채울지 안정함
            //바다 : startPos와 endPos 전 화면에서 받는게 아니라 이 화면에서 받는겅 아녀?
            //대양 : 이미 저장된 startPos, endPos가 있을 때 불러오지 않고 새로 만들어서 하면 이미 저장된 것이 날아갈 가능성이 있기 때문에 이렇게 함.
            sepos.startPos = seIntent.getStringArrayExtra("stPos");
            sepos.endPos = seIntent.getStringArrayExtra("edPos");
        }
        catch(Exception e){
            sepos = new sePos();
        }
        sepos = new sePos();
    }

    public void  FillseEdit(){
        //InitializeView()에서 생성해 둔 테이블 틀을 채움;
        //날짜 만큼 row를 생성해서 위에 만든 테이블에 추가함
        for(int i=0; i<sepos.days; ++i){
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
            stPos.setText(sepos.startPos[i]);
            stPos.setId(2*i);
            tr.addView(stPos, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3));

            //i번째날 도착지 입력칸을 row에 추가
            EditText edPos = new EditText(this);
            edPos.setText(sepos.endPos[i]);
            edPos.setId(2*i+1);
            tr.addView(edPos, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 3));

            //row를 테이블에 추가
            seEdit.addView(tr);
        }
    }
}
