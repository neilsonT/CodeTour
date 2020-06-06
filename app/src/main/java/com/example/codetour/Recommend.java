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

    TripSchedule tripSchedule;
    List<Spot> recommendSpotList;
    KMeans kMeans;

    public void setTripSchedule(TripSchedule trip){
        tripSchedule = trip;
    }

    public Recommend(){ }

    public List<Spot> recommendSpot(Spot spot){

        List<Spot> spots = new ArrayList<>();
        spots=TourApiManager.getInstance().getSpotUseDist(spot);//스팟 근처의 장소들을 받아옵니다.

        return spots;//장소 없을경우 null return하는 경우 존재합니다

    }

    public void recommendCourse(){

        List<Cluster> clusters=RecWithClustering();//클러스터링
        clusters=sortPointReadCount(clusters);

        for(int i=0;i<tripSchedule.difdays;i++){
            List<Point> points= new ArrayList<>();
            Cluster tmp=clusters.get(i);
            if(tmp.points.size()<5){
                for(int j=0;j<tmp.points.size();j++)
                    points.add(tmp.points.get(j));
            }
            else{
                for(int j=0;j<5;j++)
                    points.add(tmp.points.get(j));
            }
            recommendSpotList=TourApiManager.getInstance().getSpot(points);
            tripSchedule.courseManager.courseList.get(i).spotManager.spotList=recommendSpotList;
        }


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
            Collections.sort(cluster.points,cmp);//cluster내부에서 point들 오름차순 정렬

        return clusters;
    }

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
