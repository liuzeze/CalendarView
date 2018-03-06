package com.example.lz_jackson.calendarview.them;

import android.graphics.Color;


public class DefaultDayTheme implements IDayTheme {
    @Override
    public int colorSelectBG() {
        return Color.parseColor("#13A4D3");
    }

    @Override
    public int colorSelectDay() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int colorToday() {
        return Color.RED;
    }

    @Override
    public int colorMonthView() {
        return Color.parseColor("#FFFFFF");
    }

    @Override
    public int colorWeekday() {
        return Color.parseColor("#404040");
    }

    @Override
    public int colorNolickDay() {
        return Color.GRAY;
    }

    @Override
    public int colorTomorrow() {
        return Color.GREEN;
    }

    @Override
    public int colorDayAfterTomorrow() {
        return Color.BLUE;
    }

    @Override
    public int colorWeekend() {
        return Color.parseColor("#404040");
    }

    @Override
    public int colorDecor() {
        return Color.parseColor("#68CB00");
    }

    @Override
    public int colorRest() {
        return Color.parseColor("#68CB00");
    }

    @Override
    public int colorWork() {
        return Color.parseColor("#FF9B12");
    }

    @Override
    public int colorDesc() {
        return Color.parseColor("#FF9B12");
    }

    @Override
    public int sizeDay() {
        return 30;
    }

    @Override
    public int sizeDecor() {
        return 6;
    }

    @Override
    public int sizeDesc() {
        return 15;
    }

    @Override
    public int dateHeight() {
        return 100;
    }

    @Override
    public int colorLine() {
        return Color.parseColor("#CBCBCB");
    }

    @Override
    public int smoothMode() {
        return 0;
    }
}
