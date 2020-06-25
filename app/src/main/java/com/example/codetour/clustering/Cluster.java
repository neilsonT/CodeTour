package com.example.codetour.clustering;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cluster implements Serializable {
    public List<Point> points;
    public Point centroid;
    public int id;
    public boolean select=false;

    //Creates a new Cluster
    public Cluster(){}

    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList();  //cluster에 들어가는 point들의 List
        this.centroid = null;
    }

    public void clear() {
        points.clear();
        select=false;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    /*Get,Set*/
    public List getPoints() {return points;}
    public Point getCentroid() {return centroid;}
    public int getId(){return id;}

    public void setPoints(List points) {this.points = points;}
    public void setCentroid(Point centroid) {this.centroid = centroid;}

}
