package com.example.lawliet.taskbench;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.swipe.SwipeLayout;
import com.example.lawliet.taskbench.Global.Constant;
import com.example.lawliet.taskbench.Global.LocalUserInfo;
import com.example.lawliet.taskbench.ServerTask.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.InviteBean;
import Bean.Task;

/**
 * Created by Lawliet on 2016/6/12.
 */
class MessageCell{
    public TextView name;
    public TextView teamName;
    public TextView taskName;
    public LinearLayout leftWrapper;
    public LinearLayout rightWrapper;
    public SwipeLayout messageWrapper;
}

public class MessageAdapter extends BaseAdapter{
    private List<InviteBean> data;
    private Context context;
    private LayoutInflater mInflater = null;
    private AlertDialog.Builder _builder;
    public MessageAdapter(Context context,List<InviteBean> data){
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

    private void sendAgreeData(final int msgId,final int teamId)
    {
        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_HANDLE_MSG,
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
                                    Toast.makeText(context, "加入该团队成功！", Toast.LENGTH_SHORT).show();
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(context, "加入该团队失败！", Toast.LENGTH_SHORT).show();
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
                map.put("userId", LocalUserInfo.userId+"");
                map.put("msgId",msgId+"");
                map.put("teamId",teamId+"");

                Log.e("send data",map.toString());
                return map;
            }
        };

        VolleyController.getInstance(context).addToRequestQueue(req);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        _builder = new AlertDialog.Builder(context);
        convertView = mInflater.inflate(R.layout.message,null);
        MessageCell messageCell = new MessageCell();
        messageCell.teamName = (TextView) convertView.findViewById(R.id.message_teamName);
        messageCell.name = (TextView) convertView.findViewById(R.id.message_name);
        messageCell.taskName = (TextView) convertView.findViewById(R.id.message_taskName);

        messageCell.leftWrapper = (LinearLayout) convertView.findViewById(R.id.wrapper_left);
        messageCell.rightWrapper = (LinearLayout) convertView.findViewById(R.id.wrapper_right);

        final InviteBean inviteBean =data.get(position);
        messageCell.teamName.setText(inviteBean.getTeamName());
        messageCell.name.setText(inviteBean.getName());
        switch(inviteBean.getMessageState()){
            case 1:
                View acceptW = new View(context);
                acceptW.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                acceptW.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击红色

                    }
                });
                View refuseW = new View(context);
                refuseW.setBackgroundColor(context.getResources().getColor(R.color.blue3));
                refuseW.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendAgreeData(data.get(position).getMsgId(),data.get(position).getTeamId());
                        //点击蓝色
                    }
                });
                messageCell.rightWrapper.addView(acceptW);
                messageCell.leftWrapper.addView(refuseW);


                if(inviteBean.isTask()){
                    messageCell.taskName.setText(" want to distribute the task " + inviteBean.getTaskName() + " to you.");
                }
                else{
                    messageCell.taskName.setText(" want to invite you to the team " + inviteBean.getTeamName() + ".");
                }
                break;
            case 2:
                Button deleteW = new Button(context);
                deleteW.setWidth(100);
                deleteW.setText("Delete");
                deleteW.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //shanchu
                    }
                });
                messageCell.rightWrapper.addView(deleteW);
                if(inviteBean.isTask()){
                    messageCell.taskName.setText(" has accepted your distribution of task " + inviteBean.getTaskName() + ".");
                }
                else{
                    messageCell.taskName.setText(" has accepted your invite to the team" + inviteBean.getTeamName() + ".");
                }
                break;
            case 3:
                Button deleteW1 = new Button(context);
                deleteW1.setText("Delete");
                deleteW1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //shanchu
                    }
                });
                messageCell.rightWrapper.addView(deleteW1);
                if(inviteBean.isTask()){
                    messageCell.taskName.setText(" has refused your distribution of task " + inviteBean.getTaskName() + ".");
                }
                else{
                    messageCell.taskName.setText(" has refused your invite to the team" + inviteBean.getTeamName() + ".");
                }
                break;
        }



        messageCell.taskName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("longclick!", "long~~~~~~~~");
                _builder.setTitle("Description:");
                _builder.setMessage(inviteBean.getDescription());
                _builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                _builder.create().show();
                return true;
            }
        });

        messageCell.messageWrapper = (SwipeLayout) convertView.findViewById(R.id.message_wrapper);
        messageCell.messageWrapper.setShowMode(SwipeLayout.ShowMode.PullOut );
        messageCell.messageWrapper.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.bottom_wrapper));


        return  convertView;
    }
}
