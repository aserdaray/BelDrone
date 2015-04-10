package com.beldrone.asay.beldrone.Model;

import java.sql.Timestamp;

/**
 * Created by asay on 25.12.2014.
 */
public class FollowMe {
    Double lat = 0.0;
    Double lng = 0.0;
    int bearing = 0;
    //int kopterID = 0;
    Timestamp time;
    int event = 0;
    int followMeDeviceId;
    String name="";
    int routeId=0;


    public void setName(String newName){
        name = newName;
    }

    public String getName(){
        return this.name;
    }

    public void setLat(double latitude) {
        this.lat = latitude;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLon(double longitude) {
        this.lng = longitude;
    }

    public double getLon() {
        return this.lng;
    }

    public void setBearing(int brng) {
        this.bearing = brng;
    }

    public int getBearing() {
        return this.bearing;
    }

    public void setTime(Timestamp tmstmp) {
        this.time = tmstmp;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setEvent(int e) {
        this.event = e;
    }

    public int getEvent() {
        return this.event;
    }

    public void setFollowMeDeviceId(int id) {
        this.followMeDeviceId = id;
    }

    public int getFollowMeDeviceId() {
        return this.followMeDeviceId;
    }

    public int getRouteId(){
        return this.routeId;
    }
    public void setRouteId(int routeId){
        this.routeId = routeId;
    }

}
