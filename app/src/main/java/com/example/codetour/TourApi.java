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
import java.util.HashMap;
import java.util.List;

public class TourApi {

    private String ServiceKey="7MuLFocxJtu7I1bf8JWQrKsLl%2FAEwkKk1U0nGGDRHkCSX1%2Bxp7mB38%2FRIjMwrB%2F6x8rRBQ3Le2dUuVFHBhBbtQ%3D%3D";
    String contentTypeId, cat1, cat2, areaCode, sigunguCode;//startDate,endDate;

    private final HashMap<String, Integer> areaCodeMap = new HashMap<String, Integer>(){
        {
            put("서울", 1);
            put("인천", 2);
            put("대전", 3);
            put("대구", 4);
            put("광주", 5);
            put("부산", 6);
            put("울산", 7);
            put("세종특별자치시", 8);

            put("경기도", 31);
            put("강원도", 32);
            put("충청북도", 33);
            put("충청남도", 34);
            put("경상북도", 35);
            put("경상남도", 36);
            put("전라북도", 37);
            put("전라남도", 38);
            put("제주도", 39);
        }
    };

    private HashMap<String, Integer>[] sigunguCodeMap;

    public TourApi() {
       //ServiceKey = "7MuLFocxJtu7I1bf8JWQrKsLl%2FAEwkKk1U0nGGDRHkCSX1%2Bxp7mB38%2FRIjMwrB%2F6x8rRBQ3Le2dUuVFHBhBbtQ%3D%3D";

        sigunguCodeMap[0] = new HashMap<String, Integer>() {{
            // 경기도
            put("가평군",  1);    put("고양시",  2);    put("과천시",  3);    put("광명시",  4);
            put("광주시",  5);    put("구리시",  6);    put("군포시",  7);    put("김포시",  8);
            put("남양주시",  9);  put("동두천시", 10);   put("부천시", 11);    put("성남시", 12);
            put("수원시", 13);    put("시흥시", 14);    put("안산시", 15);    put("안성시", 16);
            put("안양시", 17);    put("양주시", 18);    put("양평군", 19);    put("여주시", 20);
            put("연천군", 21);    put("오산시", 22);    put("용인시", 23);    put("의왕시", 24);
            put("의정부시", 25);  put("이천시", 26);    put("파주시", 27);    put("평택시", 28);
            put("포천시", 29);    put("하남시", 30);    put("화성시", 31);
        }};

        sigunguCodeMap[1] = new HashMap<String, Integer>() {{
            // 강원도
            put("강릉시",  1);    put("고성군",  2);    put("동해시",  3);    put("삼척시",  4);
            put("속초시",  5);    put("양구군",  6);    put("양양군",  7);    put("영월군",  8);
            put("원주시",  9);    put("인제군", 10);    put("정선군", 11);    put("철원군", 12);
            put("춘천시", 13);    put("태백시", 14);    put("평창군", 15);    put("홍천군", 16);
            put("화천군", 17);    put("횡성군", 18);
        }};

        sigunguCodeMap[2] = new HashMap<String, Integer>() {{
            // 충청북도
            put("괴산군",  1);    put("단양군",  2);    put("보은군",  3);    put("영동군",  4);
            put("옥천군",  5);    put("음성군",  6);    put("제천시",  7);    put("진천군",  8);
            put("청원군",  9);    put("청주시", 10);    put("충주시", 11);    put("증평군", 12);
        }};

        sigunguCodeMap[3] = new HashMap<String, Integer>() {{
            // 충청남도
            put("공주시",  1);    put("금산군",  2);    put("논산시",  3);    put("당진시",  4);
            put("보령시",  5);    put("부여군",  6);    put("서산시",  7);    put("서천군",  8);
            put("아산시",  9);  /*put("없음 ", 10);*/  put("예산군", 11);    put("천안시", 12);
            put("청양군", 13);    put("태안군", 14);    put("홍성군", 15);    put("계룡시", 16);
        }};

        sigunguCodeMap[4] = new HashMap<String, Integer>() {{
            // 경상북도
            put("경산시",  1);    put("경주시",  2);    put("고령군",  3);    put("구미시",  4);
            put("군위군",  5);    put("김천시",  6);    put("문경시",  7);    put("봉화군",  8);
            put("상주시",  9);    put("성주군", 10);    put("안동시", 11);    put("영덕군", 12);
            put("영양군", 13);    put("영주시", 14);    put("영천시", 15);    put("예천군", 16);
            put("울릉군", 17);    put("울진군", 18);    put("의성군", 19);    put("청도군", 20);
            put("청송군", 21);    put("칠곡군", 22);    put("포항시", 23);
        }};

        sigunguCodeMap[5] = new HashMap<String, Integer>() {{
            // 경상남도
            put("거제시",  1);    put("거창군",  2);    put("고성군",  3);    put("김해시",  4);
            put("남해군",  5);    put("마산시",  6);    put("밀양시",  7);    put("사천시",  8);
            put("산청군",  9);    put("양산시", 10);  /*put("없음 ", 11);*/  put("의령군", 12);
            put("진주시", 13);    put("진해시", 14);    put("창녕군", 15);    put("창원시", 16);
            put("통영시", 17);    put("하동군", 18);    put("함안군", 19);    put("함양군", 20);
            put("합천군", 21);
        }};

        sigunguCodeMap[6] = new HashMap<String, Integer>() {{
            // 전라북도
            put("고창군",  1);    put("군산시",  2);    put("김제시",  3);    put("남원시",  4);
            put("무주군",  5);    put("부안군",  6);    put("순창군",  7);    put("완주군",  8);
            put("익산시",  9);    put("임실군", 10);    put("장수군", 11);    put("전주시", 12);
            put("정읍시", 13);    put("진안군", 14);
        }};

        sigunguCodeMap[7] = new HashMap<String, Integer>() {{
            // 전라남도
            put("강진군",  1);    put("고흥군",  2);    put("곡성군",  3);    put("광양시",  4);
            put("구례군",  5);    put("나주시",  6);    put("담양군",  7);    put("목포시",  8);
            put("무안군",  9);    put("보성군", 10);    put("순천시", 11);    put("신안군", 12);
            put("여수시", 13);  /*put("없음 ", 14);*/ /*put("없음", 15);*/   put("영광군", 16);
            put("영암군", 17);    put("완도군", 18);    put("장성군", 19);    put("장흥군", 20);
            put("진도군", 21);    put("함평군", 22);    put("해남군", 23);    put("화순군", 24);
        }};

        sigunguCodeMap[8] = new HashMap<String, Integer>() {{
            // 제주도
            put("남제주군",  1);  put("북제주군",  2);   put("서귀포시",  3);  put("제주시",  4);
        }};
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