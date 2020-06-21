package com.example.codetour;

import android.util.Log;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class SpotManager implements Serializable {
    List<Spot> spotList;
    Spot[] seSpot; //각 날마다의 출발지, 도착지

    public SpotManager(){
        spotList = new ArrayList<Spot>();
        seSpot = new Spot[2];
        for(int i=0; i<2; ++i) seSpot[i] = new Spot();
    }

    public void addSpotAt(int idx, Spot spot){
        spotList.add(idx, spot);
    }

    //생성자는 load를 위해, toJSONObj는 save를 위해 사용.
    public SpotManager(JSONObject obj){
//System.out.println("            Loading SpotManager...");
        try{
            spotList = new ArrayList<Spot>();
            seSpot = new Spot[2];

            //seSpot[0] = new Spot(obj.getJSONObject("stSpot"));
            //seSpot[1] = new Spot(obj.getJSONObject("edSpot"));

            JSONArray spotListTemp = obj.getJSONArray("spotList");

            for(int i=0, l=spotListTemp.length(); i<l; ++i){
                spotList.add(new Spot(spotListTemp.getJSONObject(i)));
            }
//System.out.println("            Complete");
        }
        catch(Exception e){ System.out.println("Error at Loading SpotManager"); }
    }
    public JSONObject toJSONObj(){
//System.out.print("            Saving SpotManager...(");
//System.out.print(spotList.size());
//System.out.println(")");
        JSONObject ret = new JSONObject();
        JSONArray spotListTemp = new JSONArray();
        try{
            ret.put("stSpot", seSpot[0]);
            ret.put("edSpot", seSpot[1]);

            for(int i=0, l=spotList.size(); i<l; ++i){
                spotListTemp.put(spotList.get(i).toJSONObj());
            }

            ret.put("spotList", spotListTemp);
//System.out.println("            Complete");
        }
        catch(Exception e){ System.out.println("Error at Saving SpotManager"); }

        return ret;
    }

    public void setSpotList(List<Spot> spotList){this.spotList=spotList;}
    public List<Spot> getSpotList() {
        return spotList;
    }

    public void replaceList(List<Serializable> list){
        spotList.clear();
        for(Serializable s : list){
            spotList.add((Spot)s);
        }
    }
}
