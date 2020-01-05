package com.example.ostatniaszansa;

public class Model {
    String id,team1,team2;

    public Model(String id, String team1, String team2) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }
}
