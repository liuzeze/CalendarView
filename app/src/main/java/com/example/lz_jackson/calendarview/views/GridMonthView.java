package com.example.lz_jackson.calendarview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.lz_jackson.calendarview.them.DefaultDayTheme;
import com.example.lz_jackson.calendarview.utils.LunarCalendar;


public class GridMonthView extends MonthView {

    public GridMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void drawContent(Canvas canvas, int column, int row, int year, int month, int day) {
        drawBG(canvas, column, row, year, month, daysString[row][column]);
        drawRest(canvas, column, row, year, month, daysString[row][column]);
        drawText(canvas, column, row, year, month, daysString[row][column]);
    }


    protected void drawBG(Canvas canvas, int column, int row, int year, int month, int day) {
        if (year == initYear && month == initMonth && day == initDay) {
            //绘制背景色矩形
            float startRecX = columnSize * column + 1;
            float startRecY = rowSize * row + 1;
            float endRecX = startRecX + columnSize - 2 * 1;
            float endRecY = startRecY + rowSize - 2 * 1;
            paint.setColor(theme.colorSelectBG());
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(startRecX, startRecY, endRecX, endRecY, paint);
        }
    }


    protected void drawRest(Canvas canvas, int column, int row, int year, int month, int day) {
        if (!prevDay(year, month, day)) {
            float pointX0 = columnSize * (column + 1);
            float pointY0 = rowSize * row - 1;
            float pointX1 = (float) (columnSize * (column + 1) - rowSize * 0.5);
            float pointY1 = rowSize * row + 1;
            float pointX2 = columnSize * (column + 1);
            float pointY2 = (float) (rowSize * (row + 1) - rowSize * 0.5);
            Path path = new Path();
            path.moveTo(pointX0, pointY0);
            path.lineTo(pointX1, pointY1);
            path.lineTo(pointX2, pointY2);
            path.close();
            paint.setStyle(Paint.Style.FILL);

            //获取农历及节日
            LunarCalendar lunarCalendar = new LunarCalendar();
            lunarCalendar.getLunarDate(year, month + 1, day);
            String festival = lunarCalendar.getFestival();

            if (festival != null) {
                paint.setColor(theme.colorRest());
                canvas.drawPath(path, paint);
                paint.setTextSize(theme.sizeDesc());
                paint.setColor(theme.colorSelectDay());
                canvas.drawText(festival, (float) (pointX0 - rowSize * 0.2), (float) (pointY0 + (paint.measureText("休")) * 2), paint);

            }

        }
    }

    protected void drawText(Canvas canvas, int column, int row, int year, int month, int day) {


        paint.setTextSize(theme.sizeDay());
        float startX = columnSize * column + (columnSize - paint.measureText(day + "")) / 2;
        float startY = rowSize * row + rowSize / 2 - (paint.ascent() + paint.descent()) / 2;
        paint.setStyle(Paint.Style.STROKE);
        String des = iscalendarInfo(year, month, day);
        paint.setColor(Color.BLACK);

        if (year == initYear && month == initMonth && day == initDay) {//选中的日期
            paint.setColor(theme.colorSelectDay());

            if (day == currDay && month == currMonth) {//今日的颜色，不是选中的时候
                canvas.drawText("今天", startX - (paint.measureText("今")), startY, paint);
            } else if (day == currDay + 1 && month == currMonth) {
                canvas.drawText("明天", startX - (paint.measureText("明")), startY, paint);

            } else if (day == currDay + 2 && month == currMonth) {
                canvas.drawText("后天", startX - (paint.measureText("后")), startY, paint);

            } else {
                int dateY = (int) (startY - 10);
                if (prevDay(year, month, day)) paint.setColor(theme.colorNolickDay());
                canvas.drawText(day + "", startX, dateY, paint);

            }
            if (!TextUtils.isEmpty(des)) {//desc不为空的时候
                paint.setTextSize(theme.sizeDesc());
                int priceX = (int) (columnSize * column + (columnSize - paint.measureText(des)) / 2);
                int priceY = (int) (startY + 15);
                if (!prevDay(year, month, day))
                    canvas.drawText(des, priceX, priceY, paint);
            }
        } else {//美誉选中的日期

            if (day == currDay && month == currMonth) {//今日的颜色，不是选中的时候
                paint.setColor(theme.colorToday());
                canvas.drawText("今天", startX - (paint.measureText("今")), startY, paint);
            } else if (day == currDay + 1 && month == currMonth) {
                paint.setColor(theme.colorTomorrow());
                canvas.drawText("明天", startX - (paint.measureText("明")), startY, paint);

            } else if (day == currDay + 2 && month == currMonth) {
                paint.setColor(theme.colorDayAfterTomorrow());
                canvas.drawText("后天", startX - (paint.measureText("后")), startY, paint);

            } else {
                int dateY = (int) (startY - 10);
                if (prevDay(year, month, day)) paint.setColor(theme.colorNolickDay());
                canvas.drawText(day + "", startX, dateY, paint);
            }
            if (!TextUtils.isEmpty(des)) {//desc不为空的时候
                paint.setColor(theme.colorDesc());
                paint.setTextSize(theme.sizeDesc());
                int priceX = (int) (columnSize * column + (columnSize - paint.measureText(des)) / 2);
                int priceY = (int) (startY + 15);
                if (!prevDay(year, month, day))
                    canvas.drawText(des, priceX, priceY, paint);
            }
        }
    }

    @Override
    protected void createTheme() {
        theme = new DefaultDayTheme();
    }


}
