package com.example.codetour;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recommend implements Serializable {

    //TourApi tourApi;
    TripSchedule tripSchedule;
    List<Spot> recommendSpotList;

    public void setTripSchedule(TripSchedule trip){
        tripSchedule = trip;
    }

    public Recommend(){ }

    public void recommendSpot(){ }
    public void recommendCourse(){ }

    public void makeCourses(){
        //각 날짜에 대해서 코스를 만드는 메소드
        for(int i=0; i<tripSchedule.difdays; ++i){
            tripSchedule.courseManager.addSpotAt(i, 0, recommendSpotList.get(2*i  ));
            tripSchedule.courseManager.addSpotAt(i, 1, recommendSpotList.get(2*i+1));
        }
    }

    public void setRecommendSpotList(){
        //tourApi로부터 스팟들의 리스트를 받아와서 임시로 저장해놓는 메소드
        recommendSpotList = TourApiManager.getInstance().getData(tripSchedule.contentTypeID, tripSchedule.areacode);
        System.out.println("Successfully got list");
        for(int i=0; i<recommendSpotList.size(); ++i) System.out.println(recommendSpotList.get(i).title);
    }

    public void Test(){

        //tourApi=new TourApi();
        //List<Spot> tmp=new ArrayList<>();

        /*
        한번 요청할 때 마다 15개씩 받아옵니다.
        지역코드, 시군구코드는 int형 나머지는 string형 입니다
        spot에 x좌표,y좌표, contentid, contenttypeid, title, tel, add1 받아옵니다 ( + title, tel, add1은 추천에서 필요 없으면 getExplain에서 받아올까요?)
         */
        //tmp = tourApi.getData("12",1);


        //for(int i=0;i<tmp.size();i++)
            //System.out.println(tmp.get(i).getTitle());  //확인용

        /* 하나씩 개요설명 하나하나 요청해야합니다
            추천 모두 완료한 후 받아옵시다.
            지금은 설명만 받아옵니다( + 대표이미지도 여기서 받아올까요?)
         */
        //tourApi.getexplain(tmp.get(0));
    }
}
