package com.beldrone.asay.beldrone.View;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.beldrone.asay.beldrone.BusinessLayer.App;
import com.beldrone.asay.beldrone.BusinessLayer.CustomPhoneStateListener;
import com.beldrone.asay.beldrone.BusinessLayer.Enum.DeviceType;
import com.beldrone.asay.beldrone.BusinessLayer.Enum.OperationConfig;
import com.beldrone.asay.beldrone.BusinessLayer.IDeviceServerImpl;
import com.beldrone.asay.beldrone.BusinessLayer.Enum.ResultEnum;

import com.beldrone.asay.beldrone.BusinessLayer.JSONProvider;
import com.beldrone.asay.beldrone.BusinessLayer.MKSession;
import com.beldrone.asay.beldrone.BusinessLayer.SendService;
import com.beldrone.asay.beldrone.R;


import org.ligi.ufo.VesselData;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    AlarmManager alarmManager;
    IDeviceServerImpl ids;
    private ResponseReceiver receiver;
     TelephonyManager tManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

//test
        IntentFilter filter = new IntentFilter(ResponseReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);

        Intent iHeartBeatService = new Intent(this, SendService.class);
        PendingIntent piHeartBeatService = PendingIntent.getService(this, 0, iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(piHeartBeatService);
        //     alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), OperationConfig.serviceInterval, piHeartBeatService);

        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tManager.listen(new CustomPhoneStateListener(this),PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ids = new IDeviceServerImpl();

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String ret = ids.touchServer(androidId, DeviceType.KOPTER_DEVICE.getCode());
        JSONProvider<MKSession> js = new JSONProvider<MKSession>();
        OperationConfig.mkS = js.jsonToEntity(ret,MKSession.class);


        if (OperationConfig.mkS.getDeviceId() < 0) {
            ResultEnum e = ResultEnum.getStatus(OperationConfig.mkS.getDeviceId());
            Toast.makeText(getApplicationContext(), e.getDescription(), Toast.LENGTH_LONG).show();
            OperationConfig.mkS.setDeviceId(ids.registerDevice(androidId, getUsername(), DeviceType.KOPTER_DEVICE.getCode()));

        }
    }

    public void DoWork(View v) {
        switch (v.getId()) {

            case R.id.button5:
                Intent intent = new Intent(this, BluetoothActivity.class);

                startActivityForResult(intent, 2);


                break;

            case R.id.btnTest:


                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("Current : " + VesselData.battery.getCurrent() + "Voltage : " + VesselData.battery.getVoltage() + " " + App.getMK().getConnectionTime() + " " + "IN : " + App.getMK().stats.bytes_in + "Out : " + App.getMK().stats.bytes_out);


                Intent iHeartBeatService = new Intent(this, SendService.class);
                PendingIntent piHeartBeatService = PendingIntent.getService(this, 0, iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(piHeartBeatService);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), OperationConfig.serviceInterval, piHeartBeatService);


                break;
            case R.id.button6:

                iHeartBeatService = new Intent(this, SendService.class);
                piHeartBeatService = PendingIntent.getService(this, 0, iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(piHeartBeatService);


                break;

            default:

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_Bluetooth:

                Intent intent = new Intent(this, BluetoothActivity.class);

                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();


                break;
            default:
                break;


        }


        return super.onOptionsItemSelected(item);
    }


    public String getUsername() {

        AccountManager manager = AccountManager.get(getApplicationContext());
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }

    public class ResponseReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.beldrone.asay.beldrone.BusinessLayer.intent.action.PROCESS_RESPONSE";


        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                TextView result = (TextView) findViewById(R.id.textView);
                TextView result2 = (TextView) findViewById(R.id.textView2);

                String text = intent.getStringExtra(SendService.PARAM_OUT_MSG);
                String[] split = text.split(";");

                result.setText(split[0]);
                result2.setText(split[1]);



            }catch (Exception e){

            }


        }
    }
}
