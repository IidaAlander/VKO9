package com.example.week9;

public class Movie {

    private String time;
    private String name;

    public Movie(String _time, String _name){
        time = _time;
        name = _name;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

}

