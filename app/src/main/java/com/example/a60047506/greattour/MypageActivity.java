package com.example.a60047506.greattour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
    }

    public void moveDon(View v)
    {
        Intent intent = new Intent(MypageActivity.this, DonActivity.class);
        startActivity(intent);

    }
    public void moveSchedule(View v)
    {
        Intent intent = new Intent(MypageActivity.this, SharescheduleActivity.class);
        startActivity(intent);
    }

    public void moveMypage(View v)
    {

    }

    public void moveFriend(View v)
    {
        Intent intent = new Intent(MypageActivity.this, PlusfriendActivity.class);
        startActivity(intent);
    }

}
