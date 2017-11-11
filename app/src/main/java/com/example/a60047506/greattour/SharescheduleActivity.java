package com.example.a60047506.greattour;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SharescheduleActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAMERA = 11;
    private static final int REQUEST_IMAGE_ALBUM = 12;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;
    private int serverResponseCode = 0;
    Context mContext;
    File tempFile;
    String tempPath;
    showImage task;
    getInfo info_task;
    ImageView imView;
    Bitmap bmImg;
    ListView listview;
    JSONArray json;
    ShareAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareschedule);

        mContext = getApplicationContext();

        //외장 메모리 접근 권한 요청
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }


        //info_task = new getInfo();


        //info_task.execute("http://13.125.37.8:52273/show/picture");



        imView = (ImageView) findViewById(R.id.imgView);

        listview =(ListView)findViewById(R.id.ListView);


        mMyAdapter = new ShareAdapter();

        /* 아이템 추가 및 어댑터 등록 */
        //dataSetting();


    }
    private void dataSetting(){

        int number = json.length();

        for(int i=0; i< number; i++)
        {
            try{

                String url = json.getJSONObject(i).getString("picture_url");
                //task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                task = new showImage();
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
                 //mMyAdapter.addItem(bmImg, "name_", "contents_" );
                //imView.setImageBitmap(bmImg);

                //bmImg= null;
            }
            catch(Exception e)
            {
                e.getMessage();
            }
        }
        imView.setImageBitmap(bmImg);
        // /mMyAdapter.addItem(bmImg, "name_", "contents_" );
       // for (int i=0; i<10; i++) {
       //     mMyAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.add), "name_" + i, "contents_" + i);
       // }

        /* 리스트뷰에 어댑터 등록 */
        listview.setAdapter(mMyAdapter);
    }

    public void onbtnCity(View v)
    {
        LayoutInflater layoutInflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cityView = layoutInflater.inflate(R.layout.citylayout, null);

        AlertDialog.Builder loginDialog =
                new AlertDialog.Builder(SharescheduleActivity.this);
        loginDialog.setTitle("어디로 가세요? 도시를 입력해주세요");
        // loginDialog.setMessage("");
        loginDialog.setView(cityView);
        loginDialog.show();
        //UploadFileToServer start_task = new UploadFileToServer();
        //start_task.execute();
    }
    public void onbtnSchedule(View v)
    {
        Intent intent = new Intent(SharescheduleActivity.this, ListViewActivity.class);
        startActivity(intent);
    }

    public void onBtnReview(View v)
    {
        Intent intent = new Intent(SharescheduleActivity.this, ReviewActivity.class);
        startActivity(intent);
    }



    public void moveDon(View v)
    {
        Intent intent = new Intent(SharescheduleActivity.this, DonActivity.class);
        startActivity(intent);

    }
    public void moveSchedule(View v)
    {

    }

    public void moveMypage(View v)
    {
        Intent intent = new Intent(SharescheduleActivity.this, MypageActivity.class);
        startActivity(intent);
    }

    public void moveFriend(View v)
    {
        Intent intent = new Intent(SharescheduleActivity.this, PlusfriendActivity.class);
        startActivity(intent);
    }

    private class getInfo extends AsyncTask<String,Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder output = new StringBuilder();

            HttpURLConnection con = null;
            BufferedReader reader = null;

            try {

                URL url = new URL(params[0]);
                JSONObject postDataParams = new JSONObject();

                con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStream stream = con.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                return buffer.toString();

            }
            catch (Exception e) {
                Log.v("log_err", e.getMessage());
                e.printStackTrace();
            }
            return output.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                json = new JSONArray(s);


            } catch (Exception e) {
                e.printStackTrace();
            }
            dataSetting();
            return;
        }

    }



    class showImage extends AsyncTask<String, Integer,Bitmap >{

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{

                URL myFileUrl = new URL("http://13.125.37.8:52273/upload_image/image_1510322740126_temp.jpg");
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);


            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap img){
            //imView.setImageBitmap(bmImg);
           Log.v("test", "test");
        }

    }

}
