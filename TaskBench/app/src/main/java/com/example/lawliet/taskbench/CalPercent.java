package com.example.lawliet.taskbench;

import java.util.Date;


import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalPercent {
    public double getPercent(String startDate,String deadline)
    {
        if(ifOverstep(startDate) && ifOverstep(deadline)){
//        if (notOverstep_stub(startDate) && notOverstep_stub(deadline)){
            Date startDateD = null;
            Date deadlineD = null;

            Calendar calS = Calendar.getInstance();
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDateD = sdf.parse(startDate);
            }
            catch (ParseException e)
            {
                System.out.println(e.getMessage());
                return -1;
            }
            calS.setTime(startDateD);

            Calendar calD = Calendar.getInstance();
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                deadlineD = sdf.parse(deadline);
            }
            catch (ParseException e)
            {
                System.out.println(e.getMessage());
                return -1;
            }
            calD.setTime(deadlineD);

            Date current = new Date();

            if(startDateD.getTime() > deadlineD.getTime())
                return -1;
            else if(startDateD.getTime() > current.getTime())
                return 0;
            else if(current.getTime() > deadlineD.getTime())
                return 1;

            Calendar calN = Calendar.getInstance();
//            java.util.Date now = new java.util.Date();
//            calN.setTime(new Date(now.getTime()));
            calN.setTime(new Date());

            long sl = calS.getTimeInMillis();
            long dl = calD.getTimeInMillis();
            long nl = calN.getTimeInMillis();
            double dayAll =  (dl - sl);
            double dayPast =  (nl - sl);
            double percent = dayPast/(dayAll+1);

            return  percent;
        }else
            return -1;
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
//
//    public static Date getNowDate_stub(){
//        Date nowDate = null;
//        try
//        {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            nowDate = sdf.parse("2016-6-1");
//        }
//        catch (ParseException e)
//        {
//            System.out.println(e.getMessage());
//        }
//        return nowDate;
//    }

//    public static boolean notOverstep_stub(String date){
//        Date dateToTest = null;
//        Date dateLatest = null;
//        Date dateEarliest = null;
//        try
//        {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            dateToTest = sdf.parse(date);
//            dateLatest = sdf.parse("2020-1-1");
//            dateEarliest = sdf.parse("2015-1-1");
//        }
//        catch (ParseException e)
//        {
//            System.out.println(e.getMessage());
//        }
//        if(dateToTest.getTime() < dateEarliest.getTime()){
//            System.out.println("false1");
//            return false;}
//        else if(dateToTest.getTime() > dateLatest.getTime()){
//            System.out.println("false2");
//            return false;}
//        else{
//            System.out.println("true");
//            return true;}

//        Calendar calD = Calendar.getInstance();
//        calD.setTime(dateToTest);
//        Calendar calL = Calendar.getInstance();
//        calL.setTime(dateLatest);
//        Calendar calE = Calendar.getInstance();
//        calE.setTime(dateEarliest);
//
//
//        if(calD.compareTo(calE) == -1){//if calD is earlier than calE
//            return false;
//        }else if (calD.compareTo(calL) == 1){ //if calD is later than calL
//            return false;
//        }else
//            return true;
//    }
}
