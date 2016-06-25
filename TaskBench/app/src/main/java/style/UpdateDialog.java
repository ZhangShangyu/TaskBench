package style;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lawliet.taskbench.Global.Constant;
import com.example.lawliet.taskbench.Global.LocalUserInfo;
import com.example.lawliet.taskbench.R;
import com.example.lawliet.taskbench.ServerTask.VolleyController;
import com.example.lawliet.taskbench.TeamsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.TeamBean;


/**
 * Created by Lawliet on 2016/6/14.
 */
public class UpdateDialog extends Dialog{
    private Context context;
    private String description;
    private int currentSchedule;
    private ClickListenerInterface clickListenerInterface;
    private  EditText update_schedule;
    private  int taskId;

    public interface ClickListenerInterface{
        public void doConfirm(int newSchedule);

        public void doCancel();
    }

    public UpdateDialog(Context context, String description, int currentSchedule,int taskId){
        super(context);
        this.context = context;
        this.description = description;
        this.currentSchedule = currentSchedule;
        this.taskId = taskId;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        init();
    }

    public void init(){

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_dialog, null);
        setContentView(view);

        TextView description = (TextView) findViewById(R.id.dialog_description);
        update_schedule = (EditText) findViewById(R.id.dialog_newschedule);
        final Button update = (Button) findViewById(R.id.dialog_update);
        Button cancel = (Button) findViewById(R.id.dialog_cancel);
        description.setText("description:" + this.description);
        update_schedule.setHint(this.currentSchedule + "");


        update.setOnClickListener(new ClickListener());
        cancel.setOnClickListener(new ClickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        this.clickListenerInterface = new ClickListenerInterface() {
            @Override
            public void doConfirm(int newSchedule) {
                //数据库操作
                updateServerData();
                dismiss();
            }

            @Override
            public void doCancel() {
                dismiss();
            }
        };
    }

    private void updateServerData()
    {
        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_UPDATE_TASK,
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
                                    Toast.makeText(context, "更新进度成功！", Toast.LENGTH_SHORT).show();
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(context, "更新进度失败！", Toast.LENGTH_SHORT).show();
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
                map.put("taskId", taskId+"");
                map.put("newSchedule",update_schedule.getText().toString().trim());
                Log.e("send data",map.toString());
                return map;
            }
        };

        VolleyController.getInstance(context).addToRequestQueue(req);
    }


    public void setClickListener(ClickListenerInterface clickListenerInterface){
        this.clickListenerInterface = clickListenerInterface;
    }

    private class ClickListener implements View.OnClickListener{
        ClickListener(){
            super();
        }
        @Override
        public void onClick(View view){
            EditText update_schedule = (EditText) findViewById(R.id.dialog_newschedule);
            int updateInt;
            try{
                updateInt = Integer.parseInt(update_schedule.getText().toString());
            }catch (Exception e){
                updateInt = -1;
            }

            int id = view.getId();
            switch (id){
                case R.id.dialog_update:
                    clickListenerInterface.doConfirm(updateInt);
                    break;
                case R.id.dialog_cancel:
                    clickListenerInterface.doCancel();
                    break;
            }
        }
    }
}
