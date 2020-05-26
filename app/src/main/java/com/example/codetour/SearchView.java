package com.example.codetour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.skt.Tmap.TMapView;

public class SearchView extends AppCompatActivity {
    private EditText form;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable workRunnable;
    private final long DELAY = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        TMapView tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("l7xx4620fe9b1c2445dda41257f430567fb2"); //바다 : 제가 발급받은 key 입니다

        layoutInit();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        /*
        //이 주석은 tmapAPI sample 기본 코드 , 굳이 보실 필요 없어요 !
        //LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        TMapView tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("l7xx4620fe9b1c2445dda41257f430567fb2");
        //linearLayoutTmap.addView( tMapView );


        String strData="노량진역";
        TMapData tmapdata = new TMapData();
        System.out.println("또이러네");
        tmapdata.findAllPOI("노량진역", new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(ArrayList poiItem) {
                for(int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem item = (TMapPOIItem) poiItem.get(i);
                    Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                            "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                            "Point: " + item.getPOIPoint().toString());
                }
            }
        });*/
    }


    private void layoutInit() {
        form=(EditText)findViewById(R.id.edt_search);
        recyclerView = (RecyclerView)findViewById(R.id.rl_listview);
        form.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //edittext에 글자가 입력되면 실행되는 함수
                final String keyword = s.toString();

                handler.removeCallbacks(workRunnable);
                workRunnable = new Runnable() {
                    @Override
                    public void run() {
                        //RecyclerViewAdapter.filter 함수를 실행합니다.
                        adapter.filter(keyword);
                    }
                };
                handler.postDelayed(workRunnable, DELAY);
            }
        });

        //RecyclerView를 Linear형태로 보여줄 수 있게 매니저 설정 및 초기 설정들,,
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

}

