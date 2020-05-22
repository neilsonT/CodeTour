package com.example.codetour;

//싱글톤으로 TourAPI클래스를 활용하기 위해, TourApiManager를 선언하여 모든 필드와 메소드를 static으로 함.
public class TourApiManager {
    static TourApi tourApi = null;
    public static TourApi getInstance(){
        if(tourApi == null) tourApi = new TourApi();
        return tourApi;
    }
}
