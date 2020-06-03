package com.example.codetour.clustering;

import com.example.codetour.Spot;
import com.example.codetour.TourApiManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KMeans implements Serializable {
    //Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS;   //cluster의 개수 k
    //k가 여행 날짜를 기준으로 정해저야 한다

    private List<Point> points;
    private List<Cluster> clusters;
    public KMeans() {
        this.points = new ArrayList();
        this.clusters = new ArrayList();
    }

    //Initializes the process
    public void init(int areaCode, int sigunguCode, List<String> list_cat1, List<String> list_cat2, List<String> list_food,int difdays) {
        NUM_CLUSTERS = difdays;
        for (int i=0;i<list_cat1.size();i++){
            getPointData(areaCode,sigunguCode,list_cat1.get(i),list_cat2.get(i));
        }
        if(points.size()<NUM_CLUSTERS){
            System.out.println("size : "+points.size());
            System.out.println("충분한 point가 존재하지 않습니다.");
        }

        //Create Clusters
        //Set Random Centroids
        for (int i = 0; i<NUM_CLUSTERS ;i++){
            Cluster cluster = new Cluster(i);
            Point centroid = new Point(points.get(i).getX(),points.get(i).getY()); //여기서 오류남
            cluster.setCentroid(centroid);
            clusters.add(cluster);
        }
        //plotClusters();
    }

    public void getPointData(int areaCode, int sigunguCode, String cat1, String cat2){
        List<Point> tmp_list=new ArrayList<Point>();
        //System.out.println("areaCode, sigungoCode, cat1, cat2 : "+areaCode+" ,"+sigunguCode+" ,"+cat1+" ,"+cat2);
    tmp_list=TourApiManager.getInstance().getPoint(areaCode,sigunguCode,cat1,cat2);
        if(tmp_list!=null)
            points.addAll(tmp_list);
        //System.out.println("tmp_list 출력 시작");
        //for(int i=0; i<tmp_list.size(); ++i) System.out.println(tmp_list.get(i).getContentid());
}

    /*private void plotClusters() {   //Print
        for (int i = 0; i<NUM_CLUSTERS ;i++){
            Cluster c = clusters.get(i);
            c.plotCluster();
        }
    }*/

    //The process to calculate the K Means, with iterating method.
    public List<Cluster> calculate() {
        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one.
        while (!finish) {
            //Clear cluster state
            clearClusters();

            List<Point> lastCentroids = getCentroids();

            //Assign points to the closer cluster
            assignCluster();

            //Calculate new centroids.
            calculateCentroids();//각 cluster의 centroid 재계산

            iteration++;

            List<Point> currentCentroids = getCentroids();

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for (int i = 0; i<lastCentroids.size(); i++){
                distance += Point.distance(lastCentroids.get(i), currentCentroids.get(i));
            }
            //System.out.println("#################");
            //System.out.println("Iteration: " + iteration);
            //System.out.println("Centroid distances: " + distance);
            //plotClusters();

            if (distance ==0) {
                finish = true;
            }
        }
        return clusters;
    }

    private void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List<Point> getCentroids() {
        List<Point> centroids = new ArrayList(NUM_CLUSTERS);
        for (Cluster cluster : clusters) {
            Point aux = cluster.getCentroid();
            Point point = new Point(aux.getX(), aux.getY());
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double distance = 0.0;

        for (Point point : points) {
            min = max;
            for (int i = 0; i<NUM_CLUSTERS ;i++){   //클러스터의 개수만큼 (한 점과 모든 클러스터에 대하여 계산)
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if (distance<min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);  //centroid와의 거리가 제일 가까운 클러스터로 Point 넣기...
            clusters.get(cluster).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for (Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            List<Point> list = cluster.getPoints();
            int n_points = list.size();

            for (Point point : list) {
                sumX += point.getX();
                sumY += point.getY();
            }

            Point centroid = cluster.getCentroid();
            if (n_points!=0){
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                centroid.setX(newX);
                centroid.setY(newY);
            }
        }
    }
}
