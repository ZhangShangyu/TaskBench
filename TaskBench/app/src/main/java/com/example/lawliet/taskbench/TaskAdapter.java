package com.example.lawliet.taskbench;

import android.content.Context;
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
public class TaskAdapter extends BaseAdapter {

    private List<Task> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public TaskAdapter(Context context,List<Task> data){
        this.context=context;
        this.data=data;
        this.layoutInflater= LayoutInflater.from(context);
    }
    public final class TaskCell{
        public TextView taskName;
        public TextView taskTeam;
        public TextView taskDeadline;
        public TextView taskStartTime;
        public TextView taskSchedule;
        public HistogramView taskDateScheduleChart;
        private HistogramView taskScheduleChart;
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
        TaskCell taskCell = null;
        if(convertView == null){
            taskCell = new TaskCell();
            convertView = layoutInflater.inflate(R.layout.task, null);
            taskCell.taskName = (TextView) convertView.findViewById(R.id.task_name);
            taskCell.taskTeam = (TextView) convertView.findViewById(R.id.task_team);
            taskCell.taskDeadline = (TextView) convertView.findViewById(R.id.task_deadline);
            taskCell.taskStartTime = (TextView) convertView.findViewById(R.id.task_startDate);
            taskCell.taskSchedule = (TextView) convertView.findViewById(R.id.task_schedule);
            taskCell.taskDateScheduleChart = (HistogramView) convertView.findViewById(R.id.task_date_schedule_chart);
            taskCell.taskScheduleChart = (HistogramView) convertView.findViewById(R.id.task_schedule_chart);
        }
        else{
            taskCell = (TaskCell) convertView.getTag();
        }
        taskCell.taskName.setText((String) data.get(position).getName());
        taskCell.taskTeam.setText((String) data.get(position).getTeam());
        taskCell.taskSchedule.setText(String.valueOf(data.get(position).getSchedule()));
        taskCell.taskStartTime.setText((String) data.get(position).getStartdate().toString());
        taskCell.taskDeadline.setText((String) data.get(position).getDeadline().toString());
        taskCell.taskScheduleChart.setProgress((double) (data.get(position).getSchedule()/100));

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
        double percent = dayPast/dayAll;
        taskCell.taskDateScheduleChart.setProgress(percent);

        return convertView;
    }

}
