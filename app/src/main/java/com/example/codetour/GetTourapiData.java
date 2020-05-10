package com.example.codetour;

import android.os.AsyncTask;
import android.os.Bundle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class GetTourapiData{

    ArrayList<Spot> arrtmp=new ArrayList<Spot>();
    private String ServiceKey="7MuLFocxJtu7I1bf8JWQrKsLl%2FAEwkKk1U0nGGDRHkCSX1%2Bxp7mB38%2FRIjMwrB%2F6x8rRBQ3Le2dUuVFHBhBbtQ%3D%3D";

    //입력에서 받아와야하는 데이터들
    String contentTypeId="12";//컨텐츠아이디
    String areaCode="14";   //지역코드
    String sigunguCode="14";//시군구코드
    String cat1=""; //대분류
    String cat2=""; //중분류
    String startDate="20200101";//여행시작날 축제의경우에만 사용
    String endDate="20200131";  //여행마지막날 축제의경우에만 사용


    public GetTourapiData(){
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    protected  String getUrl(int num){ //URL가져오기
        String urlstr="";

        if(contentTypeId=="15") {   //축제의 경우
            urlstr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?"
                    + "ServiceKey=" + ServiceKey
                    +"&arrange=B"   //정렬기준(조회순)
                    //+"&areaCode=" +areaCode
                    //+"&sigunguCode="+sigunguCode
                    +"&eventStartDate="+startDate
                    +"&eventEndDate="+endDate
                    +"&numOfRows=5" +
                    "&MobileOS=AND&MobileApp=TestParsing&_type=json";
        }
        else{
            urlstr = //"http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=7MuLFocxJtu7I1bf8JWQrKsLl%2FAEwkKk1U0nGGDRHkCSX1%2Bxp7mB38%2FRIjMwrB%2F6x8rRBQ3Le2dUuVFHBhBbtQ%3D%3D&contentTypeId=12&numOfRows=5&MobileOS=AND&MobileApp=TestParsing&_type=json";

                    "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
                            +"ServiceKey=" + ServiceKey
                            +"&contentTypeId=" + contentTypeId
                            //+"&areaCode=" +areaCode
                            //+"&sigunguCode="+sigunguCode
                            //+"&cat1="+cat1
                            //+"&cat2="+cat2
                            +"&pageNo="+num
                            +"&numOfRows=5"
                            +"&MobileOS=AND&MobileApp=TestParsing&_type=json";
        }
        return urlstr;
    }
    protected void dataParse(JSONArray jarray){

        for (int i = 0; i < jarray.size(); i++) {
            //TourAPI 필수제공data title, contentID, contenttypeid
            //contentID, contenttypeid 장소 세부설명 받아오는데 필요합니다.
            Spot bus = new Spot();

            JSONObject jObject = (JSONObject) jarray.get(i);
            String id = jObject.get("contenttypeid").toString();
            bus.setContentTypeId(id);

            switch (id){
                case("12"):
                    bus.setTitle(jObject.get("title"));
                    bus.setContentid(jObject.get("contentid"));

                    if (jObject.containsKey("addr1")) {
                        bus.setAddr1(jObject.get("addr1"));
                    }

                    //if (jObject.containsKey("firstimage")) {bus.setFirstimage(jObject.get("firstimage"));}

                    if (jObject.containsKey("mapx") && jObject.containsKey("mapy")) {
                        bus.setPos(jObject.get("mapx"),jObject.get("mapy"));
                    }
                    if (jObject.containsKey("tel")) {
                        bus.setTel(jObject.get("tel"));
                    }
                    arrtmp.add(bus);

                    break;
                case("15"): //축제의경우
                    break;
            }
        }
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {    //url 연결, Json Data 받아온 후 파싱.

            String urlstr,str,receiveMsg;
            URL url = null;

            try {
                for (int num=1;num<=3;num++) {  //한번에 5개 장소 가져옵니다. page=3
                    urlstr = getUrl(num);

                    url = new URL(urlstr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                        reader.close();

                        System.out.println(receiveMsg);

                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObjtmp = (JSONObject) jsonParser.parse(receiveMsg);
                        JSONObject parse_response = (JSONObject) jsonObjtmp.get("response");
                        JSONObject parse_body = (JSONObject) parse_response.get("body");

                        if(parse_body.get("items").equals("")){ //정보 없을경우
                            return null;
                        }

                        JSONObject parse_items = (JSONObject) parse_body.get("items");
                        JSONArray jarray = (JSONArray) parse_items.get("item");
                        dataParse(jarray);
                    } else {
                        System.out.println("error");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) { //doInbackground 작업이 끝난 후 실행 됨(확인용입니다)

            for(int i=0;i<arrtmp.size();i++) {
                System.out.println(arrtmp.get(i).getTitle());
            }
        }
    }
}



