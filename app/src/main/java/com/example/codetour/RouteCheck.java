package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.codetour.TmapOverlay.MarkerOverlay;
import com.example.codetour.fragment.PlaceItemFragment;
import com.example.codetour.vo.Place;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.List;

// 경로 확인 페이지 클래스
public class RouteCheck extends AppCompatActivity implements  ScheduleContract.View{

    // presenter
    private ScheduleContract.Presenter schedulePresenter;

    private FragmentManager fm;
    private PlaceItemFragment placeItemFragment;

    // 장소 리스트
    private List<Parcelable> placeList;

    // 여행 날짜 리스트
    private List<String> dayList;

    // 장소의 마커 리스트
    private List<TMapMarkerItem> locationList;
    private List<TMapMarkerItem> recommendedPlaceList;

    // 티맵 지도
    private TMapView tMapView;

    // 마커 풍선
    private MarkerOverlay markerOverlay;

    // 마커 선택시 해당 장소 저장용도
    private Place selectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_check);

        // presenter 생성
        schedulePresenter = new SchedulePresenter(this);

        // 지도 띄우기
        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(getString(R.string.tmap_key));
        linearLayoutTmap.addView( tMapView );

        // 지도에 이벤트 핸들러 설정
        tMapView.setOnClickListenerCallBack(new MapClickListener());

        // 경로 정보에 대한 Fragment 관리
        fm = getSupportFragmentManager();
        placeItemFragment = (PlaceItemFragment)fm.findFragmentById(R.id.placeItemFragment);
        fm.beginTransaction().addToBackStack(null);
        hideFragment(placeItemFragment);

        // 여행 날짜 List 초기화
        dayList = new ArrayList<>();

        // 여행 날짜 선택 spinnner
        Spinner daySpinner = findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        // 여행 날짜 선택 이벤트
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 장소 리스트 테스트용도
        placeList = new ArrayList<>();
        for(int i=0; i<10; i++){
            placeList.add(new Place("시립대"+i));
        }

        // 이전 페이지에서 넘겨준 정보 받기
        Intent intent = new Intent();

    }

    // 경로 보기 설명 버튼 누르면 작동. 경로의 정보가 설정되고 fragment가 표시된다
    public void openPlaceList(View view) {
        setPlaceDetail(placeList);
        showFragment(placeItemFragment);
    }

    // fragment 숨기기
    public void hideFragment(Fragment fragment){
        fm.beginTransaction().hide(fragment).commit();
    }

    // fragment 보이기
    public void showFragment(Fragment fragment){
        fm.beginTransaction().show(fragment).commit();
    }



    // 경로의 정보가 fragment에 표시될수 있도록 세팅된다
    public void setPlaceDetail(List<Parcelable> placeList){
        placeItemFragment.showPlaceList(placeList);
    }

    // 마커 지도에 표시
    public void showMarker(List<Parcelable> placeList){
        // 마커 리스트 테스트용도
        locationList = new ArrayList<>();
        for(int i=0; i<10; i++){
            TMapMarkerItem markerItem = new TMapMarkerItem();

            TMapPoint tMapPoint = new TMapPoint(37.570841+0.001*i, 126.985302-0.001*i);
            // 마커 아이콘
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.marker);

            markerItem.setIcon(bitmap);
            markerItem.setPosition(0.5f,1.0f);
            markerItem.setTMapPoint(tMapPoint);
            markerItem.setName("marker");
            tMapView.addMarkerItem("markerItem"+i,markerItem);

            locationList.add(markerItem);
        }
        tMapView.setCenterPoint(126.985302,37.570841);
}

    public void hideMarker(List<Parcelable> placeList) {

        // 삭제 테스트
        for(int i=0; i<locationList.size(); i++){
            tMapView.removeMarkerItem(locationList.get(i).getID());
        }
    }

    public void showMarkerOverlay(TMapPoint tMapPoint){
        Bitmap image = null;
        markerOverlay = new MarkerOverlay(getApplicationContext(), "https://www.dhnews.co.kr/news/photo/201807/83555_72577_634.png", "서울시립대", "123-123-123", "서울시 동대문구");
        tMapView.addMarkerItem2("id", markerOverlay);
        markerOverlay.setID("id");
        markerOverlay.setPosition(0.0f, 0.0f);
        markerOverlay.setTMapPoint(tMapPoint);
    }

    public void hideMarkerOverlay(MarkerOverlay markerOverlay){
        tMapView.removeMarkerItem2(markerOverlay.getID());
    }

    // 일정 저장하기
    public void saveSchedule(View view) {
        Intent intent = new Intent(this,ScheduleList.class);
        // intent에 저장할 일정 데이터 저장해서 다음 페이지로 보내기
        startActivityForResult(intent,0);
    }

    // 일정 취소하기
    public void cancel(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivityForResult(intent,0);
    }


    // 지도 클릭 이벤트 핸들러
    public class MapClickListener implements TMapView.OnClickListenerCallback {

        @Override
        public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
            Log.d("onPressEvent","지도 클릭");
            return false;
        }

        @Override
        public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
            Log.d("onPressUpEvent","지도 클릭");
            // 지도 선택시 장소 경로 숨기기
            hideFragment(placeItemFragment);

            // 말풍선 지우기
            if(markerOverlay != null) {
               hideMarkerOverlay(markerOverlay);
            }

            // 장소를 선택한게 아니면 아무일도 없다
            if(arrayList.size() == 0) {
                selectedPlace = null;
                return false;
            }

            // 마커를 선택했다는 조건 추가해야됨
            if(true) {
                showMarkerOverlay(tMapPoint);
            }

            return false;
        }
    }
}
