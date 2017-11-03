package com.example.a60047506.greattour;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.io.File;


public class SharescheduleActivity extends AppCompatActivity {


    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;
    private int serverResponseCode = 0;
    Context mContext;
    Uri fileUri;
    String selectPath;
    File albumFile;
    Intent chooseIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareschedule);

        mContext = getApplicationContext();
;
    }

    public void onBtnReview(View v)
    {


        LayoutInflater layoutInflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cityView = layoutInflater.inflate(R.layout.calendarlayout, null);

        AlertDialog.Builder loginDialog =
                new AlertDialog.Builder(SharescheduleActivity.this);
        loginDialog.setTitle("언제 가세요? 날짜를 선택해주세요");
        // loginDialog.setMessage("");
        loginDialog.setView(cityView);
        loginDialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //webView.loadUrl(USERLIST_URL);
            }
        });
        loginDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //webView.loadUrl(HOME_URL);
            }
        });
        loginDialog.show();

        /*
        final Intent gallaryIntent = new Intent();
        gallaryIntent.setType("image/*");
        gallaryIntent.setAction(Intent.ACTION_PICK);

        chooseIntent = Intent.createChooser(gallaryIntent, "이미지 선택");
        Log.v("start","chooseIntent");
        startActivityForResult(chooseIntent, PICK_FROM_ALBUM);
        */
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

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)  return;

        switch(requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                if(data == null)
                {
                    Toast.makeText(mContext, "선택된 사진이 없습니다.", Toast.LENGTH_SHORT);
                    return;
                }
                try
                {
                    if(resultCode == RESULT_OK)
                    {
                        fileUri = data.getData();
                        selectPath = fileUri.getPath();

                        //albumFile = new File(Environment.getExternalStorageDirectory().getPath());

                    }
                    else
                    {
                        Log.v("start","Environmen_error");

                    }


                }catch(Exception e)
                {
                    Log.e("Take_Albumn_Error",e.toString());
                }

               break;
            }

            case PICK_FROM_CAMERA:
            {

            }

            case CROP_FROM_CAMERA:
            {

            }
        }
    }


    private class UploadFileToServer extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {


            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
*/




}
