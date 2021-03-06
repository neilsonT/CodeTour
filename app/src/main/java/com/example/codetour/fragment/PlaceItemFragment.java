package com.example.codetour.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.codetour.R;
import com.example.codetour.RouteCheck;
import com.example.codetour.Spot;
import com.example.codetour.vo.Place;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 코스 상세 정보를 담고 있는 Class
public class PlaceItemFragment extends Fragment implements PlaceItemContract.View {

    private PlaceItemContract.Presenter placeItemPresenter ; // presenter

    private List<Spot> dataset;    // 장소들의 List
    private ListView listView;
    private PlaceItemViewAdapter placeItemViewAdapter;

//    private TextView placeAdd;  // 장소 추가 버튼
    private ImageButton placeDeleteButton;  // 장소 삭제 버튼

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_placeitem_list,container,false);

        placeItemPresenter = new PlaceItemPresenter(this);

        dataset = new ArrayList<Spot>();
        listView = rootView.findViewById(R.id.placeItemList);
        placeItemViewAdapter = new PlaceItemViewAdapter(dataset,getActivity().getApplicationContext());
        placeItemViewAdapter.setFragment(this);


        listView.setAdapter(placeItemViewAdapter);

        View v = inflater.inflate(R.layout.fragment_placeitem,container,false);
//        placeAdd = v.findViewById(R.id.placeAddButton);
        placeDeleteButton = v.findViewById(R.id.placeDeleteButton);

////         장소 추가 버튼 이벤트 핸들러
//        placeAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                placeItemPresenter.loadRecommendPlace(new Spot());
//            }
//        });
//
//        // 장소 삭제 버튼 이벤트 핸들러
//        placeDeleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dataset.remove(0);
//                Spot place = null;
//                placeItemPresenter.deletePlace(place);
//            }
//        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            // adapterView : 아이템이 포함된 부모 뷰: 리스트뷰
//            // view : 클릭된 아이템
//            // i : 선택된 항목의 번호 ( position )
//            // l : 일반적으로 position과 같은 개념 ( id )
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                dataset.remove(i);
//                placeItemPresenter.deletePlace(i);
//            }
//        });

        return rootView;
    }

    // 장소의 리스트를 보여주는 메소드
    // 변경된 장소가 매개변수로 들어오면 해당 placeList가 dataset에 추가된다
    public void showPlaceList(List<Serializable> placeList) {
        dataset.clear();
        Log.d("show","place List Size :"+placeList.size());
        for(Serializable s : placeList){
            dataset.add((Spot) s);
        }
        placeItemViewAdapter.notifyDataSetChanged();
    }

    // 추천장소를 화면에 표시하는 메서드
    public void showRecommendPlace(int i){
        List<Spot> spotList = placeItemPresenter.loadRecommendPlace(dataset.get(i));
        ((RouteCheck)getActivity()).showRecommendMarkers(spotList, i);
    }

    @Override
    public void showRecommendPlace(List<Spot> placeList) {

    }

    // 코스에 포함된 장소를 화면에서 삭제하는 메서드
    public void erasePlace(Spot place){

    }

    @Override
    public void erasePlace(int i) {
        ((RouteCheck)getActivity()).hideMarker(dataset.get(i));
        dataset.remove(i);
        placeItemViewAdapter.notifyDataSetChanged();
    }
}
