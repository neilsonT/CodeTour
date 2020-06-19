package com.example.codetour;

import android.os.AsyncTask;
import android.util.Log;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TmapPOI extends AsyncTask<String, Void, ArrayList<PosItem>> {

    private ArrayList<PosItem> mListData;
    private RecyclerViewAdapter mAdapter;

    public TmapPOI(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
        mListData = new ArrayList<>();
    }

    @Override
    protected ArrayList<PosItem> doInBackground(String... strings) {
        try{
            TMapData tmapdata= new TMapData();
            ArrayList posItem = tmapdata.findAllPOI(strings[0]);
            for(int i = 0; i < posItem.size(); i++) {
                TMapPOIItem item = (TMapPOIItem) posItem.get(i);
                //이 주석은 검색결과 잘 받아오는지 로그로 확인해보는 거라 지금은 안봐도 됨
                Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                        "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                        "Point: " + item.getPOIPoint().toString()+"content"+item.getPOIContent());

                mListData.add(new PosItem(item.getPOIName().toString(),item.getPOIAddress().replace("null","").toString(),item.getPOIPoint()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //adapter에 검색결과 달아주고, 데이터 갱신되었다고 알려주기.
        return mListData;
    }

}

