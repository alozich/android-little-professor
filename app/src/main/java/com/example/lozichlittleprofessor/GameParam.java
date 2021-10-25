package com.example.lozichlittleprofessor;

import java.util.Arrays;

public class GameParam {
    private String playerName;
    private String level;
    private String[] OperationsOn;

    public GameParam(){
        playerName = "The Little Professor";
        level = "Beginner";
        OperationsOn = new String[]{"Beginner"};
    }

    public GameParam(String name, String lvl, String[] ops)
    {
        playerName = name;
        level = lvl;
        OperationsOn = ops;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getLevel() {
        return level;
    }

    public String[] getOperationsOn()
    {
        return OperationsOn;
    }

    public void setPlayerName(String n)
    {
        playerName = n;
    }
    public void setLevel(String l)
    {
        level = l;
    }
    public void setOperations(String[] ops)
    {
        OperationsOn = ops;
    }

}
