package com.example.codetour;

import com.skt.Tmap.TMapPoint;

public class PosItem {
    private String title;
    private String address;

    //위도 경도를 담는 class  http://tmapapi.sktelecom.com/main.html#android/docs/androidDoc.TMapPoint 궁금하면 여기 참고할 것
    private TMapPoint point;

    public PosItem(String title, String address,TMapPoint point) {
        this.title = title;
        this.address = address;
        this.point = point;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public TMapPoint getPoint() {
        return point;
    }

    public void setPoint(TMapPoint point) {
        this.point=point;
    }

}
