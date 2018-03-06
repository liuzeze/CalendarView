package com.example.lz_jackson.calendarview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.lz_jackson.calendarview.R;
import com.example.lz_jackson.calendarview.entity.CalendarInfo;
import com.example.lz_jackson.calendarview.them.IDayTheme;
import com.example.lz_jackson.calendarview.them.IWeekTheme;

import java.util.List;


public class GridCalendarView extends LinearLayout {
    private WeekView weekView;
    private GridMonthView gridMonthView;
    private TextView textViewYear, textViewMonth;
    private final View mView;


    public GridCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        mView = LayoutInflater.from(context).inflate(R.layout.display_item_date, null);
        gridMonthView = (GridMonthView) mView.findViewById(R.id.GridMonthView);
        weekView = (WeekView) mView.findViewById(R.id.weekview);

    }

    public void init(int year, int month, int currDay2) {
        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        removeAllViews();
        addView(mView, llParams);
        textViewYear = (TextView) mView.findViewById(R.id.year);
        textViewMonth = (TextView) mView.findViewById(R.id.month);
        gridMonthView.initCalendar(year, month, currDay2);
        textViewYear.setText(year + "年");
        textViewMonth.setText(fillZero(month+1) + "月");


    }

    /**
     * 月份前补0
     * @param number
     * @return
     */
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    /**
     * 设置日历点击事件
     *
     * @param dateClick
     */
    public void setDateClick(MonthView.IDateClick dateClick) {
        gridMonthView.setDateClick(dateClick);
    }

    /**
     * 设置星期的形式
     *
     * @param weekString 默认值	"日","一","二","三","四","五","六"
     */
    public void setWeekString(String[] weekString) {
        weekView.setWeekString(weekString);
    }

    public void setCalendarInfos(List<CalendarInfo> calendarInfos) {
        gridMonthView.setCalendarInfos(calendarInfos);
    }

    public void setDayTheme(IDayTheme theme) {
        gridMonthView.setTheme(theme);
    }

    public void setWeekTheme(IWeekTheme weekTheme) {
        weekView.setWeekTheme(weekTheme);
    }

    public void setSelectData(int currYear2, int currMonth2, int currDay2) {
        gridMonthView.setSelectData(currYear2, currMonth2, currDay2);
    }
}