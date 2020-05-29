package com.example.codetour.fragment;

import com.example.codetour.Spot;
import com.example.codetour.vo.Place;

import java.util.List;

public class PlaceItemPresenter implements PlaceItemContract.Presenter {

    private PlaceItemContract.View placeItemView;

    public PlaceItemPresenter(PlaceItemContract.View view){
        this.placeItemView = view;
    }

    @Override
    public void loadRecommendPlace() {
        List<Spot> placeList = null ;
        // 서비스로 부터 장소 추가 받아오기 메서드
        placeItemView.showRecommendPlace(placeList);
    }

    @Override
    public void deletePlace(int i) {
        placeItemView.erasePlace(i);
    }

    @Override
    public void deletePlace(Spot place) {
        placeItemView.erasePlace(place);
    }
}
