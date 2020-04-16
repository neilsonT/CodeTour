package com.example.codetour.vo;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

// 장소 클래스
public class Place implements Parcelable {

        private String name;
        private String tel;
        private String address;
        private double lat;
        private double lon;
        private String detail;
//    private Image imageView;
        private boolean type;


    public Place() {
        this("");
    }

    public Place(String address){
        this("","",address,0,0,"",null,false);
    }

    public Place(String name, String tel, String address, double lat, double lon, String detail, Image image, boolean type) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.detail = detail;
//        this.iamge = image;
        this.type = type;
    }

    protected Place(Parcel in) {
        name = in.readString();
        tel = in.readString();
        address = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        detail = in.readString();
        type = in.readByte() != 0;
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(tel);
        parcel.writeString(address);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
        parcel.writeString(detail);
        parcel.writeByte((byte) (type ? 1 : 0));
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isType() {
        return type;
    }
}
