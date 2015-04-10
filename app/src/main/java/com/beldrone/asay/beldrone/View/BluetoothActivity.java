package com.beldrone.asay.beldrone.View;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.beldrone.asay.beldrone.BusinessLayer.App;
import com.beldrone.asay.beldrone.BusinessLayer.BluetoothCommunicationAdapter;
import com.beldrone.asay.beldrone.BusinessLayer.ConnectionStatusAlertDialog;
import com.beldrone.asay.beldrone.R;

import org.ligi.tracedroid.logging.Log;

import java.util.Set;

public class BluetoothActivity extends ActionBarActivity {

    public static final int INTENT_REQUEST_CODE_BT_CONN = 2;
    private static final int REQUEST_ENABLE_BT = 1;
    private Button onBtn;
    private Button offBtn;
    private Button listBtn;
    private Button findBtn;
    private TextView text;
    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ListView myListView;
    private ArrayAdapter<String> BTArrayAdapter;
    private BluetoothDevice currDevice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        // take an instance of BluetoothAdapter - Bluetooth radio
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (myBluetoothAdapter == null) {


            Toast.makeText(getApplicationContext(), "Your device does not support Bluetooth", Toast.LENGTH_LONG).show();
        } else {
            text = (TextView) findViewById(R.id.text);
            onBtn = (Button) findViewById(R.id.turnOn);
            onBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    on(v);

                }
            });

            offBtn = (Button) findViewById(R.id.turnOff);
            offBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    off(v);
                }
            });

            listBtn = (Button) findViewById(R.id.paired);
            listBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    list(v);
                }
            });

            findBtn = (Button) findViewById(R.id.search);
            findBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    find(v);
                }
            });

            myListView = (ListView) findViewById(R.id.listView1);


            // create the arrayAdapter that contains the BTDevices, and set it to the ListView
            BTArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

            myListView.setAdapter(BTArrayAdapter);


            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                    String s = parent.getItemAtPosition(position).toString();
                    String[] split = s.split("\n");
                    connectToMk(split[0],split[1]);

                }
            });
        }
    }

    private void connectToMk(String address,String name){

        BluetoothCommunicationAdapter bt_com = new BluetoothCommunicationAdapter(address);
        App.getMK().setCommunicationAdapter(bt_com);
        App.getMK().connect_to("btspp://" + address,name);

        ConnectionStatusAlertDialog.show(this);
    }

    private void saveKoptertoPref(){

        if (App.getMK().isConnected()){
            SharedPreferences myPref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor edit = myPref.edit();

            edit.putBoolean("isMkSet", true);
            edit.putString("mkAddress", currDevice.getAddress());
            edit.putString("mkName", currDevice.getName());
            edit.commit();
         //   edit.apply();
        }

    }

    private void on(View v) {
        if (!myBluetoothAdapter.isEnabled()) {
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
            text.setText("Status: Enabled");
            Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on", Toast.LENGTH_LONG).show();
        }
    }

    private void off(View v) {
        myBluetoothAdapter.disable();
        text.setText("Status: Disconnected");
        Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_LONG).show();

    }

    private void list(View v) {
        BTArrayAdapter.clear();
        // get paired devices
        pairedDevices = myBluetoothAdapter.getBondedDevices();

        // put it's one to the adapter
        for (BluetoothDevice device : pairedDevices)
            BTArrayAdapter.add(device.getAddress()+"\n"+device.getName());

        Toast.makeText(getApplicationContext(), "Show Paired Devices", Toast.LENGTH_SHORT).show();
    }

    private void find(View v) {

        if (myBluetoothAdapter.isDiscovering()) {
            // the button is pressed when it discovers, so cancel the discovery
            myBluetoothAdapter.cancelDiscovery();
            text.setText("Status: Cancelled.");
        } else {
            BTArrayAdapter.clear();
            text.setText("Status: Discovering...");
            myBluetoothAdapter.startDiscovery();
            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }


    }

    final BroadcastReceiver bReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // add the name and the MAC address of the object to the arrayAdapter
                BTArrayAdapter.add(device.getAddress()+"\n"+device.getName());
                BTArrayAdapter.notifyDataSetChanged();
                text.setText("Status: Found...");
            }
        }


    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {

            case REQUEST_ENABLE_BT:
                text.setText("Status: Enabled");
                break;
            default:
                text.setText("Status: Disabled");
                Log.w("unknown code in onActivityResult code=" + requestCode);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bluetooth, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
