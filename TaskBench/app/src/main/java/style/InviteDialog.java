package style;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lawliet.taskbench.Global.Constant;
import com.example.lawliet.taskbench.R;
import com.example.lawliet.taskbench.ServerTask.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lawliet on 2016/6/15.
 */
public class InviteDialog extends Dialog{
    private Context context;
    private int teamId;
    private String leaderName;
    private String teamName;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface{
        public void doConfirm(String userName, int teamId, String leaderName,String teamName);

        public void doCancel();
    }

    public InviteDialog(Context context, int teamId, String leaderName,String teamName){
        super(context);
        this.context = context;
        this.teamId = teamId;
        this.leaderName = leaderName;
        this.teamName = teamName;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        init();
    }

    public void init(){

        this.clickListenerInterface = new ClickListenerInterface() {
            @Override
            public void doConfirm(String userName, int teamId, String leaderName,String teamName) {
                //数据库操作
                sendInviteData(userName,teamId,leaderName,teamName);
                dismiss();
            }

            @Override
            public void doCancel() {
                dismiss();
            }
        };

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.invite_dialog, null);
        setContentView(view);

        Button invite = (Button) findViewById(R.id.invite_dialog_inviteButton);
        Button cancel = (Button) findViewById(R.id.invite_dialog_cancelButton);
        invite.setOnClickListener(new ClickListener(this.teamId, leaderName,teamName));
        cancel.setOnClickListener(new ClickListener(this.teamId,leaderName,teamName));

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    private void sendInviteData(final String userName, final int teamId, final String leaderName, final String teamName)
    {
        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_SEND_INVITE_MSG,
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
                                    Toast.makeText(context, "发送邀请信息成功！", Toast.LENGTH_SHORT).show();
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(context, "发送邀请信息失败！", Toast.LENGTH_SHORT).show();
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
                map.put("teamId", teamId+"");
                map.put("leaderName",leaderName);
                map.put("userName",userName);
                map.put("teamName",teamName);

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
        private String userName;
        private int teamId;
        private String leaderName;
        private String teamName;
        ClickListener(int teamId, String leaderName,String teamName){
            super();
            this.teamId = teamId;
            this.leaderName = leaderName;
            this.teamName = teamName;
        }
        @Override
        public void onClick(View view){
            int id = view.getId();
            EditText userNameED = (EditText) findViewById(R.id.invite_dialog_username);
            userName = userNameED.getText().toString();
            switch (id){
                case R.id.invite_dialog_inviteButton:
                    clickListenerInterface.doConfirm(userName, teamId, leaderName,teamName);
                    break;
                case R.id.invite_dialog_cancelButton:
                    clickListenerInterface.doCancel();
                    break;
            }
        }
    }
}
