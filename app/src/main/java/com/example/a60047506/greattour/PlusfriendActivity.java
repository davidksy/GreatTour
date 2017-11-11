package com.example.a60047506.greattour;

import android.app.AlertDialog;
import android.content.Context;
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
        Intent intent = new Intent(PlusfriendActivity.this, AirCalendarDatePickerActivity.class);
        startActivity(intent);
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
