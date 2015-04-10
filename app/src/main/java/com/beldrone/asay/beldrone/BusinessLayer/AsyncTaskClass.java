package com.beldrone.asay.beldrone.BusinessLayer;

import android.os.AsyncTask;


import com.beldrone.asay.beldrone.BusinessLayer.Enum.OperationConfig;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;

import java.net.URI;

/**
 * Created by asay on 24.12.2014.
 */
public class AsyncTaskClass extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        //uzun islem oncesi yapilacaklar
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        System.out.println(values);
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            URI uri = URIUtils.createURI("http", OperationConfig.host, -1, strings[0], strings[1], null);
            HttpGet httpget = new HttpGet(uri);
//            System.out.println(httpget.getURI());


            IDeviceServerImpl ids = new IDeviceServerImpl();
            ids.setWebServiceUrl(httpget.getURI().toString());
            String send = ids.send();
            send = send.replace("\n", "").replace("\r", "");

            return send;
        } catch (Exception e) {

            return "";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        //uzun islem bitince yapilacaklar
    }
}
