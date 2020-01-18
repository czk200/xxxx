package com.example.ostatniaszansa;

public class Model {
    String id,team1,team2,Date,team1score,team2score,matchstatus,goal;

    public Model(String id, String team1, String team2,String Date,String team1score, String team2score,String matchstatus,String goal) {
        this.id = id;
        this.Date=Date;
        this.team1 = team1;
        this.team2 = team2;
        this.team1score=team1score;
        this.team2score=team2score;
        this.matchstatus=matchstatus;
        this.goal=goal;
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
    public String getDate() {
        return Date;
    }
    public String getteam1score() {
        return team1score;
    }
    public String getteam2score() {
        return team2score;
    }
    public String getMatchstatus() {
        return matchstatus;
    }
    public String getGoal() {
        return goal;
    }
}
