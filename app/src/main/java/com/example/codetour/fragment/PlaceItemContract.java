package com.example.codetour.fragment;

import com.example.codetour.Spot;
import com.example.codetour.vo.Place;

import java.io.Serializable;
import java.util.List;

public interface PlaceItemContract {

    interface Presenter{

        List<Spot> loadRecommendPlace(Spot spot);

        void deletePlace(Spot place);
        void deletePlace(int i);
    }

    interface  View {

        void showRecommendPlace(List<Spot> placeList);

        void erasePlace(Spot place);
        void erasePlace(int i);
    }

}
