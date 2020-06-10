package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.codetour.TmapOverlay.MarkerOverlay;
import com.example.codetour.fragment.PlaceFragment;
import com.example.codetour.fragment.PlaceItemFragment;
import com.example.codetour.vo.Place;
import com.skt.Tmap.TMapInfo;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

// 경로 확인 페이지 클래스
public class RouteCheck extends AppCompatActivity implements  ScheduleContract.View{

    // presenter
    private ScheduleContract.Presenter schedulePresenter;

    // fragment
    private FragmentManager fm;
    private PlaceItemFragment placeItemFragment;
//    private PlaceFragment placeFragment;

    // 티맵 지도
    private TMapView tMapView;

    // 사용자가 입력한 조건에 의해 완성된 여행 일정
    private TripSchedule tripSchedule;
    private List<Serializable> spotList;

    private Recommend rec; //added by 대양; 위의 tripSchedule 외에 추천 Spot들의 리스트들을 같이 받기 위해 작성

    // 여행 날짜 리스트
    private List<String> dayList;

    // 장소, 경로 관련
    private List<TMapMarkerItem> locationList;
    private List<TMapMarkerItem> recommendedPlaceList;  // 장소 추가 할때 추천 장소 리스트
    private ArrayList<TMapPoint> tMapPointList; // 지도 위치를 조정하기 위한, TMapPoint의 위도 경도 집합
    private List<Course> courseList;
    // 마커 풍선
    private MarkerOverlay markerOverlay;

    // 마커 선택시 해당 장소 저장용도
    private Place selectedPlace;

    // 현재 날짜 번호( 1일차, 2일차 )
    private int day;

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
//        placeFragment = (PlaceFragment)fm.findFragmentById(R.id.placeFragment);
        fm.beginTransaction().addToBackStack(null);
        hideFragment(placeItemFragment);
//        hideFragment(placeFragment);

        // 완성된 여행 일정; edited by 대양; tripSchedule 클래스를 직접 받는 대신, recommend 클래스 안에 있는 것을 갖도록 함.
        Intent intent = getIntent();
        rec = (Recommend) intent.getSerializableExtra("rec");
        tripSchedule = rec.tripSchedule;
        //List<Spot> recommendSpotList = rec.recommendSpotList;

        // 여행 날짜 List 초기화
        dayList = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(tripSchedule.getStartDate(),"-");
        int startYear = Integer.parseInt(st.nextToken());
        int startMon = Integer.parseInt(st.nextToken());
        int startDay = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(tripSchedule.getEndDate(),"-");
        int endYear = Integer.parseInt(st.nextToken());
        int endMon = Integer.parseInt(st.nextToken());
        int endDay = Integer.parseInt(st.nextToken());

        Calendar startDate = new GregorianCalendar(startYear,startMon,startDay);
        Calendar endDate = new GregorianCalendar(endYear,endMon,endDay);

        long diff = (endDate.getTimeInMillis() - startDate.getTimeInMillis())/(1000*24*60*60);
        diff++;
        for(int i=0; i<diff; i++){
            dayList.add(i+1+"일차");
        }


        tripSchedule.getStartDate();

        // 여행 날짜 선택 spinnner
        Spinner daySpinner = findViewById(R.id.daySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        // 여행 날짜 선택 이벤트
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tMapView.removeAllMarkerItem();
                        tMapView.removeAllTMapPolyLine();
                    }
                });
                day = i;
                spotList = courseList.get(i).getSpotList();
