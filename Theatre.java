package com.example.week9;

import java.util.ArrayList;

public class Theatre {

    private String  ID;
    private String name;

    public Theatre(String _ID, String _name){
        name = _name;
        ID = _ID;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

}
