package com.beldrone.asay.beldrone.BusinessLayer.Enum;

import com.beldrone.asay.beldrone.BusinessLayer.MKSession;

/**
 * Created by asay on 24.12.2014.
 */
public final class OperationConfig {
    public static String host = "mk-onurerden.rhcloud.com";
    public static MKSession mkS ;
    public static int serviceInterval = 1500;
    public static boolean isTrying = false;

    public static enum JsonActionList {
        touchServer("/mkWS/TouchServer"),
        registerDevice("/mkWS/RegisterDevice"),
        sendStatus("/mkWS/SendStatus"),
        getTask("/mkWS/GetTask"),
        sendFollowMeData("/mkWS/SendFollowMeData"),
        getKopterStatus("/mkWS/GetKopterStatus"),
        getRegisteredData("mkWS/GetRegisteredData");

        private final String text;



        private JsonActionList(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public OperationConfig() {
        mkS = new MKSession();
    }
}
