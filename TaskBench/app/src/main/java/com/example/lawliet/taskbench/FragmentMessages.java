package com.example.lawliet.taskbench;

import android.os.Bundle;
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

import Bean.InviteBean;


public class FragmentMessages extends ListFragment implements MyFragmentInterface {


    public FragmentMessages() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getServerData();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.messages, container, false);
    }

    public void getServerData()
    {
        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_GET_MSG,
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
                                    setMsgInfo(jsonArray);
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(getActivity(), "无任何邀请信息！", Toast.LENGTH_SHORT).show();
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
                map.put("userName", LocalUserInfo.userName);
                Log.e("send data",map.toString());
                return map;
            }
        };

        VolleyController.getInstance(getActivity()).addToRequestQueue(req);
    }

    private void setMsgInfo(JSONArray jsonArray)
    {
        List<InviteBean> messageData = new ArrayList<>();
        for(int i = 0 ; i<jsonArray.length();i++)
        {
            try
            {
                JSONObject json = jsonArray.getJSONObject(i);
                InviteBean inviteBean = new InviteBean();
                inviteBean.setIsTask(false);
                inviteBean.setMsgId(json.getInt("invite_id"));
                inviteBean.setTeamId(json.getInt("team_id"));
                inviteBean.setTaskName(json.getInt("team_id")+"");
                inviteBean.setDescription("wants to invite you to team"+json.getString("team_name"));
                inviteBean.setName(json.getString("leader_name"));
                inviteBean.setTeamName(json.getString("team_name"));
                inviteBean.setMessageState(1);
                messageData.add(inviteBean);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
        MessageAdapter messageAdapter = new MessageAdapter(getActivity(),messageData);
        setListAdapter(messageAdapter);

    }




}



