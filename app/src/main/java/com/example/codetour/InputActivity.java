package com.example.codetour;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class InputActivity extends AppCompatActivity implements InputContract.View{
    InputContract.Presenter presenter;
    //날짜 설정을 위한 변수
    private Button start_date_B;
    private Button end_date_B;
    private DatePickerDialog.OnDateSetListener callbackMethodD;
    private int DataPickerCalled; //가는날/오는날 중에 어떤 버튼을 클릭한 것인지 저장
    private String startDate;
    private String endDate;
    private int[] startD;
    private int[] endD;
    //시간 설정을 위한 변수
    private TimePickerDialog.OnTimeSetListener callbackMethodT;
    private Button start_time_B;
    private Button end_time_B;
    private int TimePickerCalled;
    private int[] startTime;
    private int[] endTime;
    //spinner
    private MultiSelectionSpinner food_spinner;
    private MultiSelectionSpinner theme_spinner;
    //예산 입력
    private EditText tour_budget;
    private EditText acc_budget;
    int tourBudget;
    int accBudget;
    //인원 설정
    private TextView pNum;
    int num=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //처음에 실행되는 함수
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_input);
        presenter=new InputPresenter();
        presenter.setView(this);
        presenter.clear();
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
        food_list.add("양식");
        food_list.add("일식");
        food_list.add("중식");
        food_list.add("아시아식");
        food_spinner.setItems(food_list);
        List<String> theme_list = new ArrayList<String>();
        theme_list.add("선택하세요");
        theme_list.add("자연관광지");
        theme_list.add("관광자원");
        theme_list.add("역사관광지");
        theme_list.add("휴양관광지");
        theme_list.add("체험관광지");
        theme_list.add("산업관광지");
        theme_list.add("건축/조형물");
        theme_list.add("문화시설");
        theme_list.add("축제");
        theme_list.add("공연/행사");
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
                if (DataPickerCalled == R.id.start_date_B){
                    String month;
                    String day;
                    if (monthOfYear+1<10)
                        month="0"+(monthOfYear+1);
                    else
                        month=(monthOfYear+1)+"";
                    if(dayOfMonth<10)
                        day="0"+dayOfMonth;
                    else
                        day=dayOfMonth+"";
                    startDate = year+"-"+month+"-"+day;
                    start_date_B.setText(startDate);
                    startD = new int[3];
                    startD[0] = year;
                    startD[1] = monthOfYear;
                    startD[2] = dayOfMonth;
                }

                else if (DataPickerCalled == R.id.end_date_B) {
                    String month;
                    String day;
                    if (monthOfYear+1<10)
                        month="0"+(monthOfYear+1);
                    else
                        month=(monthOfYear+1)+"";
                    if(dayOfMonth<10)
                        day="0"+dayOfMonth;
                    else
                        day=dayOfMonth+"";
                    endDate = year+"-"+month+"-"+day;
                    end_date_B.setText(endDate);
                    endD = new int[3];
                    endD[0] = year;
                    endD[1] = monthOfYear;
                    endD[2] = dayOfMonth;
                }
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
                    startTime=new int[2];
                    startTime[0]=hourOfDay;
                    startTime[1]=minute;
                    if (minute<10){ //분이 한자리수 이면 앞에 0을 붙여서 두 자리로 만들어서 출력
                        start_time_B.setText(hourOfDay+" : 0"+m);
                    }
                    else //분이 두자리면 그냥 출력
                        start_time_B.setText(hourOfDay+" : "+m);
                }
                else if (TimePickerCalled==R.id.end_time_B){
                    m=Integer.toString(minute);
                    endTime=new int[2];
                    endTime[0]=hourOfDay;
                    endTime[1]=minute;
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
        int month = today.get(today.MONTH);
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
    public void SubmitInput(View view){ //여행 코스 추천 버튼을 눌렀을 때(미완)
        AlertDialog.Builder alert = new AlertDialog.Builder(InputActivity.this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();     //닫기
            }
        });
        //예외처리
        try{
            for(int i=0;i<3;i++){
                if (startD[i]>endD[i]){
                    alert.setMessage("출발/도착 날짜를 확인해주세요");
                    alert.show();
                    return;
                }
            }
            if (startTime[0]>endTime[0] || startTime[1]>endTime[1]){
                alert.setMessage("활동 시작/종료시간을 확인해주세요");
                alert.show();
                return;
            }
            tourBudget = Integer.parseInt( "" + tour_budget.getText() );
            accBudget = Integer.parseInt( "" + acc_budget.getText() );

            List<Integer> food_selection = food_spinner.getSelectedIndicies();
            List<Integer> theme_selection = theme_spinner.getSelectedIndicies();
            presenter.makeTripSchedule("",startDate,endDate,num,tourBudget,accBudget,startTime,endTime,food_selection,theme_selection);
            Intent intent=new Intent(getApplicationContext(),SePosSetting.class);
            intent.putExtra("class", presenter.getTripSchedule());
            startActivity(intent);
        } catch (NumberFormatException e) {
            alert.setMessage("모두 입력해주세요");
            alert.show();
            //e.printStackTrace();
        } catch (NullPointerException e) {
            alert.setMessage("모두 입력해주세요");
            alert.show();
        }
    }
}
