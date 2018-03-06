package com.example.lz_jackson.calendarview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.lz_jackson.calendarview.entity.CalendarInfo;
import com.example.lz_jackson.calendarview.views.GridCalendarView;
import com.example.lz_jackson.calendarview.views.MonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<CalendarInfo> list;
    //当前日期
    private int currYear;
    private int currMonth;
    private int currDay;

    private int currYear2;
    private int currMonth2;
    private int currDay2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        init();

    }

    private void init() {


        Calendar calendar = Calendar.getInstance();
        currYear2 = calendar.get(Calendar.YEAR);
        currMonth2 = calendar.get(Calendar.MONTH) + 1;
        currDay2 = calendar.get(Calendar.DAY_OF_MONTH)+10;


        currYear = calendar.get(Calendar.YEAR);
        currDay = calendar.get(Calendar.DAY_OF_MONTH);
        currMonth = calendar.get(Calendar.MONTH) + 1;
        list = new ArrayList<CalendarInfo>();
        list.add(new CalendarInfo(currYear, currMonth + 1, 4, "￥1200"));
        list.add(new CalendarInfo(currYear, currMonth + 1, 6, "￥1200"));
        list.add(new CalendarInfo(currYear, currMonth + 1, 12, "￥1200"));
        list.add(new CalendarInfo(currYear, currMonth + 1, 16, "￥1200"));
        list.add(new CalendarInfo(currYear, currMonth + 1, 28, "￥1200"));
        list.add(new CalendarInfo(currYear, currMonth + 1, 1, "￥1200", 1));
        list.add(new CalendarInfo(currYear, currMonth, 11, "￥1200", 1));
        list.add(new CalendarInfo(currYear, currMonth + 1, 19, "￥1200", 2));
        list.add(new CalendarInfo(currYear, currMonth, 21, "￥1200", 1));
        recyclerView.setAdapter(new SimpleMonthAdapter());
    }

    public class SimpleMonthAdapter extends RecyclerView.Adapter<SimpleMonthAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            GridCalendarView gridCalendarView = new GridCalendarView(MainActivity.this, null);
            ViewHolder viewHolder = new ViewHolder(gridCalendarView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GridCalendarView gridCalendarView = holder.gridCalendarView;
            gridCalendarView.init((currMonth - 1 + position)>11?currYear+1:currYear, (currMonth - 1 + position)%12, currYear);

            gridCalendarView.setSelectData(currYear2, currMonth2, currDay2);


            gridCalendarView.setCalendarInfos(list);
            gridCalendarView.setDateClick(new MonthView.IDateClick() {

                @Override
                public void onClickOnDate(int year, int month, int day) {
                    currYear2 = year;
                    currMonth2 = month;
                    currDay2 = day;
                    notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "点击了" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final GridCalendarView gridCalendarView;

            public ViewHolder(View itemView) {
                super(itemView);
                gridCalendarView = (GridCalendarView) itemView;
                LinearLayout.LayoutParams llParams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                gridCalendarView.setLayoutParams(llParams);
            }
        }

    }

}
