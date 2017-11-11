package com.example.a60047506.greattour;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by 60047506 on 2017-11-10.
 */

public class DondataUpload {
    public void Data(String[] params)
    {
        new dataUpload().execute(
                "http://13.125.37.8:52273/payment",
                params[0], params[1], params[2], params[3], params[4]);
    }

}

class dataUpload extends AsyncTask<String,String,String> {

    @Override
    protected String doInBackground(String... params) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("user_id", params[1]);
            postDataParams.put("day_info", params[2]);
            postDataParams.put("category", params[3]);
            postDataParams.put("pay", params[4]);
            postDataParams.put("detail_info", params[5]);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true); conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line = null;
                while(true) {
                    line = reader.readLine();
                    if (line == null) break;
                    output.append(line);
                }
                reader.close();
                conn.disconnect();
            }
        } catch (Exception e) { e.printStackTrace(); }
        return output.toString();
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        Log.i("result json", s);
    }
}
