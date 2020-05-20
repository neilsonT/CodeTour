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
        void setPlaceDetail(List<Parcelable> placeList);
        void showMarker(Parcelable place);
        void hideMarker(Parcelable place);
        void showMarkers(List<Spot> placeList);
        void hideMarkers(List<Spot> placeList);
        void showMarkerOverlay(TMapPoint tMapPoint);
        void hideMarkerOverlay(MarkerOverlay markerOverlay);
    }

    interface Presenter {
        void saveSchedule(TripSchedule tripSchedule);
    }
}
