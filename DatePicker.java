package com.example.week9;

import java.util.Calendar;

public class DatePicker {

    private static DatePicker instance;

    public static DatePicker getInstance() {
        if (instance == null) {
            instance = new DatePicker();
        }
        return instance;
    }

    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH +1);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day,month,year);
    }

    public String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";
        // Default
        return "JAN";
    }


}
