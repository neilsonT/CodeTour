package com.example.codetour;

import android.view.inputmethod.CorrectionInfo;

import com.example.codetour.clustering.Cluster;
import com.example.codetour.clustering.KMeans;
import com.example.codetour.clustering.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Recommend implements Serializable {

    //TourApi tourApi;
    TripSchedule tripSchedule;
    List<Spot> recommendSpotList;
    KMeans kMeans;

    public void setTripSchedule(TripSchedule trip){
        tripSchedule = trip;
    }

    public Recommend(){ }

    public void recommendSpot(){ }
    public void recommendCourse(){
        //숙소좌표 받아와야 합니다()
        List<Cluster> clusters=RecWithClustering();

        clusters=sortPointReadCount(clusters); //조회순을 기준으로 오름차순으로 정렬된 클러스터

        int j=0;
        for(Cluster cluster : clusters){
            List<Point> points= new ArrayList<>();

            int count ;
            if (cluster.points.size() <5)
                count=cluster.points.size();
            else
                count=5;
            for(int i=0;i<count;i++) {//활동시간 계산하여 몇개 받아올지 계산해야합니다(i)값으로 주세요!
                points.add(cluster.points.get(i));
                //points=sortPointDistance(points);
                //거리순으로 정렬합니다.
            }
            recommendSpotList=TourApiManager.getInstance().getSpot(points);
            tripSchedule.courseManager.courseList.get(j).spotManager.spotList=recommendSpotList;
            j++;
        }

    }


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

    public List<Cluster> RecWithClustering(){
        List<String> list_cat1 = new ArrayList<String>();//대분류 코드
        List<String> list_cat2 = new ArrayList<String>(); //중분류 코드
        List<String> list_food = new ArrayList<String>(); //음식점 코드
        for (int i=0;i<tripSchedule.theme_selection.size();i++){
            switch (tripSchedule.theme_selection.get(i)) {
                case 1:
                    list_cat1.add("A01");
                    list_cat2.add("A0101");
                    break;
                case 2:
                    list_cat1.add("A01");
                    list_cat2.add("A0102");
                    break;
                case 3:
                    list_cat1.add("A02");
                    list_cat2.add("A0201");
                    break;
                case 4:
                    list_cat1.add("A02");
                    list_cat2.add("A0202");
                    break;
                case 5:
                    list_cat1.add("A02");
                    list_cat2.add("A0203");
                    break;
                case 6:
                    list_cat1.add("A02");
                    list_cat2.add("A0204");
                    break;
                case 7:
                    list_cat1.add("A02");
                    list_cat2.add("A0205");
                    break;
                case 8:
                    list_cat1.add("A02");
                    list_cat2.add("A0206");
                    break;
                case 9:
                    list_cat1.add("A02");
                    list_cat2.add("A0207");
                    break;
                case 10:
                    list_cat1.add("A02");
                    list_cat2.add("A0208");
                    break;
                default:
                    break;
            }
        }
        for (int i=0;i<tripSchedule.food_selection.size();i++){
            switch (tripSchedule.food_selection.get(i)) {
                case 0:
                    list_food.add("A05020100");
                    break;
                case 1:
                    list_food.add("A05020200");
                    break;
                case 2:
                    list_food.add("A05020300");
                    break;
                case 3:
                    list_food.add("A05020400");
                    break;
                case 4:
                    list_food.add("A05020500");
                    break;
                default:
                    break;
            }
        }
        kMeans= new KMeans();
        kMeans.init(tripSchedule.areacode, tripSchedule.sigungucode, list_cat1,list_cat2,list_food, tripSchedule.difdays);
        List<Cluster> clusters = kMeans.calculate();
        return clusters;

    }//kMeans 클러스터링 후 return clusterList
    public List<Cluster> sortPointReadCount(List<Cluster> clusters) {

        Comparator<Point> cmp=new Comparator<Point>(){

            @Override
            public int compare(Point p1, Point p2){
                if(p1.getReadcount()>p2.getReadcount()){
                    return -1;
                }
                else if(p1.getReadcount()==p2.getReadcount()){
                    return 0;
                }
                else
                    return 1;
            }

        };

        for(Cluster cluster: clusters)
            Collections.sort(cluster.points,cmp);//오름차순 정렬

        return clusters;


    }//조회순 정렬을 위해 사용합니다.
    public List<Point> sortPointDistance(List<Point> points) {

        Comparator<Point> cmp = new Comparator<Point>() {

            @Override
            public int compare(Point p1, Point p2) {
                //받아온 숙소 좌표와 distance 계산
                //distance별로 정렬(내림차순)
                return 1;
            }
        };

        for (Point point : points) {
            Collections.sort(points, cmp);
        }

        return points;
    }
}
