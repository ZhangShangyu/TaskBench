package com.example.lawliet.taskbench;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import Bean.TeamBean;

/**
 * Created by Lawliet on 2016/6/11.
 */

class TeamCell{
    public TextView teamName;
    public TextView leaderName;
    public TextView numberOfMember;
}

public class TeamsAdapter extends BaseAdapter{
    private List<TeamBean> data = null;
    private LayoutInflater mInflater = null;
    private Context context;
    private int userId;
    public static String teamId = "teamListviewToTeamDetailTeamId";
    public static String teamLeaderId = "teamListviewToTeamDetailTeamLeaderId";
    public static String teamDes = "teamListviewToTeamDetailTeamDescription";
    public static String userIdS = "teamListviewToTeamDetailUserId";
    public static String teamName = "teamListviewToTeamDetailteamName";

    public TeamsAdapter(Context context,List<TeamBean> data, int userId){
        super();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.userId = userId;
        this.data=data;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        convertView = mInflater.inflate(R.layout.team,null);
        TeamCell teamCell = new TeamCell();
        teamCell.teamName = (TextView) convertView.findViewById(R.id.team_teamname);
        teamCell.leaderName = (TextView) convertView.findViewById(R.id.team_leadername);
        teamCell.numberOfMember = (TextView) convertView.findViewById(R.id.team_membernumber);

        teamCell.teamName.setText(data.get(position).getName());
        teamCell.leaderName.setText(data.get(position).getLeaderName());
        teamCell.numberOfMember.setText(data.get(position).getNumberOfMember()+"");

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.team_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(context,TeamDetailActivity.class);
                intent.putExtra("teamId",data.get(position).getId());
                intent.putExtra(userIdS, userId);
                intent.putExtra("teamLeaderName", data.get(position).getLeaderName());
                intent.putExtra(teamDes,data.get(position).getDescription());
                intent.putExtra("teamName",data.get(position).getName());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

}
