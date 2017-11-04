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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    ImageView imView;
    Bitmap bmImg;

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

        task = new showImage();

        imView = (ImageView) findViewById(R.id.imgView);

        task.execute("http://13.125.37.8:52273/upload_image/image_1509780302187_temp.jpg");
        


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




    private class showImage extends AsyncTask<String, Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
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
            imView.setImageBitmap(bmImg);
        }

    }

}
