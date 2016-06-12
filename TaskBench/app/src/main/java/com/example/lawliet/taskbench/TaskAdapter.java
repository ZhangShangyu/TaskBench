package com.example.lawliet.taskbench;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;


import Bean.Task;
import style.HistogramView;

/**
 * Created by Lawliet on 2016/6/7.
 */

 class ViewHolder{
    public TextView taskName;
    public TextView taskTeam;
    public TextView taskDeadline;
    public TextView taskStartTime;
    public TextView taskSchedule;
    public HistogramView taskDateScheduleChart;
    public HistogramView taskScheduleChart;
}

public class TaskAdapter extends BaseAdapter {

    private List<Task> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private LayoutInflater mInflater = null;
    public TaskAdapter(Context context,List<Task> data){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        this.data=data;
        this.layoutInflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount(){
        return data.size();
    }

    @Override
    public Object getItem(int position){
        return data.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder taskCell = null;
        convertView = mInflater.inflate(R.layout.task,null);
        taskCell = new ViewHolder();
        taskCell.taskName = (TextView) convertView.findViewById(R.id.task_name);
        taskCell.taskTeam = (TextView) convertView.findViewById(R.id.task_team);
        taskCell.taskDeadline = (TextView) convertView.findViewById(R.id.task_deadline);
        taskCell.taskStartTime = (TextView) convertView.findViewById(R.id.task_startDate);
        taskCell.taskSchedule = (TextView) convertView.findViewById(R.id.task_schedule);
        taskCell.taskDateScheduleChart = (HistogramView) convertView.findViewById(R.id.task_date_schedule_chart);
        taskCell.taskScheduleChart = (HistogramView) convertView.findViewById(R.id.task_schedule_chart);

        Log.d("DATA", "name:" + data.get(position).getName());
        taskCell.taskName.setText(data.get(position).getName());
        Log.d("DATA", "team:" + data.get(position).getTeam());
        taskCell.taskTeam.setText(data.get(position).getTeam());
        Log.d("DATA", "schedule:" + data.get(position).getSchedule());
        taskCell.taskSchedule.setText(String.valueOf(data.get(position).getSchedule()));
        Log.d("DATA", "startdate:" + data.get(position).getStartdate());
        taskCell.taskStartTime.setText(data.get(position).getStartdate().toString());
        Log.d("DATA", "deadline:" + data.get(position).getDeadline());
        taskCell.taskDeadline.setText(data.get(position).getDeadline().toString());
        double chartschdule = (((double) data.get(position).getSchedule()) / 100);
        Log.d("DATA", "chartschedule:" + chartschdule);
        taskCell.taskScheduleChart.setProgress(chartschdule);

        Calendar calS = Calendar.getInstance();
        calS.setTime(data.get(position).getStartdate());
        Calendar calD = Calendar.getInstance();
        calD.setTime(data.get(position).getDeadline());
        Calendar calN = Calendar.getInstance();
        java.util.Date now = new java.util.Date();
        calN.setTime(new Date(now.getTime()));
        long sl = calS.getTimeInMillis();
        long dl = calD.getTimeInMillis();
        long nl = calN.getTimeInMillis();
        int dayAll = (int) (dl - sl)/(1000 * 60 * 60 * 24);
        int dayPast = (int) (nl - sl)/(1000 * 60 * 60 * 24);
        double percent = dayPast/(dayAll+1);
        taskCell.taskDateScheduleChart.setProgress(percent);

        return convertView;
    }

}
