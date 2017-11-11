package com.example.a60047506.greattour;

/**
 * Created by 60047506 on 2017-11-06.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;

public class DayPickerView extends RecyclerView {
    public Context mContext;
    public SimpleMonthAdapter mAdapter;
    public DatePickerController mController;
    public int mCurrentScrollState = 0;
    public long mPreviousScrollPosition;
    public int mPreviousScrollState = 0;
    public TypedArray typedArray;
    public OnScrollListener onScrollListener;
    public boolean isBooking = false;
    public boolean isMonthDayLabels = false;
    public ArrayList<String> mBookingDates;
    public selectDateModel mSelectDateModel = null;

    public DayPickerView(Context context)
    {
        this(context, null);
    }

    public DayPickerView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public DayPickerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        if (!isInEditMode())
        {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.DayPickerView);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            init(context);
        }
    }

    public void setController(DatePickerController mController)
    {
        this.mController = mController;
        setUpAdapter();
        setAdapter(mAdapter);
    }

    public void setShowBooking(boolean isbooking){
        this.isBooking = isbooking;
    }

    public void setBookingDateArray(ArrayList<String> dates){this.mBookingDates = dates;}

    public void setSelected(selectDateModel date){
        this.mSelectDateModel = date;
    }

    public void setIsMonthDayLabel(boolean isLabel) { this.isMonthDayLabels = isLabel; }

    public void setMonthDayLabels(boolean monthDayLabels){ this.isMonthDayLabels = monthDayLabels; }

    public void init(Context paramContext) {
        setLayoutManager(new LinearLayoutManager(paramContext));
        mContext = paramContext;
        setUpListView();

        onScrollListener = new OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                final SimpleMonthView child = (SimpleMonthView) recyclerView.getChildAt(0);
                if (child == null) {
                    return;
                }

                mPreviousScrollPosition = dy;
                mPreviousScrollState = mCurrentScrollState;
            }
        };
    }


    public void setUpAdapter() {
        if (mAdapter == null) {
            mAdapter = new SimpleMonthAdapter(getContext(), mController, typedArray , isBooking , isMonthDayLabels , mBookingDates , mSelectDateModel);
        }
        mAdapter.notifyDataSetChanged();
    }


    public void setUpListView() {
        setVerticalScrollBarEnabled(false);
        setOnScrollListener(onScrollListener);
        setFadingEdgeLength(0);
    }

    public SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> getSelectedDays() { return mAdapter.getSelectedDays();}

    public ArrayList<String> getBookingDates(){ return this.mBookingDates;  };

    public DatePickerController getController()
    {
        return mController;
    }

    public TypedArray getTypedArray()
    {
        return typedArray;
    }
}