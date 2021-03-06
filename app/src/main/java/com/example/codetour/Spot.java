package com.example.codetour;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class Spot implements Serializable {
    double[] pos= new double[2];
    String title;//관광지 이름
    String tel;  //전화번호
    String contentTypeId;   //설명 받아오기 위해 필요합니다
    String contentid;       //설명 받아오기 위해 필요합니다
    String firstImage2;     //이미지
    String explain;//설명
    String address;//주소
    boolean RorT; // 식당인지 관광지인지
    //카테고리

    public Spot(){
        pos[0]=0;
        pos[1]=0;
        title="";
        tel="";
        contentTypeId="";
        contentid="";
        explain="";
        address="";
        RorT=false;
        firstImage2 ="";

    }
    //임의 생성자는 field값을 pos만 채우는 생성자를 만들엉서 해결하면 될듯

    public String getAddr1(){
        return address;
    }
    public String getTitle(){return title; }
    //public String getFirstimage(){return firstimage;}
    public double[] getPos(){return pos;}
    public String getTel(){ return tel; }
    public String getContentTypeId(){
        return contentTypeId;
    }
    public String getContentid(){
        return contentid;
    }
    public String getExplain(){return explain;}

    public void setAddr1(Object address){
        this.address=address.toString();
    }
    public void setTitle(Object title){
        this.title=title.toString();
    }
    public void setExplain(Object explain){this.explain=explain.toString();}

    public String getFirstImage2() {
        return firstImage2;
    }

    public void setFirstImage2(String firstImage2) {
        this.firstImage2 = firstImage2;
    }

    //public void setFirstimage(Object firstimage){this.firstimage=firstimage.toString();}

    public void setPos(Object mapx,Object mapy){
        if(mapx instanceof String)
            this.pos[0]=Double.parseDouble((String)mapx);
        else
            this.pos[0]=Double.parseDouble(mapx.toString());
        if(mapy instanceof String)
            this.pos[1]=Double.parseDouble((String)mapy);
        else
            this.pos[1]=Double.parseDouble(mapy.toString());
    }

    public void setTel(Object tel){
        this.tel=tel.toString();
    }
    public void setContentTypeId(Object contentTypeId){
        this.contentTypeId=contentTypeId.toString();
    }
    public void setContentid(Object contentid){
        this.contentid=contentid.toString();
    }

    //생성자는 load를 위해, toJSONObj는 save를 위해 사용.
    public Spot(JSONObject obj){
//System.out.print("                Loading Spot...");
        try {
            pos = new double[2];
            pos[0] = obj.getDouble("posX");
            pos[1] = obj.getDouble("posY");

            title = obj.getString("title");
            tel = obj.getString("tel");
            contentTypeId = obj.getString("contentTypeId");
            contentid = obj.getString("contentid");
            explain = obj.getString("explain");
            address = obj.getString("address");
            RorT = obj.getBoolean("RorT");
            firstImage2 = obj.getString("firstImage2");
            //openTime = new int[ ?? ];
            //closedTime = new int[ ?? ];
//System.out.println("Complete");
        }
        catch(Exception e){ System.out.println("Error at Loading Spot"); }
    }
    public JSONObject toJSONObj(){
//System.out.print("                Saving Spot...");
        JSONObject ret = new JSONObject();
        try{
            ret.put("posX", pos[0]);
            ret.put("posY", pos[1]);
            ret.put("title", title);
            ret.put("tel", tel);
            ret.put("contentTypeId", contentTypeId);
            ret.put("contentid", contentid);
            ret.put("explain", explain);
            ret.put("address", address);
            ret.put("RorT", RorT);
            ret.put("firstImage2",firstImage2);
//System.out.println("Complete");
        }
        catch(Exception e){ System.out.println("Error at Saving Spot"); }
        return ret;
    }

    public void setPos(double[] pos) {
        this.pos = pos;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setContentTypeId(String contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRorT() {
        return RorT;
    }

    public void setRorT(boolean rorT) {
        RorT = rorT;
    }
}

