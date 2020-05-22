package com.example.codetour;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    SpotManager spotManager;
    int order;
    int[] timeList; //소요시간 list
    //List<Spot> spotList; // TODO: spotList를 Course에서 빼고 SpotManager 안으로 옮기므로, (특히 저장/로드) 관련 메소드들을 추가/수정/삭제 할 필요 있음
    int[] startTime;
    int[] endTime;
    int[] startPos; //xy 좌표 형태로 받을 것을 예상
    int[] endPos;
    int countSpot;

    public Course(){
        spotManager = new SpotManager();
    }
    public Course(int num, int[] startTime, int[] endTime){
        this.order=num;
        this.startTime=startTime;
        this.endTime=endTime;
        spotManager = new SpotManager();
    }

    public void addSpotAt(int sIdx, Spot spot) {
        spotManager.addSpotAt(sIdx, spot);
    }
    public void deleteSpot(){

    }
    public void changeSpot(){

    }

    //생성자는 load를 위해, toJSONObj는 save를 위해 사용.
    public Course(JSONObject obj){
        try{
            spotManager = new SpotManager(obj.getJSONObject("spotManager"));
            order = obj.getInt("order");
            countSpot = obj.getInt("countSpot");

            // 대양: spotList가 Course 안에도 하나 있고 SpotManager 안에도 하나 있던데, 이 리스트를 어디서 관리할 지 확실하게 해야할 듯?
            //      애초에 spot 관련한 모든 멤버들을 굳이 Course에서 관리할 필요가 있는지는 살짝 의문임.

            //spotList = new ArrayList<Spot>();
            timeList = new int[countSpot];
            JSONArray timeListTemp = obj.getJSONArray("timeList");
            JSONArray spotListTemp = obj.getJSONArray("spotList");
            for(int i=0; i<countSpot; ++i){
                timeList[i] = timeListTemp.getInt(i);
                //spotList.add(new Spot(spotListTemp.getJSONObject(i)));
            }

            startPos = new int[2];
            startPos[0] = obj.getInt("sPosX");
            startPos[1] = obj.getInt("sPosY");

            endPos = new int[2];
            endPos[0] = obj.getInt("ePosX");
            endPos[1] = obj.getInt("ePosY");

            //startTime = new int[ ?? ];
            //endTime = new int[ ?? ];
        }
        catch(Exception e){}
    }
    public JSONObject toJSONObj(){
        JSONObject ret = new JSONObject();
        JSONArray spotListTmp = new JSONArray();
        JSONArray timeListTmp = new JSONArray();
        try{
            ret.put("spotManager", spotManager.toJSONObj());
            ret.put("order", order);
            ret.put("countSpot", countSpot);
            ret.put("sPosX", startPos[0]);
            ret.put("sPosY", startPos[1]);
            ret.put("ePosX", endPos[0]);
            ret.put("ePosY", endPos[1]);

            for(int i=0; i<countSpot; ++i){
                //spotListTmp.put(spotList.get(i).toJSONObj());
                timeListTmp.put(timeList[i]);
                //startTime ??
                //endTime ??
            }
            ret.put("spotList", spotListTmp);
            ret.put("timeList", timeListTmp);
        }
        catch(Exception e){}
        return ret;
    }
    public List<Serializable> getSpotList(){
        List<Serializable> s = new ArrayList<>();
        //s.addAll(spotList);
        return s;
    }
}
