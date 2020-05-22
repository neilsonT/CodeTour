package com.example.codetour;

public class TourApiManager {
    static TourApi tourApi = null;
    public static TourApi getInstance(){
        if(tourApi == null) tourApi = new TourApi();
        return tourApi;
    }
}
