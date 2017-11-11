package com.example.a60047506.greattour;

/**
 * Created by 60047506 on 2017-11-06.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;



import java.util.ArrayList;

public class AirCalendarIntent  extends Intent implements Parcelable {

    public AirCalendarIntent() {
    }

    public AirCalendarIntent(Intent o) {
        super(o);
    }

    public AirCalendarIntent(String action) {
        super(action);
    }

    public AirCalendarIntent(String action, Uri uri) {
        super(action, uri);
    }

    public AirCalendarIntent(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
    }

    public AirCalendarIntent(Context packageContext) {
        super(packageContext, AirCalendarDatePickerActivity.class);
    }

    public void isSelect(boolean isSelect) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_SELECT, isSelect);
    }

    public void isBooking(boolean isBooking) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_BOOIKNG, isBooking);
    }

    public void isMonthLabels(boolean isLabel) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_IS_MONTH_LABEL, isLabel);
    }

    public void setBookingDateArray(ArrayList<String> arrays) {
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_BOOKING_DATES, arrays);
    }

    public void setStartDate(int year , int month , int day){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SY, year);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SM, month);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_SD, day);

    }

    public void setEndDate(int year , int month , int day){
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_EY, year);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_EM, month);
        this.putExtra(AirCalendarDatePickerActivity.EXTRA_SELECT_DATE_ED, day);
    }
}