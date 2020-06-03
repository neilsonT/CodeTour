package com.example.codetour.clustering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point {

    private double x = 0;
    private double y = 0;
    private String contentid="";
    private int cluster_number = 0;
    private int num;

    public Point(){}

    public Point(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }

    public void setX(Object x) {
        if(x instanceof String)
            this.x=Double.parseDouble((String)x);
        else
            this.x=Double.parseDouble(x.toString());
    }

    public double getX()  {
        return this.x;
    }

    public void setY(Object y) {
        if(y instanceof String)
            this.y=Double.parseDouble((String)y);
        else
            this.y=Double.parseDouble(y.toString());
    }
    public double getY() {
        return this.y;
    }

    public void setContentid(Object contentid){ this.contentid=contentid.toString();}

    public String getContentid(){return this.contentid;}

    public void setCluster(int n) {
        this.cluster_number = n;
    }

    public int getCluster() {
        return this.cluster_number;
    }

    //Calculates the distance between two points.
    protected static double distance(Point p, Point centroid) {
        return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
    }

    //Creates random point

    public String toString() {
        return "("+x+","+y+")";
    }
}
