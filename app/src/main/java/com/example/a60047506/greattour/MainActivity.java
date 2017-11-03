package com.example.a60047506.greattour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onBtnlogin(View v)
    {
        Intent intent = new Intent(MainActivity.this, LoginidpwActivity.class);
        startActivity(intent);
        finish();
    }
    public void onBtnReg(View v)
    {
        Intent intent = new Intent(MainActivity.this, PlusfriendActivity.class);
        startActivity(intent);
    }
}
