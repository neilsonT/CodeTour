package com.example.codetour;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.URL;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class SpotManager implements Serializable {
    List<Spot> spotList;

    public SpotManager(){
        spotList = new ArrayList<Spot>();
    }

    public void addSpotAt(int idx, Spot spot){
        spotList.add(idx, spot);
    }

    //생성자는 load를 위해, toJSONObj는 save를 위해 사용.
    public SpotManager(JSONObject obj){

    }
    public JSONObject toJSONObj(){
        JSONObject ret = new JSONObject();
        try{

        }
        catch(Exception e){}
        return ret;
    }
}
