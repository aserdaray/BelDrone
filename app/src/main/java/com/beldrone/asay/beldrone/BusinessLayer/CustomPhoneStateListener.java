package com.beldrone.asay.beldrone.BusinessLayer;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.util.Log;

/**
 * Created by asay on 9.3.2015.
 */
public class CustomPhoneStateListener extends PhoneStateListener {
    Context mContext;
    public static String LOG_TAG = "CustomPhoneStateListener";
    public static String GsmSignalStrength = "0";

    public CustomPhoneStateListener(Context context) {
        mContext = context;
    }


    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        try{
        Log.i(LOG_TAG, "onSignalStrengthsChanged: " + signalStrength);
        if (signalStrength.isGsm()) {
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getGsmBitErrorRate "
                    + signalStrength.getGsmBitErrorRate());
            Log.i(LOG_TAG, "onSignalStrengthsChanged: getGsmSignalStrength "
                    + signalStrength.getGsmSignalStrength());
            GsmSignalStrength = signalStrength.getGsmSignalStrength()+"";
        }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
