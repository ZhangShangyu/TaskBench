package style;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lawliet.taskbench.Global.Constant;
import com.example.lawliet.taskbench.Global.LocalUserInfo;
import com.example.lawliet.taskbench.R;
import com.example.lawliet.taskbench.ServerTask.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lawliet on 2016/6/16.
 */
public class CreateTeamDialog extends Dialog {
    private Context context;
    private int userId;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface{
        public void doConfirm(int userId, String teamName, String teamDescription);

        public void doCancel();
    }

    public CreateTeamDialog(Context context, int userId){
        super(context);
        this.context = context;
        this.userId = userId;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        init();
    }

    public void init(){
        this.clickListenerInterface = new ClickListenerInterface() {
            @Override
            public void doConfirm(int userId, String teamName, String teamDescription) {
                //shujuku
                createTeam(userId,teamName,teamDescription);
                dismiss();
            }

            @Override
            public void doCancel() {
                dismiss();
            }
        };

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.create_team_dialog, null);
        setContentView(view);

        Button create = (Button) findViewById(R.id.create_team_create_button);
        Button cancel = (Button) findViewById(R.id.create_team_cancel_button);
        create.setOnClickListener(new ClickListener(this.userId));
        cancel.setOnClickListener(new ClickListener(this.userId));
    }

    private void createTeam(int userId, final String teamName, final String teamDescription)
    {
        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_CREATE_TEAM,
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
                                    Toast.makeText(context, "创建团队成功！", Toast.LENGTH_SHORT).show();
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(context, "创建团队失败！", Toast.LENGTH_SHORT).show();
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
                map.put("teamName", teamName);
                map.put("teamDescription",teamDescription);
                map.put("leaderName", LocalUserInfo.userName);
                map.put("userId",LocalUserInfo.userId+"");
                Log.e("send data",map.toString());
                return map;
            }
        };

        VolleyController.getInstance(context).addToRequestQueue(req);
    }


    private class ClickListener implements View.OnClickListener{
        String teamName;
        String teamDescription;
        private int userId;
        ClickListener(int userId){
            super();
            this.userId = userId;
        }
        @Override
        public void onClick(View view){
            int id = view.getId();
            EditText teamNameED = (EditText) findViewById(R.id.create_team_name);
            teamName = teamNameED.getText().toString();
            EditText teamDescriptionED = (EditText) findViewById(R.id.create_team_deacription);
            teamDescription = teamDescriptionED.getText().toString();
            switch (id){
                case R.id.create_team_create_button:
                    clickListenerInterface.doConfirm(userId, teamName, teamDescription);
                    break;
                case R.id.create_team_cancel_button:
                    clickListenerInterface.doCancel();
                    break;
            }
        }
    }
}