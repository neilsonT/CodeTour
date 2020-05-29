package com.example.codetour;

import android.os.AsyncTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TourApi {

    private String ServiceKey="7MuLFocxJtu7I1bf8JWQrKsLl%2FAEwkKk1U0nGGDRHkCSX1%2Bxp7mB38%2FRIjMwrB%2F6x8rRBQ3Le2dUuVFHBhBbtQ%3D%3D";
    String contentTypeId, cat1, cat2, areaCode, sigunguCode;//startDate,endDate;


    public TourApi() {
       //ServiceKey = "7MuLFocxJtu7I1bf8JWQrKsLl%2FAEwkKk1U0nGGDRHkCSX1%2Bxp7mB38%2FRIjMwrB%2F6x8rRBQ3Le2dUuVFHBhBbtQ%3D%3D";
    }

    public List<Spot> getData(String contentTypeId, int areaCode, int sigunguCode, String cat1, String cat2) {
        this.contentTypeId = contentTypeId;
        this.areaCode = Integer.toString(areaCode);
        this.sigunguCode = Integer.toString(sigunguCode);
        this.cat1 = cat1;
        this.cat2 = cat2;

        GetDataAsyncTask myAsyncTask = new GetDataAsyncTask();
        try {
            return myAsyncTask.execute().get();
        } catch (Exception e) {
            return null;
        }
    }
    public List<Spot> getData(String contentTypeId, int areaCode, String cat1, String cat2) {

        this.contentTypeId = contentTypeId;
        this.areaCode = Integer.toString(areaCode);
        this.cat1 = cat1;
        this.cat2 = cat2;

        this.sigunguCode = "";

        GetDataAsyncTask myAsyncTask = new GetDataAsyncTask();
        try {
            return myAsyncTask.execute().get();
        } catch (Exception e) {
            return null; //
        }
    }
    public List<Spot> getData(String contentTypeId, int areaCode, int sigunguCode, String cat1) {
        this.contentTypeId = contentTypeId;
        this.areaCode = Integer.toString(areaCode);
        this.sigunguCode = Integer.toString(sigunguCode);
        this.cat1 = cat1;

        this.cat2 = "";

        GetDataAsyncTask myAsyncTask = new GetDataAsyncTask();
        try {
            return myAsyncTask.execute().get();
        } catch (Exception e) {
            return null; //
        }
    }
    public List<Spot> getData(String contentTypeId, int areaCode, String cat1) {
        this.contentTypeId = contentTypeId;
        this.areaCode = Integer.toString(areaCode);
        this.cat1 = cat1;

        this.sigunguCode = "";
        this.cat2 = "";
        GetDataAsyncTask myAsyncTask = new GetDataAsyncTask();
        try {
            return myAsyncTask.execute().get();
        } catch (Exception e) {
            return null; //
        }
    }
    public List<Spot> getData(String contentTypeId, int areaCode) {
        this.contentTypeId = contentTypeId;
        this.areaCode = Integer.toString(areaCode);

        this.cat1 = "";
        this.sigunguCode = "";
        this.cat2 = "";

        GetDataAsyncTask myAsyncTask = new GetDataAsyncTask();
        try {
            return (myAsyncTask.execute().get());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public List<Spot> getRestaurant(Spot spot,String classifi){ //음식점을 받아옵니다.
        GetRestaurantAsyncTask asynctask = new GetRestaurantAsyncTask();
        cat2=classifi;
        try {
            return asynctask.execute(spot).get();
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    public void getExplain(Spot spot) {
        GetExplainAsyntaskTask asynctask = new GetExplainAsyntaskTask();
        asynctask.execute(spot);
    }


    protected String getUrl(int num) { //URL가져오기
        String urlstr = "";
        /*
        if (contentTypeId == "15") {   //축제의 경우(임시)
            urlstr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?"
                    + "ServiceKey=" + ServiceKey
                    + "&arrange=B"   //정렬기준(조회순)
                    //+"&areaCode=" +areaCode
                    //+"&sigunguCode="+sigunguCode
                    //+"&eventStartDate="+startDate
                    //+"&eventEndDate="+endDate
                    + "&numOfRows=5" +
                    "&MobileOS=AND&MobileApp=TestParsing&_type=json";
        } else { //"http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=7MuLFocxJtu7I1bf8JWQrKsLl%2FAEwkKk1U0nGGDRHkCSX1%2Bxp7mB38%2FRIjMwrB%2F6x8rRBQ3Le2dUuVFHBhBbtQ%3D%3D&contentTypeId=12&numOfRows=5&MobileOS=AND&MobileApp=TestParsing&_type=json";*/

        urlstr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?"
                + "ServiceKey=" + ServiceKey
                + "&contentTypeId=" + contentTypeId
                + "&areaCode=" + areaCode
                + "&cat1=" + cat1
                + "&cat2=" + cat2
                + "&pageNo=" + num
                + "&numOfRows=5"
                + "&MobileOS=AND&MobileApp=TestParsing&_type=json";
        return urlstr;
    }
    protected List<Spot> dataParse(JSONArray jarray, List<Spot> spotlist) {
        for (int i = 0; i < jarray.size(); i++) {
            //TourAPI 필수제공data title, contentID, contenttypeid
            //contentID, contenttypeid 장소 세부설명 받아오는데 필요합니다.
            Spot bus = new Spot();
            JSONObject jObject = (JSONObject) jarray.get(i);

            bus.setContentTypeId(jObject.get("contenttypeid"));
            bus.setTitle(jObject.get("title"));
            bus.setContentid(jObject.get("contentid"));

            if (jObject.containsKey("addr1")) {
                bus.setAddr1(jObject.get("addr1"));
            }

            //if (jObject.containsKey("firstimage")) {bus.setFirstimage(jObject.get("firstimage"));}

            if (jObject.containsKey("mapx") && jObject.containsKey("mapy")) {
                bus.setPos(jObject.get("mapx"), jObject.get("mapy"));
            }
            if (jObject.containsKey("tel")) {
                bus.setTel(jObject.get("tel"));
            }

            if(jObject.containsKey("firstimage2")){
                bus.setFirstImage2((String)jObject.get("firstimage2"));
            }

            spotlist.add(bus);
        }

        return spotlist;
    }
    protected List<Spot> dataParse_restaurant(JSONArray jarray,List<Spot> spotlist){

        for (int i = 0; i < jarray.size(); i++) {
            //TourAPI 필수제공data title, contentID, contenttypeid
            //contentID, contenttypeid 장소 세부설명 받아오는데 필요합니다.
            Spot bus = new Spot();
            JSONObject jObject = (JSONObject) jarray.get(i);

            if(jObject.get("cat2")!=cat2)
                continue;

            bus.setContentTypeId(jObject.get("contenttypeid"));
            bus.setTitle(jObject.get("title"));
            bus.setContentid(jObject.get("contentid"));

            if (jObject.containsKey("addr1")) {
                bus.setAddr1(jObject.get("addr1"));
            }

            //if (jObject.containsKey("firstimage")) {bus.setFirstimage(jObject.get("firstimage"));}

            if (jObject.containsKey("mapx") && jObject.containsKey("mapy")) {
                bus.setPos(jObject.get("mapx"), jObject.get("mapy"));
            }
            if (jObject.containsKey("tel")) {
                bus.setTel(jObject.get("tel"));
            }

            spotlist.add(bus);
        }

        return spotlist;

    }

    public class GetDataAsyncTask extends AsyncTask<String, Void, List<Spot>> {

        @Override
        protected List<Spot> doInBackground(String... strings) {    //url 연결, Json Data 받아온 후 파싱.
            List<Spot> spotList = new ArrayList<>();
            String urlstr, str, receiveMsg;
            URL url = null;

            try {
                for (int num = 1; num <= 3; num++) {  //한번에 5개 장소 가져옵니다. page=3
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

                        if (parse_body.get("items").equals("")) { //정보 없을경우
                            return null;
                        }

                        JSONObject parse_items = (JSONObject) parse_body.get("items");
                        JSONArray jarray = (JSONArray) parse_items.get("item");
                        spotList = dataParse(jarray, spotList);

                    } else {
                        System.out.println("error");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return spotList;
        }
/*
        @Override
        protected void onPostExecute(List<Spot> result) { //doInbackground 작업이 끝난 후 실행 됨(확인용입니다)

            for (int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i).getTitle());
            }
        }
 */
    }
    public class GetExplainAsyntaskTask extends AsyncTask<Spot, Void, Void> {
        @Override
        protected Void doInBackground(Spot... spot) {    //url 연결, Json Data 받아온 후 파싱.
            String urlstr, str, receiveMsg;

            URL url = null;
            urlstr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?"
                    + "ServiceKey=" + ServiceKey
                    + "&contentId=" + spot[0].getContentid()
                    + "&overviewYN=Y"
                    + "&MobileOS=AND&MobileApp=TestParsing&_type=json";

            try {
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

                    if (parse_body.get("items").equals("")) { //정보 없을경우
                        return null;
                    }

                    JSONObject parse_items = (JSONObject) parse_body.get("items");
                    JSONObject parse_item = (JSONObject) parse_items.get("item");

                    if (parse_item.containsKey("overview")) {
                        spot[0].setExplain(parse_item.get("overview"));
                        System.out.println("in!");
                    }

                } else {
                    System.out.println("error");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("error111");
            }
            return null;
        }
    }
    public class GetRestaurantAsyncTask extends AsyncTask<Spot, Void, List<Spot>> {    //SpotList를 가져올까요?
        @Override
        protected List<Spot> doInBackground(Spot... spot) {    //url 연결, Json Data 받아온 후 파싱.
            String str, receiveMsg;
            List<Spot> tmp_list=null;
            URL url = null;

            try {
                for (int num = 1; num <= 3; num++) {  //한번에 5개 장소 가져옵니다. page=3
                    String urlstr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?"
                            + "ServiceKey=" + ServiceKey
                            + "&pageNo="+num
                            + "&numOfRows=5&listYN=Y&contentTypeId=39&arrange=P"
                            + "&mapX="+spot[0].getPos()[0]
                            + "&mapY="+spot[0].getPos()[1]
                            + "radius=2000" //단위 m
                            + "&MobileOS=AND&MobileApp=TestParsing&_type=json";

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

                        if (parse_body.get("items").equals("")) { //정보 없을경우
                            return tmp_list;
                        }

                        JSONObject parse_items = (JSONObject) parse_body.get("items");
                        JSONArray jarray = (JSONArray) parse_items.get("item");

                        tmp_list = dataParse_restaurant(jarray,tmp_list);

                    } else {
                        System.out.println("error");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return tmp_list;
        }
    }
}