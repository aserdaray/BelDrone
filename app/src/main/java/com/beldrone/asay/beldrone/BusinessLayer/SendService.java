package com.beldrone.asay.beldrone.BusinessLayer;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;


import com.beldrone.asay.beldrone.BusinessLayer.Enum.OperationConfig;
import com.beldrone.asay.beldrone.BusinessLayer.Enum.ResultEnum;
import com.beldrone.asay.beldrone.BusinessLayer.Enum.TaskEvent;
import com.beldrone.asay.beldrone.Model.FollowMe;
import com.beldrone.asay.beldrone.Model.Status;
import com.beldrone.asay.beldrone.View.MainActivity;

import org.ligi.ufo.BlCalculations;
import org.ligi.ufo.VesselData;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

public class SendService extends IntentService {
    public static final String PARAM_IN_MSG = "imsg";
    public static String PARAM_OUT_MSG = "omsg";


    public SendService() {
        super("SendService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
     /*   try {
            this.beat.run();
        }catch (Exception e){

        } */
try{

        String resultTxt;

        DecimalFormat df = new DecimalFormat("#.##");
        String ntRx = df.format(TrafficStats.getUidRxBytes(getApplicationInfo().uid) / 1024d);
        String ntTx = df.format(TrafficStats.getUidTxBytes(getApplicationInfo().uid) / 1024d);
        String btRx = df.format(App.getMK().stats.bytes_in / 1024d);
        String btTx = df.format(App.getMK().stats.bytes_out / 1024d);


        resultTxt = "BT IN (kB): " + btRx + " BT OUT (kB): " + btTx +
                " ; NT IN (kB): " + ntRx + " NT OUT (kB): " + ntTx;

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && App.getMK().isConnected()) {

            int x = new Random().nextInt(100);

            Long time = 0L;
            System.out.println(x + "Stat Başladı : " + System.currentTimeMillis() + " ms");
            time = System.currentTimeMillis();

try{


            IDeviceServerImpl ids = new IDeviceServerImpl();
            String task = ids.getTask(OperationConfig.mkS.getDeviceId());

            JSONProvider<FollowMe> jsp = new JSONProvider<FollowMe>();
            FollowMe followMe = jsp.jsonToEntity(task, FollowMe.class);


            TaskEvent e = TaskEvent.getStatus(followMe.getEvent());
            System.out.println("Sonuc : " + e.getLabel());
            switch (e) {
                case FOLLOW_ME:

                    App.getMK().send_follow_me((byte) 60, followMe.getLat(), followMe.getLon());
                    System.out.println("Sonuc : " + App.getMK().gps_position.Latitude_str() + " - " + App.getMK().gps_position.Longitude_str());

                    break;
                case LOOK_AT_ME:

                    double currLat = Double.parseDouble(App.getMK().gps_position.Latitude_str());
                    double currLong = Double.parseDouble(App.getMK().gps_position.Longitude_str());

                    BlCalculations calc = new BlCalculations();
                    double bearing = calc.getBearing(followMe.getLat(), followMe.getLon(), currLat, currLong);

                    App.getMK().send_follow_me((byte) 60, bearing);

                    break;
                case COME_HOME:

                    double homeLat = Double.parseDouble(App.getMK().gps_position.HomeLatitude_str());
                    double homeLon = Double.parseDouble(App.getMK().gps_position.HomeLongitude_str());

                    App.getMK().send_follow_me((byte) 60, homeLat, homeLon);


                    break;
                default:


            }
}catch(Exception ex){

}
            System.out.println("Connection Time : " + App.getMK().getConnectionTime() + " bytes_in : " + App.getMK().stats.bytes_in + " bytes_out : " + App.getMK().stats.bytes_out);

            Status s = new Status();
            s.setKopterId(OperationConfig.mkS.getDeviceId() + "");
            s.setKopterAltitude(App.getMK().getAlt() + "");
            s.setKopterHeading(App.getMK().stats.last_heading + "");
            s.setKopterLatitude(App.getMK().gps_position.Latitude_str());
            s.setKopterLongitude(App.getMK().gps_position.Longitude_str());
            s.sessionId = OperationConfig.mkS.getSessionId();
            s.updateTime = new Timestamp(new Date().getTime());


            s.setGsmSignalStrength(CustomPhoneStateListener.GsmSignalStrength);

          //s.setKopterRcSignal();
            s.setKopterSpeed(App.getMK().stats.avg_speed() + "");
            s.setKopterVoltage(VesselData.battery.getVoltage() + "");
            s.setBatteryCurrent(VesselData.battery.getCurrent() + "");
            s.setBatteryCapacity(VesselData.battery.getUsedCapacity() + "");
            s.setKopterVario(String.valueOf(App.getMK().gps_position.Variometer));

            s.setFlagsNC(Integer.toBinaryString(App.getMK().gps_position.NCFlags).toString());
            s.setFlagsStatusFC1(Integer.toBinaryString(App.getMK().gps_position.FCFlags).toString());
            s.setFlagsStatusFC2(Integer.toBinaryString(App.getMK().gps_position.FCFlags2).toString());

            JSONProvider<Status> jsp1 = new JSONProvider<Status>();
            String s1 = jsp1.entityToJson(s);

            IDeviceServerImpl ids1 = new IDeviceServerImpl();
            int i = ids1.sendStatus(s1);


            ResultEnum e1 = ResultEnum.getStatus(i);
            System.out.println("Sonuc : " + i + "=>" + e1.getLabel());

            System.out.println(x + "Stat Bitti  : " + (System.currentTimeMillis()) + " ms");
            System.out.println(x + "Stat Sürdü  : " + (System.currentTimeMillis() - time) + " ms");

            OperationConfig.serviceInterval = Integer.parseInt((System.currentTimeMillis() - time) + "");

        }else{
           resultTxt = "Bağlantı YOK! ; -";
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MainActivity.ResponseReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
        sendBroadcast(broadcastIntent);


        this.stopSelf();

}catch (Exception ex){
this.stopSelf();
    this.onDestroy();
}
    }

    public Runnable beat = new Runnable() {

        public synchronized void run() {

        }
    };
}