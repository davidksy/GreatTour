package com.example.a60047506.greattour;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.List;


public class DonActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CLIENT_ID = "T9kMIFxpZgqlsq39gWqY"; // "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요./ private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;
    private TextView txtResult;
    private Button btnStart;
    private String mResult;
    private AudioWriterPCM writer;
    private RecognitionHandler handler;

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                txtResult.setText("Connected");
                writer = new AudioWriterPCM(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;

            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;

            case R.id.partialResult:
                // Extract obj property typed with String.
                mResult = (String) (msg.obj);
                txtResult.setText(mResult);
                break;

            case R.id.finalResult:
                // Extract obj property typed with String array.
                // The first element is recognition result for speech.
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                List<String> results = speechRecognitionResult.getResults();
                StringBuilder strBuf = new StringBuilder();
                strBuf.append(results.get(0));

                /*
                for(String result : results) {
                    strBuf.append(result);
                    strBuf.append("\n");
                }
                */
                mResult = strBuf.toString();
                txtResult.setText(mResult);
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                txtResult.setText(mResult);
                btnStart.setText(R.string.str_start);
                btnStart.setEnabled(true);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

                btnStart.setText(R.string.str_start);
                btnStart.setEnabled(true);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don);

        txtResult = (TextView) findViewById(R.id.detail_info_txt);
        btnStart = (Button) findViewById(R.id.saydetail);

        handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(this, handler, CLIENT_ID);


        }
    public void ChooseDay(View v){

        LayoutInflater layoutInflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cityView = layoutInflater.inflate(R.layout.calendarlayout, null);

        AlertDialog.Builder loginDialog =
                new AlertDialog.Builder(DonActivity.this);
        loginDialog.setTitle("언제에요? 날짜를 선택해주세요");
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

    public void ChooseCategory(View v){

        LayoutInflater layoutInflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cityView = layoutInflater.inflate(R.layout.categorylayout, null);

        AlertDialog.Builder loginDialog =
                new AlertDialog.Builder(DonActivity.this);
        loginDialog.setTitle("어떤거에요? 항목을 선택해주세요");
        // loginDialog.setMessage("");
        loginDialog.setView(cityView);

        loginDialog.show();

    }

    public void Saydetail(View v)
    {
        if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
            // Start button is pushed when SpeechRecognizer's state is inactive.
            // Run SpeechRecongizer by calling recognize().
            mResult = "";
            txtResult.setText("Connecting...");
            btnStart.setText(R.string.str_stop);
            naverRecognizer.recognize();
        } else {
            Log.d(TAG, "stop and wait Final Result");
            btnStart.setEnabled(false);

            naverRecognizer.getSpeechRecognizer().stop();
        }
        //return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // NOTE : initialize() must be called on start time.
        naverRecognizer.getSpeechRecognizer().initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mResult = "";
        txtResult.setText("");
        btnStart.setText(R.string.str_start);
        btnStart.setEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }


    static class RecognitionHandler extends Handler {
        private final WeakReference<DonActivity> mActivity;

        RecognitionHandler(DonActivity activity) {
            mActivity = new WeakReference<DonActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DonActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }


    public void moveDon(View v)
    {



    }
    public void moveSchedule(View v)
    {
        Intent intent = new Intent(DonActivity.this, SharescheduleActivity.class);
        startActivity(intent);
    }

    public void moveMypage(View v)
    {
        Intent intent = new Intent(DonActivity.this, MypageActivity.class);
        startActivity(intent);
    }

    public void moveFriend(View v)
    {
        Intent intent = new Intent(DonActivity.this, PlusfriendActivity.class);
        startActivity(intent);
    }
}
