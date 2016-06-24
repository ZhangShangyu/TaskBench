package com.example.lawliet.taskbench;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.lawliet.taskbench.Global.LocalUserInfo;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

import style.CreateTeamDialog;

public class MainActivity extends ActionBarActivity {
    Context context = this;
    private FloatingActionButton creatTeam;
    private FloatingActionButton logOut;
    private FloatingActionMenu menu;
    private Toolbar toolbar;
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private SystemBarTintManager barTintManager;
    private MyPagerAdapter myPagerAdapter;
    private FragmentTasks fragmentTasks;
    private FragmentTeams fragmentTeams;
    private FragmentMessages fragmentMessages;
    private int currentColor;
    private Drawable oldBackground = null;
    private ArrayList<MyFragmentInterface> fragmentList=new ArrayList<MyFragmentInterface>() ;
//    private Fragment fragmentMe;
//    private RadioGroup radioGroup;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        setSupportActionBar(toolbar);
        barTintManager = new SystemBarTintManager(this);
        barTintManager.setStatusBarTintEnabled(true);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myPagerAdapter);
        tabs.setViewPager(pager);
        pager.setCurrentItem(0);
        changeColor(getResources().getColor(R.color.blue2));
        tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Toast.makeText(MainActivity.this, "Tab reselected: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        menu = (FloatingActionMenu) findViewById(R.id.menu_blue);
        logOut = (FloatingActionButton) findViewById(R.id.logOut);
        creatTeam = (FloatingActionButton) findViewById(R.id.creatTeam);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("bluemenu", "logOut");
                fragmentList.get(pager.getCurrentItem()).getServerData();
            }
        });
        logOut.setLabelText("Log off");
        logOut.setLabelVisibility(100);
        creatTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTeamDialog createTeamDialog = new CreateTeamDialog(context, LocalUserInfo.userId);
                createTeamDialog.setCanceledOnTouchOutside(true);
                createTeamDialog.setTitle("Create Your Team");
                createTeamDialog.show();
                Log.d("bluemenu", "creatTeam");
            }
        });
        creatTeam.setLabelText("Create a new team");
        creatTeam.setLabelVisibility(10);

    }

    private void changeColor(int newColor) {
        tabs.setBackgroundColor(newColor);
        barTintManager.setTintColor(newColor);
        // change ActionBar color just if an ActionBar is available
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        if (oldBackground == null) {
            getSupportActionBar().setBackgroundDrawable(ld);
        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
            getSupportActionBar().setBackgroundDrawable(td);
            td.startTransition(200);
        }

        oldBackground = ld;
        currentColor = newColor;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Tasks", "Teams", "Messages"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragmentTasks = new FragmentTasks();
                    fragmentTasks.setId(user_id);
                    fragmentList.add(fragmentTasks);
                    return fragmentTasks;

                case 1:
                    fragmentTeams = new FragmentTeams();
                    fragmentTeams.setUser_id(102);
                    fragmentList.add(fragmentTeams);
                    return fragmentTeams;

                case 2:
                    fragmentMessages =new FragmentMessages();
                    fragmentList.add(fragmentMessages);
                    return fragmentMessages;

                default:
                    fragmentTasks = new FragmentTasks();
                    fragmentTasks.setId(user_id);
                    fragmentList.add(fragmentTasks);
                    return fragmentTasks;
            }
        }
    }

//    public void initView(){
//        fragmentTasks = new FragmentTasks();
//        fragmentTasks.setIsTeam(false);
//        fragmentTasks.setId(user_id);
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_context, fragmentTasks).commit();
//        radioGroup = (RadioGroup)findViewById(R.id.main_radiogroup);
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.rb_tasks:
//                        fragmentTasks = new FragmentTasks();
//                        fragmentTasks.setId(user_id);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_context, fragmentTasks).commit();
//                        break;
//                    case R.id.rb_teams:
//                        fragmentTeams = new FragmentTeams();
//                        fragmentTeams.setUser_id(102);
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_context,fragmentTeams).commit();
//                        break;
//                    case R.id.rb_messages:
//                        fragmentMessages =new FragmentMessages();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_context,fragmentMessages).commit();
//                        break;
//                    case R.id.rb_me:
//                        fragmentMe = new FragmentMe();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_context,fragmentMe).commit();
//                        break;
//                    default:
//                            break;
//                }
//            }
//        });
//
//
//    }
}
