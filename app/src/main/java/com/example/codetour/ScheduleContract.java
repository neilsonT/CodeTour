package com.example.codetour;

import androidx.fragment.app.Fragment;

import com.example.codetour.TmapOverlay.MarkerOverlay;
import com.example.codetour.vo.Place;
import com.skt.Tmap.TMapPoint;

import java.util.List;

public interface ScheduleContract {
    interface View{
        void showFragment(Fragment fragment);
        void hideFragment(Fragment fragment);
        void setPlaceDetail(List<Place> placeList);
        void showMarker();
        void hideMarker();
        void showMarkerOverlay(TMapPoint tMapPoint);
        void hideMarkerOverlay(MarkerOverlay markerOverlay);
    }

    interface Presenter {

    }
}
