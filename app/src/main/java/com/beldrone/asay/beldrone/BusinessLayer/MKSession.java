package com.beldrone.asay.beldrone.BusinessLayer;

import com.beldrone.asay.beldrone.BusinessLayer.Enum.DeviceType;

import java.sql.Timestamp;

/**
 * Created by asay on 31.3.2015.
 */
public class MKSession {
    private int deviceId;
    private int sessionId;
    private Timestamp time;
    private DeviceType mDeviceType;

    /**
     * @return the kopterId
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * @param kopterId the kopterId to set
     */
    public void setDeviceId(int kopterId) {
        this.deviceId = kopterId;
    }

    /**
     * @return the KopterSessionId
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * @param KopterSessionId the KopterSessionId to set
     */
    public void setSessionId(int KopterSessionId) {
        this.sessionId = KopterSessionId;
    }

    /**
     * @return the time
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * @return the deviceType
     */
    public DeviceType getDeviceType() {
        return mDeviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(DeviceType deviceType) {
        this.mDeviceType = deviceType;
    }
}
