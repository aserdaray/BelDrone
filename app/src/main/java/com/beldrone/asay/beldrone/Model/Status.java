package com.beldrone.asay.beldrone.Model;

import java.sql.Timestamp;

/**
 * Created by asay on 24.12.2014.
 */
public class Status
{
    private String kopterVoltage;
    private String kopterId;
    private String batteryCurrent;
    private String gsmSignalStrength;
    private String kopterHeading;
    private String homeLatitude;
    private String kopterErrorCode;
    private String kopterLatitude;
    private String batteryCapacity;
    private String kopterSpeed;
    private String gpsSatCount;
    private String kopterVario;
    private String homeLongitude;
    private String kopterLongitude;
    private String kopterRcSignal;
    private String kopterAltitude;
    private String FlagsNC;
    private String FlagsStatusFC1;
    private String FlagsStatusFC2;
    public int sessionId;
    public Timestamp updateTime;


    public String getFlagsStatusFC1() {
        return FlagsStatusFC1;
    }

    public void setFlagsStatusFC1(String flagsStatusFC1) {
        FlagsStatusFC1 = flagsStatusFC1;
    }

    public String getFlagsStatusFC2() {
        return FlagsStatusFC2;
    }

    public void setFlagsStatusFC2(String flagsStatusFC2) {
        FlagsStatusFC2 = flagsStatusFC2;
    }

    public String getFlagsNC() {
        return FlagsNC;
    }

    public void setFlagsNC(String flagsNC) {
        FlagsNC = flagsNC;
    }

    public String getKopterVoltage() {
        return kopterVoltage;
    }

    public void setKopterVoltage(String kopterVoltage) {
        this.kopterVoltage = kopterVoltage;
    }

    public String getKopterId() {
        return kopterId;
    }

    public void setKopterId(String kopterId) {
        this.kopterId = kopterId;
    }

    public String getBatteryCurrent() {
        return batteryCurrent;
    }

    public void setBatteryCurrent(String batteryCurrent) {
        this.batteryCurrent = batteryCurrent;
    }

    public String getGsmSignalStrength() {
        return gsmSignalStrength;
    }

    public void setGsmSignalStrength(String gsmSignalStrength) {
        this.gsmSignalStrength = gsmSignalStrength;
    }

    public String getKopterHeading() {
        return kopterHeading;
    }

    public void setKopterHeading(String kopterHeading) {
        this.kopterHeading = kopterHeading;
    }

    public String getHomeLatitude() {
        return homeLatitude;
    }

    public void setHomeLatitude(String homeLatitude) {
        this.homeLatitude = homeLatitude;
    }

    public String getKopterErrorCode() {
        return kopterErrorCode;
    }

    public void setKopterErrorCode(String kopterErrorCode) {
        this.kopterErrorCode = kopterErrorCode;
    }

    public String getKopterLatitude() {
        return kopterLatitude;
    }

    public void setKopterLatitude(String kopterLatitude) {
        this.kopterLatitude = kopterLatitude;
    }

    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public String getKopterSpeed() {
        return kopterSpeed;
    }

    public void setKopterSpeed(String kopterSpeed) {
        this.kopterSpeed = kopterSpeed;
    }

    public String getGpsSatCount() {
        return gpsSatCount;
    }

    public void setGpsSatCount(String gpsSatCount) {
        this.gpsSatCount = gpsSatCount;
    }

    public String getKopterVario() {
        return kopterVario;
    }

    public void setKopterVario(String kopterVario) {
        this.kopterVario = kopterVario;
    }

    public String getHomeLongitude() {
        return homeLongitude;
    }

    public void setHomeLongitude(String homeLongitude) {
        this.homeLongitude = homeLongitude;
    }

    public String getKopterLongitude() {
        return kopterLongitude;
    }

    public void setKopterLongitude(String kopterLongitude) {
        this.kopterLongitude = kopterLongitude;
    }

    public String getKopterRcSignal() {
        return kopterRcSignal;
    }

    public void setKopterRcSignal(String kopterRcSignal) {
        this.kopterRcSignal = kopterRcSignal;
    }

    public String getKopterAltitude() {
        return kopterAltitude;
    }

    public void setKopterAltitude(String kopterAltitude) {
        this.kopterAltitude = kopterAltitude;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
