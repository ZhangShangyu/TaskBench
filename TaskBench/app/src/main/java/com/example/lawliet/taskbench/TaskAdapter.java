package com.example.lawliet.taskbench;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import style.HistogramView;

/**
 * Created by Lawliet on 2016/6/7.
 */
public class TaskAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public TaskAdapter(Context context,List<Map<String, Object>> data){
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

        

        return convertView;
    }

}
