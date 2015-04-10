package com.beldrone.asay.beldrone.BusinessLayer;

import android.app.Application;

import org.ligi.ufo.MKCommunicator;

/**
 * Created by asay on 6.1.2015.
 */
public class App extends Application {

    static MKCommunicator mk = null;

    public static MKCommunicator getMK() {
        if (mk == null) {
            mk = new MKCommunicator();
        }

        return mk;
    }

    public static void disposeMK() {
        mk = null;
    }
}

