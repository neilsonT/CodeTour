package com.example.codetour.fragment;

import com.example.codetour.Recommend;
import com.example.codetour.Spot;
import com.example.codetour.vo.Place;

import java.io.Serializable;
import java.util.List;

public class PlaceItemPresenter implements PlaceItemContract.Presenter {

    private PlaceItemContract.View placeItemView;

    public PlaceItemPresenter(PlaceItemContract.View view){
        this.placeItemView = view;
    }

    @Override
    public List<Spot> loadRecommendPlace(Spot spot) {
        // 서비스로 부터 장소 추가 받아오기 메서드
        return new Recommend().recommendSpot(spot);
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
