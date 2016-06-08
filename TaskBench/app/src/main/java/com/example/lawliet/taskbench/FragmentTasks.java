package com.example.lawliet.taskbench;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Bean.Task;

/**
 * Created by Lawliet on 2016/5/29.
 */
public class FragmentTasks extends ListFragment {

    private View view;
    private TaskAdapter taskAdapter;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        List<Task> tasksData = getTaskData();
        taskAdapter = new TaskAdapter(getActivity(),tasksData);
        setListAdapter(taskAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.tasks,container,false);
        return view;
    }

    public List<Task> getTaskData(){
        List<Task> tasks = new ArrayList<Task>();
        for(int i = 0; i < 10; i++){
            Task task = new Task();
            task.setName("taskname" + i);
            task.setTeam("taskteam" + i);
            task.setSchedule(10 * i);
            task.setStartdate(new Date(new java.util.Date().getTime()));
            task.setDeadline(new Date(new java.util.Date().getTime()));
            tasks.add(task);
        }
        return tasks;
    }
}
