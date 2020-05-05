package com.example.codetour.fragment;

public class PlaceItemPresenter implements PlaceItemContract.Presenter {

    private PlaceItemContract.View placeItemView;

    public PlaceItemPresenter(PlaceItemContract.View view){
        this.placeItemView = view;
    }
}
