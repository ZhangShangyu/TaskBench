package com.example.lawliet.taskbench;

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
import com.example.lawliet.taskbench.Global.Constant;
import com.example.lawliet.taskbench.Global.LocalUserInfo;
import com.example.lawliet.taskbench.ServerTask.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private String userName;
    private String password;
    private SharedPreferences sharedPreferences;
    public static String userId = "loginToMainUserId";
    private EditText userNameET;
    private EditText passwordET;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        sharedPreferences = getSharedPreferences("task_bench_login", Activity.MODE_PRIVATE);
        boolean ifLogin = sharedPreferences.getBoolean("ifLogin", false);
        int userid = sharedPreferences.getInt("userid", -1);

        Intent intent = new Intent(this, MainActivity.class);
        if(ifLogin && (userid != -1)){
            intent.putExtra(userId, userid);
            startActivity(intent);
            finish();
        }
        */
        setContentView(R.layout.activity_login);
        userNameET = (EditText) findViewById(R.id.loginUserName);
        passwordET = (EditText) findViewById(R.id.loginPassword);
        dialog = new ProgressDialog(this);

    }

    private void login(final String userName, final String password)
    {
        if(userName.equals(""))
        {
            dialog.dismiss();
            Toast.makeText(LoginActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.equals(""))
        {
            dialog.dismiss();
            Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest req = new StringRequest(Request.Method.POST, Constant.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG get data ", response);
                        dialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            int code = json.getInt("code");
                            {
                                if (code == 1) {
                                    LocalUserInfo.userId = json.getInt("userId");
                                    LocalUserInfo.userName = userName;
                                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                    startMainActivity();
                                } else if (code == 2) {
                                    Toast.makeText(LoginActivity.this, "登录失败,账号或密码错误！", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "服务器返回码错误！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("json解析错误", "json格式错误");
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "服务器错误！！", Toast.LENGTH_SHORT).show();
                Log.e("server data error ", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("userName", userName);
                map.put("password", password);
                Log.e("send data", map.toString());
                return map;
            }
        };

        VolleyController.getInstance(this).addToRequestQueue(req);
    }


    public void clickLogin(View v) {
        dialog.setMessage("正在登录...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        userName = userNameET.getText().toString().trim();
        password = passwordET.getText().toString().trim();

        login(userName,password);

    }

    public void startMainActivity()
    {
        /*
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userid",LocalUserInfo.userId);
        editor.putBoolean("ifLogin", true);
        editor.commit();*/

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
       // finish();

    }


    public  void clickRegister(View v)
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }


}
