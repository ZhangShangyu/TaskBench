package com.example.lawliet.taskbench;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.lawliet.taskbench.Constant.Constant;
import com.example.lawliet.taskbench.ServerTask.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText et_user_id ;
    private EditText et_password;
    private ProgressDialog dialog;

    private String userId;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("login", Activity.MODE_PRIVATE);
        boolean ifLogin = sharedPreferences.getBoolean("ifLogin", false);
        int userid = sharedPreferences.getInt("userid", -1);
        if(ifLogin && (userid != -1)){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userid", userid);
            startActivity(intent);
        }
        setContentView(R.layout.activity_login);
    }
     public void clickLogin(View v)
    {
        dialog.setMessage("正在登录...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        userId = et_user_id.getText().toString().trim();
        password = et_password.getText().toString().trim();

        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_LOGIN,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e("TAG get data ",response);
                        dialog.dismiss();
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            int code = json.getInt("code");
                            {
                                if(code == 1)
                                {
                                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                                    startMainActivity();
                                }
                                else if(code == 2)
                                {
                                    Toast.makeText(LoginActivity.this,"登录失败,账号或密码错误！",Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this,"服务器返回码错误！",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this,"服务器错误！！",Toast.LENGTH_SHORT).show();
                        Log.e("server data error ", error.getMessage(), error);
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        //在这里设置需要post的参数
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("userName", userId);
                        map.put("password", password);
                        Log.e("send data",map.toString());
                        return map;
                    }
                };

                VolleyController.getInstance(this).addToRequestQueue(req);

    }

    public void startMainActivity()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    public  void clickRegister(View v)
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
