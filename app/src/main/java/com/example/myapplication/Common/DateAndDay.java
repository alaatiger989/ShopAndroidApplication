package com.example.myapplication.Common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateAndDay {
    public String getDate()
    {
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        return df.format(c);
    }
    public String getDay()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        return sdf.format(d);
    }
}
