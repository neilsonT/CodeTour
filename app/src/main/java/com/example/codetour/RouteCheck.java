package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.codetour.fragment.PlaceItemFragment;
import com.example.codetour.vo.Place;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;
import java.util.List;

// 경로 확인 페이지 클래스
public class RouteCheck extends AppCompatActivity {

    private FragmentManager fm;
    private PlaceItemFragment placeListFragment;

    private List<Parcelable> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_check);

        // 지도 띄우기
        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        TMapView tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(getString(R.string.tmap_key));
        linearLayoutTmap.addView( tMapView );

        // 지도에 이벤트 핸들러 설정
        tMapView.setOnClickListenerCallBack(new MapClickListener());

        // 경로 정보에 대한 Fragment 관리
        fm = getSupportFragmentManager();
        placeListFragment = (PlaceItemFragment)fm.findFragmentById(R.id.placeItemFragment);
        fm.beginTransaction().addToBackStack(null);
        hideFragment(placeListFragment);



        // 장소 리스트 테스트용도
        placeList = new ArrayList<>();
        for(int i=0; i<10; i++){
            placeList.add(new Place("시립대"+i));
        }
    }

    // fragment 숨기기
    public void hideFragment(Fragment fragment){
        fm.beginTransaction().hide(fragment).commit();
    }

    // fragment 보이기
    public void showFragment(Fragment fragment){
        fm.beginTransaction().show(fragment).commit();
    }

    // 장소 상세 설명 버튼 누르면 작동
    public void openPlaceList(View view) {
        setPlaceDetail();
        showFragment(placeListFragment);
    }

    // 장소 상세 정보 설정하기
    public void setPlaceDetail(){
        placeListFragment.showPlaceList(placeList);
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
            hideFragment(placeListFragment);
            return false;
        }
    }
}
