package com.example.studentregister.calendar;

public class CustomDate {

    private int year;
    private int month;
    private int day;

    public CustomDate() {
    }

    public CustomDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public static boolean isEmpty(CustomDate customDate) {
        return customDate == null
                || customDate.getYear() <= 0
                || customDate.getMonth() < 1
                || customDate.getMonth() > 12
                || customDate.getDay() < 1
                || customDate.getDay() > 31;
    }
}