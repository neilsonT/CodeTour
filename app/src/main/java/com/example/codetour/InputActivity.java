package com.example.codetour;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class InputActivity extends AppCompatActivity {
    //날짜 설정을 위한 변수
    private Button start_date_B;
    private Button end_date_B;
    private DatePickerDialog.OnDateSetListener callbackMethodD;
    private int DataPickerCalled; //가는날/오는날 중에 어떤 버튼을 클릭한 것인지 저장
    //시간 설정을 위한 변수
    private TimePickerDialog.OnTimeSetListener callbackMethodT;
    private Button start_time_B;
    private Button end_time_B;
    private int TimePickerCalled;
    //spinner
    private MultiSelectionSpinner food_spinner;
    private MultiSelectionSpinner theme_spinner;
    //예산 입력
    private EditText tour_budget;
    private EditText acc_budget;
    //인원 설정
    private TextView pNum;
    int num=1;
    //다음 화면에게 input정보를 넘겨주기 위한 변수들(어떤 형태로 넘겨야 할지 정하지 않아 미완)
    int[] start_date=new int[3];
    int[] end_date=new int[3];
    int[] start_time=new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) { //처음에 실행되는 함수
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_input);

        this.InitializeView();
        this.InitializeListener();
        this.FillSpinner();
    }
    public void InitializeView(){ //activity에 필요한 요소들 참조값 가져오기
        start_date_B = (Button)findViewById(R.id.start_date_B);
        end_date_B = (Button)findViewById(R.id.end_date_B);
        start_time_B=(Button)findViewById(R.id.start_time_B);
        end_time_B=(Button)findViewById(R.id.end_time_B);
        food_spinner = (MultiSelectionSpinner)findViewById(R.id.food_spinner);
        theme_spinner=(MultiSelectionSpinner)findViewById(R.id.theme_spinner);
        tour_budget=(EditText)findViewById(R.id.input_Tbudget);
        acc_budget=(EditText)findViewById(R.id.input_Abudget);
        pNum=(TextView)findViewById(R.id.pNum);
        pNum.setText(num+" 명");
    }
    public void FillSpinner(){ //음식, 테마 입력 관련, spinner 관련 함수들은 MultiSelectionSpinner 클래스에 있음
        //food와 theme각각 따로 arraylist에 항목들을 담은다음에 setItems 메소드를 통해 spinner에 넣어주면 된다.
        List<String> food_list = new ArrayList<String>();
        food_list.add("선택하세요");
        food_list.add("한식");
        food_list.add("중식");
        food_list.add("일식");
        food_list.add("양식");
        food_spinner.setItems(food_list);
        List<String> theme_list = new ArrayList<String>();
        food_list.add("선택하세요");
        theme_list.add("이거");
        theme_list.add("저거");
        theme_list.add("요거");
        theme_list.add("그거");
        theme_spinner.setItems(theme_list);
    }
    public void InitializeListener() //날짜, 시간 입력 Listener 설정
    {
        //날짜입력 '완료'버튼을 눌렀을 때 실행되는 부분
        callbackMethodD = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                //이 DataPicker를 부른 것이 가는날인지, 오는날인지 확인해서 해당 text를 바꿔준다.
                if (DataPickerCalled == R.id.start_date_B)
                    start_date_B.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                else if (DataPickerCalled == R.id.end_date_B)
                    end_date_B.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
            }
        };
        //시간입력 '완료'버튼을 눌렀을 때 실행되는 부분
        callbackMethodT = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String m; //분이 한자리수 일 때 앞에 0을 붙여주기 위한 String
                //이 TimePicker를 부른 것이 활동시간인지, 종료시간인지 확인해서 해당 text를 바꿔준다
                if (TimePickerCalled==R.id.start_time_B){
                    m=Integer.toString(minute);
                    if (minute<10){ //분이 한자리수 이면 앞에 0을 붙여서 두 자리로 만들어서 출력
                        start_time_B.setText(hourOfDay+" : 0"+m);
                    }
                    else //분이 두자리면 그냥 출력
                        start_time_B.setText(hourOfDay+" : "+m);
                }
                else if (TimePickerCalled==R.id.end_time_B){
                    m=Integer.toString(minute);
                    if (minute<10){ //분이 한자리수 이면 앞에 0을 붙여서 두 자리로 만들어서 출력
                        end_time_B.setText(hourOfDay+" : 0"+m);
                    }
                    else //분이 두자리면 그냥 출력
                        end_time_B.setText(hourOfDay+" : "+m);

                }
            }
        };
    }

    public void OnClickHandlerD(View view){ // 날짜 설정 버튼이 클릭되었을 때
        //날짜 선택 창을 처음 띄웠을 때 '오늘 날짜' 기준으로 표시하기 위해 사용
        GregorianCalendar today = new GregorianCalendar();
        int year = today.get(today.YEAR);
        int month = today.get(today.MONTH)+1;
        int day = today.get(today.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethodD,year,month,day); //여기 쓰여지는 날짜는 무슨 의미인가?
        DataPickerCalled = view.getId();
        dialog.show();
    }
    public void OnClickHandlerT(View view){ //시간 설정 버튼이 클릭되었을 때
        //시간 선택 창은 무조건 처음 값이 아침 9시인걸로 설정해두었다. 바꾸고싶으면 나중에 바꾸면 된다!
        TimePickerDialog dialog = new TimePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,callbackMethodT,9,0,false);
        TimePickerCalled=view.getId();
        dialog.show();
    }

    public void onClick(View view) { //인원 더하기,빼기 버튼을 위한 click 함수
        if (view.getId()==R.id.pNum_plus){
            num++;
            pNum.setText(num + " 명");
        }
        else if (view.getId()==R.id.pNum_minus){
            if (num > 0) {
                num--;
                pNum.setText(num + " 명");
            }
        }
    }
    public void HandOverInput(){ //여행 코스 추천 버튼을 눌렀을 때(미완)

    }
}
