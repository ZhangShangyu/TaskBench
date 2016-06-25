package com.example.lawliet.taskbench;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.lawliet.taskbench.Global.Constant;
import com.example.lawliet.taskbench.ServerTask.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_user_name;
    private EditText et_password;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_user_name = (EditText)findViewById(R.id.reg_et_user_id);
        et_password = (EditText)findViewById(R.id.reg_et_password);
        dialog = new ProgressDialog(this);
    }

    public void clickRegister(View v)
    {
        dialog.setMessage("正在注册...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        final String userName = et_user_name.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_REGISTER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        dialog.dismiss();

                        Log.e("TAG get data ",response);
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            int code = json.getInt("code");
                            {
                                if(code == 1)
                                {
                                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                                    startLogin();
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(RegisterActivity.this,"注册失败,账号重复！",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this,"服务器返回码错误！",Toast.LENGTH_SHORT).show();
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
                dialog.dismiss();
                Toast.makeText(RegisterActivity.this,"服务器错误！！",Toast.LENGTH_SHORT).show();
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
                map.put("password", password);
                Log.e("send data",map.toString());
                return map;
            }
        };

        VolleyController.getInstance(this).addToRequestQueue(req);


    }

    private void startLogin()
    {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
