package com.example.a60047506.greattour;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.example.a60047506.greattour.R.id.pay_number;


public class DonActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CLIENT_ID = "T9kMIFxpZgqlsq39gWqY"; // "내 애플리케이션"에서 Client ID를 확인해서 이곳에 적어주세요./ private RecognitionHandler handler;
    private NaverRecognizer naverRecognizer;
    private EditText txtResult;
    private Button btnStart;
    private String mResult;
    private AudioWriterPCM writer;
    private RecognitionHandler handler;
    private TextView category_view;
    private TextView day_text;
    private String day_string, str_Qtype;
    int Edit_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don);

        handler = new RecognitionHandler(this);
        naverRecognizer = new NaverRecognizer(this, handler, CLIENT_ID);

        txtResult = (EditText) findViewById(R.id.detail_info_edit);
        btnStart = (Button) findViewById(R.id.saydetail);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO}, 1);
        }
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


    private void handleMessage(Message msg) {


        if(Edit_id== R.id.saydetail)
        {
            txtResult= (EditText) findViewById(R.id.detail_info_edit);
            btnStart= (Button)findViewById(R.id.saydetail);
        }
        else if(Edit_id == R.id.pay_info)
        {
            txtResult= (EditText)findViewById(pay_number);
            btnStart= (Button)findViewById(R.id.pay_info);
        }

        switch (msg.what) {
            case R.id.clientReady:
                // Now an user can speak.
                txtResult.setText("정보를 말씀해주세요");
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


                mResult = strBuf.toString();
                txtResult.setText(mResult);
                break;

            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }

                mResult = "Error code : " + msg.obj.toString();
                txtResult.setText(mResult);
                //btnStart.setText(R.string.str_start);
                //btnStart.setEnabled(true);
                break;

            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }

               // btnStart.setText(R.string.str_start);
                //btnStart.setEnabled(true);
                break;
        }
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
        //btnStart.setText(R.string.str_start);
        //btnStart.setEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // NOTE : release() must be called on stop time.
        naverRecognizer.getSpeechRecognizer().release();
    }

    public void ChooseDay(View v){

        Intent intent = new Intent(DonActivity.this, AirCalendarDatePickerActivity.class);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String start_day= data.getStringExtra("Start_Day");
                    String end_day= data.getStringExtra("End_day");
                    day_string= start_day+"-"+end_day;
                    day_text = (TextView)findViewById(R.id.daytxt);
                    day_text.setText(day_string);

            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void ChooseCategory(View v){

        LayoutInflater layoutInflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View categoryView = layoutInflater.inflate(R.layout.categorylayout, null);

        AlertDialog.Builder loginDialog =
                new AlertDialog.Builder(DonActivity.this);
        loginDialog.setTitle("어떤거에요? 항목을 선택해주세요");
        // loginDialog.setMessage("");
        loginDialog.setView(categoryView);
        loginDialog.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RadioGroup rg= (RadioGroup)categoryView.findViewById(R.id.radioGroup1);

                RadioButton rd= (RadioButton)categoryView.findViewById(rg.getCheckedRadioButtonId());

                str_Qtype = rd.getText().toString();

                Toast.makeText(getApplicationContext(), str_Qtype+" 선택됨", Toast.LENGTH_SHORT).show();

                category_view = (TextView)findViewById(R.id.category_text);

                category_view.setText(str_Qtype);
            }
        });

        loginDialog.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        loginDialog.show();

    }

    public void Save(View v)
    {
        LayoutInflater layoutInflater =
                (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View categoryView = layoutInflater.inflate(R.layout.categorylayout, null);

        RadioGroup rg= (RadioGroup)categoryView.findViewById(R.id.radioGroup1);

        RadioButton rd= (RadioButton)categoryView.findViewById(rg.getCheckedRadioButtonId());

        String str_Qtype = rd.getText().toString();

        Toast.makeText(getApplicationContext(), str_Qtype+" 선택됨", Toast.LENGTH_SHORT).show();

        finish();


    }


    public void Saypay(View v)
    {
        Edit_id = ((Button)v).getId();
        btnStart= (Button)findViewById(R.id.pay_info);
        if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
            // Start button is pushed when SpeechRecognizer's state is inactive.
            // Run SpeechRecongizer by calling recognize().
            mResult = "";
            naverRecognizer.recognize();
        } else {
            Log.d(TAG, "stop and wait Final Result");
            //btnStart.setEnabled(false);

            naverRecognizer.getSpeechRecognizer().stop();
        }

    }

    public void Saydetail(View v)
    {
        Edit_id = ((Button)v).getId();

        btnStart= (Button)findViewById(R.id.saydetail);

        if(!naverRecognizer.getSpeechRecognizer().isRunning()) {
                // Start button is pushed when SpeechRecognizer's state is inactive.
                // Run SpeechRecongizer by calling recognize().
            mResult = "";
            naverRecognizer.recognize();
        } else {
            Log.d(TAG, "stop and wait Final Result");
            //btnStart.setEnabled(false);

            naverRecognizer.getSpeechRecognizer().stop();
        }

    }


    public void BtnSave(View v)
    {

        String user_id="kim";

        EditText detail_text= (EditText) findViewById(R.id.detail_info_edit);

        String detail = detail_text.getText().toString();

        EditText pay_text= (EditText) findViewById(R.id.pay_number);

        String pay_number= pay_text.getText().toString();

        String[] param= {user_id, day_string, str_Qtype, detail, pay_number};

        DondataUpload dataUpload;

        dataUpload = new DondataUpload();

        dataUpload.Data(param);

        Intent intent = new Intent(DonActivity.this, ListDonActivity.class);
        startActivity(intent);

        //String pay_string = (String)findViewById(R.id.detail_info_edit).toString();

        //String Don_number = pay_number.replaceAll("[^0-9]", "");
        //String Don_string = pay_number.replaceAll("[0-9]", "") ;


    }


    public void ShowDon(View v)
    {
        Intent intent = new Intent(DonActivity.this, ListDonActivity.class);
        startActivity(intent);
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
