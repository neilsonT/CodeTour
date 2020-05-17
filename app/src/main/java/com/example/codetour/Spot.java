package com.example.codetour;

import org.json.JSONObject;

import java.io.Serializable;

public class Spot implements Serializable {
    double[] pos= new double[2];
    String title;//관광지 이름
    String tel;  //전화번호
    String contentTypeId;   //설명 받아오기 위해 필요합니다
    String contentid;       //설명 받아오기 위해 필요합니다
    //이미지
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

            //openTime = new int[ ?? ];
            //closedTime = new int[ ?? ];
        }
        catch(Exception e){}
    }
    public JSONObject toJSONObj(){
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
        }
        catch(Exception e){}
        return ret;
    }
}

