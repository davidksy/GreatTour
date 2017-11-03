package com.example.a60047506.greattour;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public class PlusfriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plusfriend);
    }

    public void onBtnschedule(View v)
    {
        LayoutInflater layoutInflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cityView = layoutInflater.inflate(R.layout.calendarlayout, null);

        AlertDialog.Builder loginDialog =
                new AlertDialog.Builder(PlusfriendActivity.this);
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
    }

    public void onBtnId(View v)
    {
        LayoutInflater layoutInflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cityView = layoutInflater.inflate(R.layout.idlayout, null);

        AlertDialog.Builder loginDialog =
                new AlertDialog.Builder(PlusfriendActivity.this);

        loginDialog.setView(cityView);

        loginDialog.show();

    }

    public void moveDon(View v)
    {
        Intent intent = new Intent(PlusfriendActivity.this, DonActivity.class);
        startActivity(intent);

    }
    public void moveSchedule(View v)
    {
        Intent intent = new Intent(PlusfriendActivity.this, SharescheduleActivity.class);
        startActivity(intent);
    }

    public void moveMypage(View v)
    {
        Intent intent = new Intent(PlusfriendActivity.this, MypageActivity.class);
        startActivity(intent);
    }

    public void moveFriend(View v)
    {

    }


}
