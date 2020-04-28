package com.example.codetour;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.net.URL;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


public class SpotManager {
    final private String key_DY = "YuGhNUdswtkAVKVlwFiC%2BgZF%2F1qCuhiKIln3Q1UTlY31jCR987Kfcg8Ci82dTLTfZAzHTgkiQPSJ1qEXOZFoVw%3D%3D";
    final String endPoint = "http://api.visitkorea.or.kr/openapi/service/rest";
    String lang = "/KorService";
    String searchType = "/areaBasedList";
    final String OSandAPPname = "&MobileOS=ETC&MobileApp=codetour";
    private String serviceKey;

    private XmlPullParserFactory parserCreator;
    private XmlPullParser parser;

    List<Spot> SpotList;

    public SpotManager(){
        try{
            serviceKey = URLEncoder.encode(key_DY, "UTF-8");
            parserCreator = XmlPullParserFactory.newInstance();
            parser = parserCreator.newPullParser();
        }
        catch(Exception e){System.out.println("Initializing error;");}
    }

    List<Spot> spotList;
    public void ToAPI(){
        try {
            parser.setInput(new URL(
                endPoint+lang+searchType
                +"?ServiceKey="+serviceKey
                +"&listYN="+"Y"
                +"&arrange="+"B"
                +"&areaCode="+"35" //35는 경북의 지역코드
                /* TODO: 지역코드 관리 필요 및 다른 조건 추가 필요 */
                +OSandAPPname
            ).openStream(), null);
        }
        catch(Exception e){System.out.println("URL error;");}
    }
    public void ProcessData(){
        /* TODO: 플래그들을 어떻게 관리할 지(정적/동적), 빼거나 넣을 것 정하기 */
        boolean f_title = false, f_addr1 = false, f_areacode = false, f_cat1 = false, f_firstimage = false,
            f_mapx = false, f_mapy = false, f_tel = false, f_zipcode = false;
        String title, addr1, areacode, cat1, firstimage, mapx, mapy, tel, zipcode;
        try {
            for(int parserEvent = parser.getEventType(); parserEvent != XmlPullParser.END_DOCUMENT; parserEvent = parser.next()){
                switch(parserEvent){
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();

                        /* TODO: 어떤 종류의 태그인지 플래그 활성화 */
                        if(tagName.equals("title")) f_title = true;
                        else if(tagName.equals("addr1")) f_addr1 = true;
                        else if(tagName.equals("areacode")) f_areacode = true;
                        else if(tagName.equals("cat1")) f_cat1 = true;
                        else if(tagName.equals("firstimage")) f_firstimage = true;
                        else if(tagName.equals("mapx")) f_mapx = true;
                        else if(tagName.equals("mapy")) f_mapy = true;
                        else if(tagName.equals("tel")) f_tel = true;
                        else if(tagName.equals("zipcode")) f_zipcode = true;

                        break;

                    case XmlPullParser.TEXT:
                        /* TODO: 위에서 활성화한 플래그에 해당하는 값 가져오기*/
                        if(f_title){
                            title = parser.getText();
                            f_title = false;
                        }
                        else if(f_addr1){
                            addr1 = parser.getText();
                            f_addr1 = false;
                        }
                        else if(f_areacode){
                            areacode = parser.getText();
                            f_areacode = false;
                        }
                        else if(f_cat1){
                            cat1 = parser.getText();
                            f_cat1 = false;
                        }
                        else if(f_firstimage){
                            firstimage = parser.getText();
                            f_firstimage = false;
                            /* TODO: firstimage는 그림에 해당하는 URL 주소를 담고 있음. 어디서 처리할 지? */
                        }
                        else if(f_mapx){
                            mapx = parser.getText();
                            f_mapx = false;
                        }
                        else if(f_mapy){
                            mapy = parser.getText();
                            f_mapy = false;
                        }
                        else if(f_tel){
                            tel = parser.getText();
                            f_tel = false;
                        }
                        else if(f_zipcode){
                            zipcode = parser.getText();
                            f_zipcode = false;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")) {
                            /* TODO: 위에서 받아온 데이터를 하나의 장소 객체로 만들기 */
                        }
                        break;
                }
            }
        }
        catch(Exception e){System.out.println("Parsing error;");}
    }
    public void RecommendCourse(){

    }
    public void RecommendSpot(){

    }
    public void DeleteData(){

    }
}
