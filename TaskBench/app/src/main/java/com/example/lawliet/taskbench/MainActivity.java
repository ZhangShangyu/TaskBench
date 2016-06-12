package com.example.lawliet.taskbench;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity {
    private Fragment fragmentTasks;
    private Fragment fragmentTeams;
    private Fragment fragmentMessages;
    private Fragment fragmentMe;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        fragmentTasks = new FragmentTasks();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_context, fragmentTasks).commit();
        radioGroup = (RadioGroup)findViewById(R.id.main_radiogroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_tasks:
                        fragmentTasks = new FragmentTasks();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_context, fragmentTasks).commit();
                        break;
                    case R.id.rb_teams:
                        fragmentTeams = new FragmentTeams();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_context,fragmentTeams).commit();
                        break;
                    case R.id.rb_messages:
                        fragmentMessages =new FragmentMessages();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_context,fragmentMessages).commit();
                        break;
                    case R.id.rb_me:
                        fragmentMe = new FragmentMe();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_context,fragmentMe).commit();
                        break;
                    default:
                            break;
                }
            }
        });


    }
}
