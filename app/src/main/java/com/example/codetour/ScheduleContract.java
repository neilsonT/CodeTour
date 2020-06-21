package com.example.codetour;

import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.example.codetour.TmapOverlay.MarkerOverlay;
import com.example.codetour.vo.Place;
import com.skt.Tmap.TMapPoint;

import java.io.Serializable;
import java.util.List;

public interface ScheduleContract {
    interface View{
        void showFragment(Fragment fragment);
        void hideFragment(Fragment fragment);
        void setPlaceDetail(List<Serializable> placeList);
        void showMarker(Serializable place);
        void hideMarker(Serializable place);
        void showMarkers(List<Serializable> placeList);
        void hideMarkers(List<Serializable> placeList);
        void showMarkerOverlay(Serializable place, TMapPoint tMapPoint,int i);
        void hideMarkerOverlay(MarkerOverlay markerOverlay);
    }

    interface Presenter {
        void saveSchedule(TripSchedule tripSchedule);

        void setMarkerOverlay(String name);
    }
}