//                hideMarkers(courseList.get(day).getSpotList());
                showTripSchedule(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tMapPointList = new ArrayList<>();
        courseList = tripSchedule.getCourseList();
        spotList = courseList.get(0).getSpotList();
//        showTripSchedule(0);
    }

    // 경로 보기 설명 버튼 누르면 작동. 경로의 정보가 설정되고 fragment가 표시된다
    public void openPlaceList(View view) {
        setPlaceDetail(courseList.get(day).getSpotList());
//        hideFragment(placeFragment);
        showFragment(placeItemFragment);
        Log.d("tag","message");
    }

    // fragment 숨기기
    public void hideFragment(final Fragment fragment){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fm.beginTransaction().hide(fragment).commit();
            }
        });
    }

    // fragment 보이기
    public void showFragment(final Fragment fragment){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fm.beginTransaction().show(fragment).commit();
            }
        });

    }

    public void showTripSchedule(int i){
        showMarkers( courseList.get(i).getSpotList());
    }

    // 경로의 정보가 fragment에 표시될수 있도록 세팅된다
    public void setPlaceDetail(List<Serializable> spotList){
        placeItemFragment.showPlaceList(spotList);
    }

    // 마커 하나 추가
    public void showMarker(Parcelable place){
        final TMapMarkerItem markerItem = new TMapMarkerItem();

        TMapPoint tMapPoint = new TMapPoint(37.570841, 126.985302);
        // 마커 아이콘
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.marker);

        markerItem.setIcon(bitmap);
        markerItem.setPosition(0.5f,1.0f);
        markerItem.setTMapPoint(tMapPoint);
        markerItem.setName("marker");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tMapView.addMarkerItem("markerItem",markerItem);
            }
        });


        locationList.add(markerItem);
    }

    // 마커 하나 삭제
    public void hideMarker(final Parcelable place){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tMapView.removeMarkerItem(((Place)place).getName());
            }
        });

    }

    public void showRecommendMarkers(List<Spot> spotList){
        // 마커 리스트 테스트용도
        recommendedPlaceList = new ArrayList<>();

        for(int i=0; i<spotList.size(); i++){
            final TMapMarkerItem markerItem = new TMapMarkerItem();
            final Spot spot  = (Spot)spotList.get(i);
            TMapPoint tMapPoint = new TMapPoint(spot.getPos()[1],spot.getPos()[0]);
            // 마커 아이콘
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.r_marker);

            markerItem.setIcon(bitmap);
            markerItem.setPosition(0.5f,1.0f);
            markerItem.setTMapPoint(tMapPoint);
            markerItem.setName(spot.getTitle());
            markerItem.setID("recommend");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tMapView.addMarkerItem(spot.getTitle(),markerItem);
                }
            });

            recommendedPlaceList.add(markerItem);
        }
    }

    // 마커 여러개 지도에 표시
    public void showMarkers(List<Serializable> placeList){
        // 마커 리스트 테스트용도
        tMapPointList.clear();
        locationList = new ArrayList<>();

        for(int i=0; i<placeList.size(); i++){
            final TMapMarkerItem markerItem = new TMapMarkerItem();
            final Spot spot  = (Spot)placeList.get(i);
            TMapPoint tMapPoint = new TMapPoint(spot.getPos()[1],spot.getPos()[0]);
            // 마커 아이콘
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.marker+i+1);

            markerItem.setIcon(bitmap);
            markerItem.setPosition(0.5f,1.0f);
            markerItem.setTMapPoint(tMapPoint);
            markerItem.setName(spot.getTitle());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tMapView.addMarkerItem(spot.getTitle(),markerItem);
                }
            });

            tMapPointList.add(tMapPoint);
            locationList.add(markerItem);
        }
        final TMapPolyLine tMapPolyLine = new TMapPolyLine();
        tMapPolyLine.setLineColor(Color.BLUE);
        tMapPolyLine.setLineWidth(1);
        for( int i=0; i<tMapPointList.size(); i++ ) {
            tMapPolyLine.addLinePoint( tMapPointList.get(i) );
        }


        // 지도 위치를 경로에 맞게 바꿔주기
        final TMapInfo tMapInfo = tMapView.getDisplayTMapInfo(tMapPointList);
        Log.d("mapLevel", String.valueOf(tMapInfo.getTMapZoomLevel()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int zoomlevel;
                if(tMapInfo.getTMapZoomLevel()<12){ // 줌 레벨이 클 수록 확대, 작을수록 축소
                    Log.d("mapLevel","12아래");
                    zoomlevel = 11;
                }else{
                    Log.d("mapLevel","12이상");
                    zoomlevel = tMapInfo.getTMapZoomLevel();
                }
                tMapView.addTMapPolyLine(String.valueOf(day), tMapPolyLine);
                tMapView.setZoomLevel(zoomlevel);
                tMapView.setCenterPoint(tMapInfo.getTMapPoint().getLongitude(),tMapInfo.getTMapPoint().getLatitude());
            }
        });

    }

    // 마커 여러개 삭제
    public void hideMarkers(List<Serializable> placeList) {

        for(int i=0; i<placeList.size(); i++){
            tMapView.removeMarkerItem(((Spot)placeList.get(i)).getTitle());
        }
    }


    public void showMarkerOverlay(final Serializable place, final TMapPoint tmapPoint){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Spot spot = (Spot) place;
                markerOverlay = new MarkerOverlay(getApplicationContext(), spot.getFirstImage2(),spot.getTitle() ,spot.getTel() , spot.getAddress());
                markerOverlay.setID("id");
                markerOverlay.setPosition(0.0f, 0.0f);
                markerOverlay.setTMapPoint(tmapPoint);
                Log.d("image",markerOverlay.getImageStatus());
                try {
                    Thread.sleep(300);
                    Log.d("image","sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("image","addMarker");
                tMapView.addMarkerItem2("id", markerOverlay);
            }
        });

    }

    public void hideMarkerOverlay(MarkerOverlay markerOverlay){
        tMapView.removeMarkerItem2(markerOverlay.getID());
    }

    // 일정 저장하기
    public void saveSchedule(View view) {
        // 만들어진 일정을 모델에 저장
        if(tripSchedule.save){
            ScheduleService.getInstance().tripScheduleList.get(tripSchedule.getList_pos());
        }
        else{
            tripSchedule.setList_pos(ScheduleService.getInstance().tripScheduleList.size());
            tripSchedule.setName("일정"+ScheduleService.getInstance().tripScheduleList.size());
            ScheduleService.getInstance().addSchedule(tripSchedule);
        }

        //SaveLoadManager.saveTripScheduleList(this, "scheduleList", ScheduleService.getInstance().tripScheduleList);

        Intent intent = new Intent(this,ScheduleList.class);
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
                for(Serializable a : spotList){
                    if(((Spot)a).getTitle().equals(arrayList.get(0).getName())){
                       showMarkerOverlay(a,tMapPoint);
                    }
                }
                schedulePresenter.setMarkerOverlay(arrayList.get(0).getName());
            }

            return false;
        }
    }
}
