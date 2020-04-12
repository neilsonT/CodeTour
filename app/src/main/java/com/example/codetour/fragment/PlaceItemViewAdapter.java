package com.example.codetour.fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.codetour.R;
import com.example.codetour.vo.Place;

import java.util.List;

public class PlaceItemViewAdapter extends BaseAdapter {

    private List<Place> placeList;
    private Context context;
    private LayoutInflater layoutInflater;

    public PlaceItemViewAdapter() {}

    public PlaceItemViewAdapter(List<Place> placeList, Context context){
        this.context = context;
        this.placeList = placeList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return placeList.size();
    }

    @Override
    public Object getItem(int i) {
        return placeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemLayout = view;
        if(itemLayout == null){
            itemLayout = layoutInflater.inflate(R.layout.fragment_placeitem,viewGroup, false);
        }

        TextView textView = itemLayout.findViewById(R.id.placeName);

        textView.setText(placeList.get(i).getAddress());

        return itemLayout;
    }
}
