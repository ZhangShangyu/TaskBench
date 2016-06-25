package com.example.lawliet.taskbench;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by MAC on 16/6/24.
 */
public class CalPercent
{
    public double getPercent(String startDate,String deadline)
    {
    	Date startDateD = null;
        Date deadlineD = null;

        Calendar calS = Calendar.getInstance();
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            startDateD = sdf.parse(str);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        calS.setTime(startDateD);

        Calendar calD = Calendar.getInstance();
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            deadlineD = sdf.parse(str);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        calD.setTime(deadlineD);

		java.util.Date now = new java.util.Date();
        if(startDateD.getTime() > deadlineD.getTime()) return -1;
        else if(startDateD.getTime() > now.getTime()) return 0;
        else if(now.getTime() > deadlineD.getTime()) return 1;


        Calendar calN = Calendar.getInstance();
        java.util.Date now = new java.util.Date();
        calN.setTime(new Date(now.getTime()));
        long sl = calS.getTimeInMillis();
        long dl = calD.getTimeInMillis();
        long nl = calN.getTimeInMillis();
        double dayAll =  (dl - sl);
        double dayPast =  (nl - sl);
        double percent = dayPast/(dayAll+1);

        return  percent;
    }

        public boolean ifOverstep(String date){
        Date dateToTest = null;
        Date dateLatest = null;
        Date dateEarliest = null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateToTest = sdf.parse(date);
            dateLatest = sdf.parse("2020-1-1");
            dateEarliest = sdf.parse("2015-1-1");
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        if(dateToTest.getTime() < dateEarliest.getTime()){
            System.out.println("false1");
            return false;}
        else if(dateToTest.getTime() > dateLatest.getTime()){
            System.out.println("false2");
            return false;}
        else{
            System.out.println("true");
            return true;}
    }

}
