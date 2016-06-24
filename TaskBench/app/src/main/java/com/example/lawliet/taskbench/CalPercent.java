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
        Calendar calS = Calendar.getInstance();
        calS.setTime(getDate(startDate));
        Calendar calD = Calendar.getInstance();
        calD.setTime(getDate(deadline));
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

    private Date getDate(String str)
    {
        Date date=null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(str);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        return date;
    }

}
