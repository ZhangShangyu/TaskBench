package com.example.lawliet.taskbench;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.example.lawliet.taskbench.Global.Constant;
import com.example.lawliet.taskbench.Global.LocalUserInfo;
import com.example.lawliet.taskbench.MainActivity;
import com.example.lawliet.taskbench.R;
import com.example.lawliet.taskbench.ServerTask.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lawliet on 2016/6/15.
 */
public class DistributeActivity extends Activity implements DatePickerDialog.OnDateSetListener {
    Context context =this;
    private int teamId;
    private int leaderId;
    private ClickListenerInterface clickListenerInterface;
    private Button dateSet;
    private TextView dateText;
    private int day, month,year, dayEnd, monthEnd, yearEnd;

    public interface ClickListenerInterface{
        public void doConfirm(String userName, String taskName, String taskDescription, int teamId, int leaderId, int day, int month, int year, int dayEnd, int monthEnd, int yearEnd);

        public void doCancel();
    }


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        setContentView(R.layout.distribute_dialog);
        Intent intent = getIntent();
        teamId = intent.getIntExtra("distributeTeamId", 0);
        leaderId = intent.getIntExtra("distributeLeaderId", 0);

        init();
    }

    private void sendTaskData(final String userName, final String taskName, final String description, final String startDate, final String deadline,final int teamId)
    {
        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_SAVE_TASK,
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
                                    Toast.makeText(context, "分配任务成功！", Toast.LENGTH_SHORT).show();
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(context, "分配任务失败！", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(context,"服务器返回码错误！",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context,"服务器错误！！",Toast.LENGTH_SHORT).show();
                Log.e("server data error ", error.getMessage(), error);

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("userName", userName);
                map.put("taskName",taskName);
                map.put("description",description);
                map.put("startDate",startDate);
                map.put("deadline",deadline);
                map.put("teamId",teamId+"");

                Log.e("send data",map.toString());
                return map;
            }
        };

        VolleyController.getInstance(context).addToRequestQueue(req);
    }

    public void init(){
        this.clickListenerInterface = new ClickListenerInterface() {
            @Override
            public void doConfirm(String userName, String taskName, String taskDescription, int teamId, int leaderId, int day, int month, int year, int dayEnd, int monthEnd, int yearEnd) {
                //数据库操作
                String startDate = year+"-"+month+"-"+day;
                String deadline = yearEnd+"-"+monthEnd+"-"+dayEnd;
                sendTaskData(userName,taskName,taskDescription,startDate,deadline, teamId);
                finish();
            }

            @Override
            public void doCancel() {
                Log.d("distribute","cancel");
                finish();
            }
        };


        Button distribute = (Button) findViewById(R.id.distribute_dialog_distributeButton);
        Button cancel = (Button) findViewById(R.id.distribute_dialog_cancelButton);

        distribute.setOnClickListener(new ClickListener(teamId,leaderId));
        cancel.setOnClickListener(new ClickListener(teamId, leaderId));

        dateSet = (Button) findViewById(R.id.distribute_dialog_set_time_button);
        dateText = (TextView) findViewById(R.id.distribute_dialog_date);
        dateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        DistributeActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    public void setClickListener(ClickListenerInterface clickListenerInterface){
        this.clickListenerInterface = clickListenerInterface;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = "Date: From- "+dayOfMonth+"/"+(++monthOfYear)+"/"+year+" To "+dayOfMonthEnd+"/"+(++monthOfYearEnd)+"/"+yearEnd;
        this.day = dayOfMonth;
        this.month = monthOfYear;
        this.year = year;
        this.dayEnd = dayOfMonthEnd;
        this.monthEnd = monthOfYearEnd;
        this.yearEnd = yearEnd;
        dateText.setText(date);
    }

    private class ClickListener implements View.OnClickListener{
        private int teamId;
        private int leaderId;
        private String userName;
        private String taskName;
        private String taskDescription;

        ClickListener(int teamId, int leaderId){
            super();
            this.teamId = teamId;
            this.leaderId = leaderId;
        }
        @Override
        public void onClick(View view){
            int id = view.getId();
            EditText taskNameED = (EditText) findViewById(R.id.distribute_dialog_taskName);
            EditText userNameED = (EditText) findViewById(R.id.distribute_dialog_username);
            EditText taskDescriptionED = (EditText) findViewById(R.id.distribute_dialog_taskDescription);
            this.userName = userNameED.getText().toString();
            this.taskName = taskNameED.getText().toString();
            TextView distributeAT = (TextView) findViewById(R.id.distribute_alert_text);
            this.taskDescription = taskDescriptionED.getText().toString();
            switch (id){
                case R.id.distribute_dialog_distributeButton:
                    if(userName.equals(""))
                        distributeAT.setText("User name can not be empty!");
                    else if(taskName.equals(""))
                        distributeAT.setText("Task name can not be empty!");
                    else if(taskDescription.equals(""))
                        distributeAT.setText("Task description can not be empty!");
//                    else if(day == 0 || month == 0 || year == 0 || dayEnd == 0 || yearEnd == 0 || monthEnd == 0)
//                        distributeAT.setText("please set the start date and deadline");
                    else
                    clickListenerInterface.doConfirm(userName, taskName, taskDescription, teamId, leaderId, day, month, year, dayEnd, monthEnd, yearEnd);
                    break;
                case R.id.distribute_dialog_cancelButton:
                    clickListenerInterface.doCancel();
                    break;
            }
        }
    }
}