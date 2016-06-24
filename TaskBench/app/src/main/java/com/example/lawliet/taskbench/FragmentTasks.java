package com.example.lawliet.taskbench;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lawliet.taskbench.Global.Constant;
import com.example.lawliet.taskbench.Global.LocalUserInfo;
import com.example.lawliet.taskbench.ServerTask.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.Task;

/**
 * Created by Lawliet on 2016/5/29.
 */
public class FragmentTasks extends ListFragment implements MyFragmentInterface {

    private View view;
    private TaskAdapter taskAdapter;

    public void setIsTeam(boolean isTeam) {
        this.isTeam = isTeam;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean isTeam;
    private int id;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        getServerData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.tasks,container,false);
        return view;
    }

    public void getServerData(){
        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_GET_TASK_INFO,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e("TAG get data ", response);
                        try
                        {
                            JSONObject data = new JSONObject(response);
                            int code = data.getInt("code");
                            {
                                if(code == 1)
                                {
                                    JSONArray jsonArray = data.getJSONArray("infoArray");
                                    setTaskInfo(jsonArray);
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(getActivity(), "无任务！", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"服务器返回码错误！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        catch (JSONException e) {
                            Log.e("json解析错误","json格式错误");
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getActivity(),"服务器错误！！",Toast.LENGTH_SHORT).show();
                Log.e("server data error ", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                if(isTeam)
                {
                    map.put("teamId", id+"");
                    map.put("isTeam", "yes");
                }
                else
                {
                    map.put("userId", LocalUserInfo.userId+"");
                    map.put("isTeam", "no");
                }
                Log.e("send data",map.toString());
                return map;
            }
        };

        VolleyController.getInstance(getActivity()).addToRequestQueue(req);

    }
    private void setTaskInfo(JSONArray jsonArray)
    {
        List<Task> tasks = new ArrayList<>();

        for(int i = 0 ; i<jsonArray.length();i++)
        {
            try
            {
                JSONObject json = jsonArray.getJSONObject(i);
                Task task = new Task();
                task.setDescription(json.getString("description"));
                task.setName(json.getString("task_name"));
                if(isTeam)
                {
                    task.setTeamOrUser(json.getString("user_name"));
                }
                else
                {
                    task.setTeamOrUser(json.getString("team_name"));
                }
                task.setSchedule(json.getInt("schedule"));
                task.setStartdate(json.getString("start_date"));
                task.setDeadline(json.getString("deadline"));
                task.setTaskId(json.getInt("task_id"));
                tasks.add(task);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
        taskAdapter = new TaskAdapter(getActivity(),tasks);
        if(isTeam)
            taskAdapter.setIsTeam(true);
        else
            taskAdapter.setIsTeam(false);
        setListAdapter(taskAdapter);
    }
}
