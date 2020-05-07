package com.example.codetour.fragment;

import com.example.codetour.vo.Place;

import java.util.List;

public interface PlaceItemContract {

    interface Presenter{

        void loadRecommendPlace();

        void deletePlace(Place place);
    }

    interface  View {

        void showRecommendPlace(List<Place> placeList);

        void erasePlace(Place place);
    }

}
