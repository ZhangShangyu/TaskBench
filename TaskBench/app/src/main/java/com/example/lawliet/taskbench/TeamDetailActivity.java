package com.example.lawliet.taskbench;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lawliet.taskbench.Global.LocalUserInfo;

import Bean.InviteBean;
import style.InviteDialog;

public class TeamDetailActivity extends AppCompatActivity {
    Context context;
    private FragmentTasks fragmentTasks;
    private int teamId;
    private int userId;
    private String leaderName;
    private boolean isLeader;
    private String description;
    private InviteDialog inviteDialog;
    private String teamName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = this;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        teamId = intent.getIntExtra("teamId", 0);
        userId = intent.getIntExtra(TeamsAdapter.userIdS, 0);
        leaderName = intent.getStringExtra("teamLeaderName");
        description = intent.getStringExtra(TeamsAdapter.teamDes);
        teamName = intent.getStringExtra("teamName");

        setContentView(R.layout.activity_team_detail);
        TextView teamDescription = (TextView) findViewById(R.id.team_detail_description);
        teamDescription.setText(description);
        initView();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(teamName);
        toolbar.setBackgroundColor(getResources().getColor(R.color.blue2));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_na);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView(){

        if(leaderName.equals(LocalUserInfo.userName)){
            this.isLeader = true;
        }else
        this.isLeader = false;

        fragmentTasks = new FragmentTasks();
        fragmentTasks.setIsTeam(true);
        fragmentTasks.setId(teamId);
        getSupportFragmentManager().beginTransaction().replace(R.id.team_detail_tasks, fragmentTasks).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.team_detail_actionbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if(!this.isLeader){
            menu.findItem(R.id.action_bar_invite).setVisible(false);
            menu.findItem(R.id.action_bar_distribute).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        if(this.isLeader){
//            MenuInflater inflater = getMenuInflater();
//            inflater.inflate(R.menu.team_detail_actionbar, menu);
//        }
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_bar_invite:
                inviteDialog = new InviteDialog(this,teamId, leaderName,teamName);
                inviteDialog.setCanceledOnTouchOutside(true);
                inviteDialog.show();
                break;
            case R.id.action_bar_distribute:
                Intent intent = new Intent(this,DistributeActivity.class);
                intent.putExtra("distributeTeamId",teamId);
                intent.putExtra("distributeLeaderId",teamName);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
