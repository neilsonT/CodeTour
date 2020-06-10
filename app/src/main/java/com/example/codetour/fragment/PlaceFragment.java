package com.example.codetour.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.codetour.R;
import com.example.codetour.RouteCheck;
import com.example.codetour.Spot;

import java.io.Serializable;

public class PlaceFragment extends Fragment {

    TextView name;
    TextView address;
    TextView tel;
    Spot spot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("PlaceFragment","onCreateView");
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_place,container,false);

        name = rootView.findViewById(R.id.name);
        address = rootView.findViewById(R.id.address);
        tel = rootView.findViewById(R.id.telNo);

        return rootView;
    }

    public void changePlace(Serializable spot){
        if(spot instanceof Spot){
            this.spot = (Spot)spot;
            name.setText(this.spot.getTitle());
            Log.d("changePlace",this.spot.getAddress());
            address.setText(this.spot.getAddress());
            tel.setText(this.spot.getTel());
        }
    }


    @Override
    public void onStart() {
        Log.d("PlaceFragment","onStart");
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        Log.d("PlaceFrament","onStart");
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        Log.d("PlaceFragment","onResue");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("PlaceFragment","onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("PlaceFragment","onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d("PlaceFragment","onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.d("PlaceFragment","onDetach");
        super.onDetach();
    }
}
