package com.example.lawliet.taskbench;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


import Bean.Task;
import style.HistogramView;
import style.UpdateDialog;

/**
 * Created by Lawliet on 2016/6/7.
 */

 class TaskCell{
    public TextView taskName;
    public TextView taskTeam;
    public TextView taskDeadline;
    public TextView taskStartTime;
    public TextView taskSchedule;
    public HistogramView taskDateScheduleChart;
    public HistogramView taskScheduleChart;
    public TextView datePercent;
}

public class TaskAdapter extends BaseAdapter {

    private List<Task> data;
    private UpdateDialog updateDialog;

    public void setIsTeam(boolean isTeam) {
        this.isTeam = isTeam;
    }

    private boolean isTeam;
//    private LayoutInflater layoutInflater;
    private Context context;
    private LayoutInflater mInflater = null;
    public TaskAdapter(Context context,List<Task> data){
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        this.data=data;
//        this.layoutInflater= LayoutInflater.from(context);
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
    public View getView(final int position, View convertView, ViewGroup parent){
        convertView = mInflater.inflate(R.layout.task,null);
        TaskCell taskCell = new TaskCell();
        taskCell.taskName = (TextView) convertView.findViewById(R.id.task_name);
        taskCell.taskTeam = (TextView) convertView.findViewById(R.id.task_team);
        taskCell.taskDeadline = (TextView) convertView.findViewById(R.id.task_deadline);
        taskCell.taskStartTime = (TextView) convertView.findViewById(R.id.task_startDate);
        taskCell.taskSchedule = (TextView) convertView.findViewById(R.id.task_schedule);
        taskCell.taskDateScheduleChart = (HistogramView) convertView.findViewById(R.id.task_date_schedule_chart);
        taskCell.taskScheduleChart = (HistogramView) convertView.findViewById(R.id.task_schedule_chart);
        taskCell.datePercent = (TextView) convertView.findViewById(R.id.task_datePercent);

        taskCell.taskName.setText(data.get(position).getName());
        taskCell.taskTeam.setText(data.get(position).getTeamOrUser());
        taskCell.taskSchedule.setText(String.valueOf(data.get(position).getSchedule()));
        taskCell.taskStartTime.setText(data.get(position).getStartdate());
        taskCell.taskDeadline.setText(data.get(position).getDeadline());
        double chartSchedule = (((double) data.get(position).getSchedule()) / 100);
        taskCell.taskScheduleChart.setProgress(chartSchedule);

        CalPercent cal = new CalPercent();
        double percent = cal.getPercent(data.get(position).getStartdate(),data.get(position).getDeadline());

        taskCell.taskDateScheduleChart.setProgress(percent);
        int percentInt = (int) (percent*100);
        taskCell.datePercent.setText(percentInt + "");


        if(!isTeam)
        taskCell.taskScheduleChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog = new UpdateDialog(context, data.get(position).getDescription(),
                        data.get(position).getSchedule(),data.get(position).getTaskId());
                updateDialog.setCanceledOnTouchOutside(true);
                updateDialog.setTitle("Update the schedule");
                updateDialog .show();
            }
        });

        return convertView;
    }


}
