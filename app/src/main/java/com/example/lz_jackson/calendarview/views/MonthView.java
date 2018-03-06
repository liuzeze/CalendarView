package com.example.lz_jackson.calendarview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.example.lz_jackson.calendarview.entity.CalendarInfo;
import com.example.lz_jackson.calendarview.them.IDayTheme;
import com.example.lz_jackson.calendarview.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class MonthView extends View {
    protected int NUM_COLUMNS = 7;
    protected int NUM_ROWS = 6;
    protected Paint paint;
    protected IDayTheme theme;
    private IDateClick dateClick;
    protected int currYear, currMonth, currDay;
    protected int selYear, selMonth, selDay;

    protected int[][] daysString;
    protected float columnSize, rowSize = 100;
    protected float density;
    private int indexMonth;
    private int width;
    protected List<CalendarInfo> calendarInfos = new ArrayList<CalendarInfo>();
    private int downX = 0, downY = 0;
    protected int initDay;
    protected int initMonth;
    protected int initYear;

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = getResources().getDisplayMetrics().density;
        Calendar calendar = Calendar.getInstance();
        currYear = calendar.get(Calendar.YEAR);
        currMonth = calendar.get(Calendar.MONTH);
        currDay = calendar.get(Calendar.DATE);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setSelectDate(currYear, currMonth, currDay);
        createTheme();
        rowSize = theme == null ? 70 : theme.dateHeight();
    }

    /**
     * 初始化日期月份
     *
     * @param year
     * @param month
     * @param currDay2
     */
    public void initCalendar(int year, int month, int currDay2) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setSelectDate(year, month, currDay2);
        createTheme();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = (int) (300 * density);
        }
        width = widthSize;
        NUM_ROWS = 6;
        int heightSize = (int) (NUM_ROWS * rowSize);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(theme.colorMonthView());
        drawDate(canvas, selYear, selMonth);
    }

    private void drawDate(Canvas canvas, int year, int month) {
        canvas.save();
        NUM_ROWS = getMonthRowNumber(year, month);
        columnSize = getWidth() * 1.0F / NUM_COLUMNS;//列宽
        rowSize = getHeight() * 1.0F / NUM_ROWS;//行高
        daysString = new int[6][7];
        int mMonthDays = DateUtils.getMonthDays(year, month);
        int weekNumber = DateUtils.getFirstDayWeek(year, month);//第一日是周几
        int column, row;
        //drawLines(canvas, NUM_ROWS);
        for (int day = 0; day < mMonthDays; day++) {
            //计算每一天的位置
            column = (day + weekNumber - 1) % 7;
            row = (day + weekNumber - 1) / 7;
            daysString[row][column] = day + 1;
            drawContent(canvas, column, row, year, month, daysString[row][column]);
        }
        canvas.restore();
    }


    /**
     * 实例化Theme
     */
    protected abstract void createTheme();

    /**
     * 回执格网线
     *
     * @param canvas
     */
    protected abstract void drawContent(Canvas canvas, int column, int row, int year, int month, int day);


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch (eventCode) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {//点击事件
                    performClick();
                    int row = (int) ((upY + downY) / 2 / rowSize);
                    int column = (int) ((upX + downX) / 2 / columnSize);
                    setSelectDate(selYear, selMonth, daysString[row][column]);
                    if (prevDay(selYear, selMonth, selDay)) {
                        return false;
                    } else {
                        //执行activity发送过来的点击处理事件
                        if (dateClick != null) {
                            //初始化默认的日期  用于刷新界面选中背景
                            initYear = selYear;
                            initMonth = selMonth;
                            initDay = selDay;
                            dateClick.onClickOnDate(selYear, selMonth + 1, selDay);
                        }
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 设置点击过程中选中的日期
     *
     * @param year
     * @param month
     */
    protected void setSelectDate(int year, int month, int day) {
        selYear = year;
        selMonth = month;
        selDay = day;
    }

    /**
     * 获取行数
     *
     * @param year
     * @param month
     * @return
     */
    protected int getMonthRowNumber(int year, int month) {
        int monthDays = DateUtils.getMonthDays(year, month);
        int weekNumber = DateUtils.getFirstDayWeek(year, month);
        return (monthDays + weekNumber - 1) % 7 == 0 ? (monthDays + weekNumber - 1) / 7 : (monthDays + weekNumber - 1) / 7 + 1;
    }


    /**
     * 判断是否是传入日期之前的日期
     *
     * @param year
     * @param month
     * @param Day
     * @return
     */
    protected boolean prevDay(int year, int month, int Day) {
        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        return ((year < time.year)) || (year == time.year && month < time.month) || (month == time.month && Day < time.monthDay);
    }

    /**
     * 外部传入需要展示的业务字样  如：￥ 1200
     *
     * @param calendarInfos
     */
    public void setCalendarInfos(List<CalendarInfo> calendarInfos) {
        this.calendarInfos = calendarInfos;
    }

    /**
     * 判断当天是否需要展示与业务相关的字样  比如：￥1200等
     *
     * @param day
     * @return
     */
    protected String iscalendarInfo(int year, int month, int day) {
        if (calendarInfos == null || calendarInfos.size() == 0) return "";
        for (CalendarInfo calendarInfo : calendarInfos) {
            if (calendarInfo.day == day && calendarInfo.month == month + 1 && calendarInfo.year == year) {
                return calendarInfo.des;
            }
        }
        return "";
    }

    /**
     * 初始化默认选中
     *
     * @param currYear2
     * @param currMonth2
     * @param currDay2
     */
    public void setSelectData(int currYear2, int currMonth2, int currDay2) {
        initYear = currYear2;
        initMonth = currMonth2 - 1;
        initDay = currDay2;
    }


    public void setDateClick(IDateClick dateClick) {
        this.dateClick = dateClick;
    }

    public interface IDateClick {
        void onClickOnDate(int year, int month, int day);
    }

    /**
     * 设置样式
     *
     * @param theme
     */
    public void setTheme(IDayTheme theme) {
        this.theme = theme;
    }

    public int getSelYear() {
        return selYear;
    }

    public int getSelMonth() {
        return selMonth;
    }
}
