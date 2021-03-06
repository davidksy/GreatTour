package com.example.a60047506.greattour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AirCalendarDatePickerActivity extends AppCompatActivity implements DatePickerController  {

    public final static String EXTRA_FLAG = "FLAG";
    public final static String EXTRA_IS_BOOIKNG = "IS_BOOING";
    public final static String EXTRA_IS_SELECT = "IS_SELECT";
    public final static String EXTRA_BOOKING_DATES = "BOOKING_DATES";
    public final static String EXTRA_SELECT_DATE_SY = "SELECT_START_DATE_Y";
    public final static String EXTRA_SELECT_DATE_SM = "SELECT_START_DATE_M";
    public final static String EXTRA_SELECT_DATE_SD = "SELECT_START_DATE_D";
    public final static String EXTRA_SELECT_DATE_EY = "SELECT_END_DATE_Y";
    public final static String EXTRA_SELECT_DATE_EM = "SELECT_END_DATE_M";
    public final static String EXTRA_SELECT_DATE_ED = "SELECT_END_DATE_D";
    public final static String EXTRA_IS_MONTH_LABEL = "IS_MONTH_LABEL";

    public final static String RESULT_SELECT_START_DATE = "start_date";
    public final static String RESULT_SELECT_END_DATE = "end_date";
    public final static String RESULT_SELECT_START_VIEW_DATE = "start_date_view";
    public final static String RESULT_SELECT_END_VIEW_DATE = "end_date_view";
    public final static String RESULT_FLAG = "flag";
    public final static String RESULT_TYPE = "result_type";
    public final static String RESULT_STATE = "result_state";

    public DayPickerView pickerView;
    public TextView tv_start_date;
    public TextView tv_end_date;
    public TextView tv_popup_msg;
    public RelativeLayout rl_done_btn;
    public RelativeLayout rl_reset_btn;
    public RelativeLayout rl_popup_select_checkout_info_ok;
    public RelativeLayout rl_checkout_select_info_popup;
    public RelativeLayout rl_iv_back_btn_bg;

    public String SELECT_START_DATE = "";
    public String SELECT_END_DATE = "";
    public int YEAR = 2017;

    public String FLAG = "all";
    public boolean isSelect = false;
    public boolean isBooking = false;
    public boolean isMonthLabel = false;
    public ArrayList<String> dates;
    public selectDateModel selectDate;

    public int sYear = 0;
    public int sMonth = 0;
    public int sDay = 0;
    public int eYear = 0;
    public int eMonth = 0;
    public int eDay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_calendar_date_picker);

        Intent getData = getIntent();
        FLAG = getData.getStringExtra(EXTRA_FLAG) != null ? getData.getStringExtra(EXTRA_FLAG):"all";
        isBooking = getData.getBooleanExtra(EXTRA_IS_BOOIKNG , false);
        isSelect = getData.getBooleanExtra(EXTRA_IS_SELECT , false);
        isMonthLabel = getData.getBooleanExtra(EXTRA_IS_MONTH_LABEL , false);
        dates = getData.getStringArrayListExtra(EXTRA_BOOKING_DATES);

        sYear = getData.getIntExtra(EXTRA_SELECT_DATE_SY , 0);
        sMonth = getData.getIntExtra(EXTRA_SELECT_DATE_SM , 0);
        sDay = getData.getIntExtra(EXTRA_SELECT_DATE_SD , 0);

        eYear = getData.getIntExtra(EXTRA_SELECT_DATE_EY , 0);
        eMonth = getData.getIntExtra(EXTRA_SELECT_DATE_EM , 0);
        eDay = getData.getIntExtra(EXTRA_SELECT_DATE_ED , 0);


        if(sYear == 0 || sMonth == 0 || sDay == 0
                || eYear == 0 || eMonth == 0 || eDay == 0){
            selectDate = new selectDateModel();
            isSelect = false;
        }


        init();

    }

    private void init(){

        rl_done_btn = (RelativeLayout)findViewById(R.id.rl_done_btn);
        tv_start_date = (TextView)findViewById(R.id.tv_start_date);
        tv_end_date = (TextView)findViewById(R.id.tv_end_date);
        tv_popup_msg = (TextView)findViewById(R.id.tv_popup_msg);
        rl_checkout_select_info_popup = (RelativeLayout)findViewById(R.id.rl_checkout_select_info_popup);
        rl_reset_btn = (RelativeLayout)findViewById(R.id.rl_reset_btn);
        rl_popup_select_checkout_info_ok =(RelativeLayout) findViewById(R.id.rl_popup_select_checkout_info_ok);
        rl_checkout_select_info_popup = (RelativeLayout)findViewById(R.id.rl_checkout_select_info_popup);
        rl_iv_back_btn_bg = (RelativeLayout)findViewById(R.id.rl_iv_back_btn_bg);


        pickerView = (DayPickerView)findViewById(R.id.pickerView);
        pickerView.setIsMonthDayLabel(isMonthLabel);

        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy", Locale.KOREA );
        Date currentTime = new Date ( );
        String dTime = formatter.format ( currentTime );
        YEAR = Integer.valueOf(dTime) + 1;

        if(dates != null && dates.size() != 0 && isBooking){
            pickerView.setShowBooking(true);
            pickerView.setBookingDateArray(dates);
        }

        if(isSelect){
            selectDate = new selectDateModel();
            pickerView.setSelected(true);
            selectDate.setFristYear(sYear);
            selectDate.setFristMonth(sMonth);
            selectDate.setFristDay(sDay);
            selectDate.setLastYear(eYear);
            selectDate.setLastMonth(eMonth);
            selectDate.setLastDay(eDay);

            pickerView.setSelected(selectDate);
        }

        pickerView.setController(this);


        rl_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((SELECT_START_DATE == null || SELECT_START_DATE.equals("")) && (SELECT_END_DATE == null || SELECT_END_DATE.equals(""))){
                    SELECT_START_DATE = "";
                    SELECT_END_DATE = "";
                }else{
                    if(SELECT_START_DATE == null || SELECT_START_DATE.equals("")){
                        tv_popup_msg.setText("Please select all dates.");
                        rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                        return;
                    }else if(SELECT_END_DATE == null || SELECT_END_DATE.equals("")){
                        tv_popup_msg.setText("Please select all dates.");
                        rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                        return;
                    }
                }


                Intent resultIntent = new Intent();
                resultIntent.putExtra(RESULT_SELECT_START_DATE , SELECT_START_DATE );
                resultIntent.putExtra(RESULT_SELECT_END_DATE , SELECT_END_DATE );
                resultIntent.putExtra(RESULT_SELECT_START_VIEW_DATE , tv_start_date.getText().toString() );
                resultIntent.putExtra(RESULT_SELECT_END_VIEW_DATE , tv_end_date.getText().toString() );
                resultIntent.putExtra(RESULT_FLAG , FLAG );
                resultIntent.putExtra(RESULT_TYPE , FLAG );
                resultIntent.putExtra(RESULT_STATE , "done" );
                setResult(RESULT_OK , resultIntent);
                finish();
            }
        });

        rl_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECT_START_DATE = "";
                SELECT_END_DATE = "";
                setContentView(R.layout.activity_air_calendar_date_picker);
                init();
            }
        });

        rl_popup_select_checkout_info_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_checkout_select_info_popup.setVisibility(View.GONE);
            }
        });

        rl_iv_back_btn_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getMaxYear() {
        return YEAR;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        // TODO Single Select Event
        try{
            String start_month_str =  String.format("%02d" , (month+1));
            // 일
            String start_day_str =  String.format("%02d" , day);
            String startSetDate = year+start_month_str+start_day_str;

            String startDateDay = AirCalendarUtils.getDateDay(startSetDate , "yyyyMMdd");

            tv_start_date.setText(year+"-"+start_month_str+"-"+start_day_str + " " + startDateDay);
            tv_start_date.setTextColor(0xff4a4a4a);

            tv_end_date.setText("-");
            tv_end_date.setTextColor(0xff1abc9c);
            SELECT_END_DATE = "";
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void DaySave(View v)
    {
        String temp1= tv_start_date.getText().toString();
        String temp2= tv_end_date.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("Start_Day", temp1);
        intent.putExtra("End_day", temp2);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

        try{
            Calendar cl = Calendar.getInstance();

            cl.setTimeInMillis(selectedDays.getFirst().getDate().getTime());

            // 월
            int start_month_int = (cl.get(Calendar.MONTH)+1);
            String start_month_str =  String.format("%02d" , start_month_int);

            // 일
            int start_day_int = cl.get(Calendar.DAY_OF_MONTH);
            String start_day_str =  String.format("%02d" , start_day_int);

            String startSetDate = cl.get(Calendar.YEAR)+start_month_str+start_day_str;
            String startDateDay = AirCalendarUtils.getDateDay(startSetDate , "yyyyMMdd");
            String startDate = cl.get(Calendar.YEAR) + "-" + start_month_str + "-" + start_day_str;

            cl.setTimeInMillis(selectedDays.getLast().getDate().getTime());

            // 월
            int end_month_int = (cl.get(Calendar.MONTH)+1);
            String end_month_str = String.format("%02d" , end_month_int);

            // 일
            int end_day_int = cl.get(Calendar.DAY_OF_MONTH);
            String end_day_str = String.format("%02d" , end_day_int);

            String endSetDate = cl.get(Calendar.YEAR)+end_month_str+end_day_str;
            String endDateDay = AirCalendarUtils.getDateDay(endSetDate , "yyyyMMdd");
            String endDate = cl.get(Calendar.YEAR) + "-" + end_month_str + "-" + end_day_str;

            tv_start_date.setText(startDate + " " + startDateDay);
            tv_start_date.setTextColor(0xff4a4a4a);

            tv_end_date.setText(endDate + " " + endDateDay);
            tv_end_date.setTextColor(0xff4a4a4a);

            SELECT_START_DATE = startDate;
            SELECT_END_DATE = endDate;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
