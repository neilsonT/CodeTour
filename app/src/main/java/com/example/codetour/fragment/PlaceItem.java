package com.example.codetour.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.codetour.R;
import com.example.codetour.fragment.dummy.DummyContent;
import com.example.codetour.fragment.dummy.DummyContent.DummyItem;
import com.example.codetour.vo.Place;

import java.util.ArrayList;
import java.util.List;


public class PlaceItem extends Fragment {

    private List<Place> dataset;
    private ListView listView;
    private ArrayAdapter<Place> adapter;
    private PlaceItemViewAdapter placeItemViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_placeitem_list,container,false);

        dataset = new ArrayList<Place>();
        listView = rootView.findViewById(R.id.placeItemList);
        placeItemViewAdapter = new PlaceItemViewAdapter(dataset,getActivity().getApplicationContext());
        listView.setAdapter(placeItemViewAdapter);


        return rootView;
    }

    public void showPlaceList(List<Parcelable> placeList) {
        for(Parcelable p : placeList){
            dataset.add((Place) p);
        }
    }
}
