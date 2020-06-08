package com.example.codetour;

import android.view.inputmethod.CorrectionInfo;

import com.example.codetour.clustering.Cluster;
import com.example.codetour.clustering.KMeans;
import com.example.codetour.clustering.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Recommend implements Serializable {

    TripSchedule tripSchedule;
    List<Spot> recommendSpotList;
    KMeans kMeans;
    List<String> list_cat1 = new ArrayList<String>();//대분류 코드
    List<String> list_cat2 = new ArrayList<String>(); //중분류 코드
    List<String> list_food = new ArrayList<String>(); //음식점 코드


    public void setTripSchedule(TripSchedule trip) {
        tripSchedule = trip;
    }

    public Recommend() {
    }

    public List<Spot> recommendSpot(Spot spot) {

        List<Spot> spots = new ArrayList<>();
        spots = TourApiManager.getInstance().getSpotUseDist(spot);//스팟 근처의 장소들을 받아옵니다.

        return spots;//장소 없을경우 null return하는 경우 존재합니다

    }

    public void recommendCourse() {

        HashMap<String, List> hash_map = SetUserData();

        for (String key : hash_map.keySet()) {

            List value = hash_map.get(key);
            List<Cluster> clusters = RecWithClustering(tripSchedule.areacode[0][(int) value.get(0)], tripSchedule.sigungucode[0][(int) value.get(0)],value.size());//클러스터링
            System.out.println(key+""+value.size());
            for (int i = 0; i < value.size(); i++) {

                Point start_point = new Point(tripSchedule.startPosVal[(int) value.get(i)][0], tripSchedule.startPosVal[(int) value.get(i)][1]);
                Point end_point = new Point(tripSchedule.endPosVal[(int) value.get(i)][0], tripSchedule.endPosVal[(int) value.get(i)][1]);

                Cluster selec_cluster = SelecClusterDistance(clusters, start_point);
                List<Point> selec_points = SelecPoint(selec_cluster, start_point, end_point);

                recommendSpotList = TourApiManager.getInstance().getSpot(selec_points);
                tripSchedule.courseManager.courseList.get((int) value.get(i)).spotManager.spotList = recommendSpotList;

            }
        }
    }




    private List<Cluster> RecWithClustering(int areacode, int sigungucode,int difdays) { //kMeans 클러스터링 후 return clusterList

        kMeans = new KMeans();

        kMeans.init(areacode, sigungucode, list_cat1, list_cat2, list_food, difdays);//difdays 구해야합니다.
        List<Cluster> clusters = kMeans.calculate();
        return clusters;

    }

    private HashMap<String, List> SetUserData() {

        for (int i = 0; i < tripSchedule.theme_selection.size(); i++) {
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
        for (int i = 0; i < tripSchedule.food_selection.size(); i++) {
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

        HashMap<String, List> tmp_hashmap = new HashMap<>();

        for (int day = 0; day < tripSchedule.difdays; day++) {

            String tmp_key = Integer.toString(tripSchedule.areacode[0][day]) + Integer.toString(tripSchedule.sigungucode[0][day]);

            if (tmp_hashmap.containsKey(tmp_key)) {
                tmp_hashmap.get(tmp_key).add(day);
            } else {
                List tmp = new ArrayList();
                tmp.add(day);
                tmp_hashmap.put(tmp_key, tmp);
            }
        }

        return tmp_hashmap;
    }

    private Cluster SelecClusterDistance(List<Cluster> clusters, Point start_point) {

        Cluster sel_cluster = null;
        double min_dist = Double.MAX_VALUE;
        double dist;

        for (Cluster cluster : clusters) {
            if (cluster.select)
                continue;

            dist = Point.distance(start_point, cluster.centroid);
            if (dist < min_dist) {
                min_dist = dist;
                sel_cluster = cluster;
            }
        }

        sel_cluster.select = true;
        sortPointReadCount(sel_cluster);
        return sel_cluster;
    }

    private List<Point> SelecPoint(Cluster cluster, Point start_point, final Point end_point) {

        Comparator<Point> compare = new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                if(Point.distance(end_point, p1)<Point.distance(end_point, p2))
                    return -1;
                else if(Point.distance(end_point, p1)==Point.distance(end_point, p2))
                    return 0;
                else
                    return 1;
            }
        };


        List<Point> point_list=new ArrayList<>();
        List<Point> tmp_list=new ArrayList<>();

        if (cluster.points.size() < 5) {
            for(int i=0;i<cluster.points.size();i++)
                tmp_list.add(cluster.points.get(i));
        }
        else{
            for(int i=0;i<5;i++)
                tmp_list.add(cluster.points.get(i));
        }

        //point_list.add(start_point);
        Collections.sort(tmp_list,compare);//오름차순으로 정렬

        //출발지점과 도착지점이 같은경우
        if (start_point.getX() == end_point.getX() && start_point.getY() == end_point.getY()) {
            List<Point> right=new ArrayList<>();
            List<Point> left= new ArrayList<>();

            for(Point point:tmp_list){
                if(point.getX()<start_point.getX()){
                    left.add(point);
                }
                else{
                    right.add(point);
                }
            }

            System.out.println("left size:"+left.size());
            System.out.println("right size:"+right.size());

            if(right.size()==0){
                point_list=SortPoint(left);
            }
            else if(left.size()==0){
                point_list=SortPoint(right);
            }
            else {
                Collections.reverse(right);
                point_list.addAll(left);
                point_list.addAll(right);
            }
        } else {//출발지점과 도착지점이 다른경우 (도착지점이 먼 곳에서부터 가까운곳으로)
            Collections.reverse(tmp_list);
            point_list.addAll(tmp_list);
        }

        //point_list.add(end_point);

        return point_list;

    }

    private void sortPointReadCount(Cluster cluster) {

        Comparator<Point> cmp = new Comparator<Point>() {

            @Override
            public int compare(Point p1, Point p2) { //point 내림차순 정렬
                if (p1.getReadcount() > p2.getReadcount()) {
                    return -1;
                } else if (p1.getReadcount() == p2.getReadcount()) {
                    return 0;
                } else
                    return 1;
            }

        };

        Collections.sort(cluster.points, cmp);

    }
    private List<Point> SortPoint(List<Point> list){

        double middle_point=0;
        List<Point> return_list=new ArrayList<>();
        for(Point point : list){
            middle_point+=point.getY();
        }

        double avr_point=middle_point/list.size();

        List<Point> up_list=new ArrayList<>();
        List<Point> down_list=new ArrayList<>();

        for(Point point : list){
            if(point.getY()<avr_point)
                down_list.add(point);
            else
                up_list.add(point);
        }

        if(down_list.size()>up_list.size()){
            return_list.addAll(down_list);
            Collections.reverse(up_list);
            return_list.addAll(up_list);
        }

        return return_list;
    }
}
