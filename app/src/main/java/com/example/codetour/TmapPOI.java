package com.example.codetour;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TmapPOI  {

    private ArrayList<PosItem> mListData;
    private RecyclerViewAdapter mAdapter;

    public TmapPOI(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
        mListData = new ArrayList<PosItem>();
    }

    public void getAutoComplete(String word) throws InterruptedException {

        TMapData tmapdata= new TMapData();
        System.out.println("왜안돼");
        tmapdata.findAllPOI(word, new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(ArrayList poiItem) {
                mListData.clear();
                if (poiItem.size()==0){
                    mListData.add(new PosItem("결과가 없습니다",null,null));
                }
                //반환되는 검색결과는 최대 20개이다
                //각각의 장소를 객체에 담아서 list로 묶어주는 반복문
                for(int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem item = (TMapPOIItem) poiItem.get(i);
                    //이 주석은 검색결과 잘 받아오는지 로그로 확인해보는 거라 지금은 안봐도 됨
                    /*Log.d("POI Name: ", item.getPOIName().toString() + ", " +
                            "Address: " + item.getPOIAddress().replace("null", "")  + ", " +
                            "Point: " + item.getPOIPoint().toString());*/
                    mListData.add(new PosItem(item.getPOIName().toString(),item.getPOIAddress().replace("null","").toString(),item.getPOIPoint()));
                }
            }
        });
        //여기 sleep 안걸면 위에 for문 끝나기 전에 setData해줘서 빈 리스트가 넘어가더라. 400ms는 그냥 실험적으로 정한 값임!
        TimeUnit.MILLISECONDS.sleep(400);

        //adapter에 검색결과 달아주고, 데이터 갱신되었다고 알려주기.
        mAdapter.setData(mListData);
        mAdapter.notifyDataSetChanged();

    }
}

