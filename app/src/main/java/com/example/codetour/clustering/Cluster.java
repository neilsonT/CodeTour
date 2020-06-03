package com.example.codetour.clustering;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    public List<Point> points;
    public Point centroid;
    public int id;

    //Creates a new Cluster
    public Cluster(){}

    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList();  //cluster에 들어가는 point들의 List
        this.centroid = null;
    }

    public void clear() {
        points.clear();
    }

    public void addPoint(Point point) {
        points.add(point);
    }
/*
    public void plotCluster() {
        System.out.println("[Cluster: " + id+"]");
        System.out.println("[Centroid: " + centroid + "]");
        System.out.println("[Points: \n");
        for(int i=0;i<points.size();i++) {
            System.out.println(points.get(i));
        }
        System.out.println("]");
    }
*/
    /*Get,Set*/
    public List getPoints() {return points;}
    public Point getCentroid() {return centroid;}
    public int getId(){return id;}

    public void setPoints(List points) {this.points = points;}
    public void setCentroid(Point centroid) {this.centroid = centroid;}

}
