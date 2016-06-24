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

import Bean.TeamBean;

/**
 * Created by Lawliet on 2016/5/29.
 */
public class FragmentTeams extends ListFragment implements MyFragmentInterface {
    TeamsAdapter teamsAdapter;
    private int userId;

    public void setUser_id(int id){
        this.userId = id;
    }

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        getServerData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.teams,container,false);
    }

    public void getServerData(){
        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_GET_TEAM_INFO,
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
                                    setTeamInfo(jsonArray);
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(getActivity(), "未加入任何团队！", Toast.LENGTH_SHORT).show();
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
                map.put("userId", LocalUserInfo.userId+"");
                Log.e("send data",map.toString());
                return map;
            }
        };

        VolleyController.getInstance(getActivity()).addToRequestQueue(req);

    }

    private void setTeamInfo(JSONArray jsonArray)
    {
        List<TeamBean> data = new ArrayList<>();

        for(int i = 0 ; i<jsonArray.length();i++)
        {
            try
            {
                JSONObject json = jsonArray.getJSONObject(i);
                TeamBean teamBean = new TeamBean();
                teamBean.setId(json.getInt("team_id"));
                //  teamBean.setLeaderId();
                teamBean.setLeaderName(json.getString("leader_name"));
                teamBean.setName(json.getString("team_name"));
                teamBean.setDescription(json.getString("description"));
                teamBean.setNumberOfMember(json.getInt("member_count"));
                data.add(teamBean);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }
        teamsAdapter = new TeamsAdapter(getActivity(),data, userId);
        setListAdapter(teamsAdapter);
    }

}
