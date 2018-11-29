package jdk_test.jdk_base;

import java.util.Calendar;
import java.util.Date;

public class CalendarTest {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        System.out.println("the year is: " + calendar.get(Calendar.YEAR));
        System.out.println("the mouth is: " + calendar.get(Calendar.MONTH));
        System.out.println("the day is: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("the hour is: " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("the minute is: " + calendar.get(Calendar.MINUTE));


    }
}
