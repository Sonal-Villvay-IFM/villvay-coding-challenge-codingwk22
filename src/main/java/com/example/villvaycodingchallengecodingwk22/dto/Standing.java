package com.example.villvaycodingchallengecodingwk22.dto;

public class Standing {
    private String rank;
    private String team;
    private int matchesPlayed;
    private int matchesLost;
    private int matchesWon;
    private float totalRunsScored;
    private float totalOversFaced;
    private float totalRunsConceded;
    private float totalOversBowled;
    private float netRunRate;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getMatchesLost() {
        return matchesLost;
    }

    public void setMatchesLost(int matchesLost) {
        this.matchesLost = matchesLost;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(int matchesWon) {
        this.matchesWon = matchesWon;
    }

    public float getTotalRunsScored() {
        return totalRunsScored;
    }

    public void setTotalRunsScored(float totalRunsScored) {
        this.totalRunsScored = totalRunsScored;
    }

    public float getTotalOversFaced() {
        return totalOversFaced;
    }

    public void setTotalOversFaced(float totalOversFaced) {
        this.totalOversFaced = totalOversFaced;
    }

    public float getTotalRunsConceded() {
        return totalRunsConceded;
    }

    public void setTotalRunsConceded(float totalRunsConceded) {
        this.totalRunsConceded = totalRunsConceded;
    }

    public float getTotalOversBowled() {
        return totalOversBowled;
    }

    public void setTotalOversBowled(float totalOversBowled) {
        this.totalOversBowled = totalOversBowled;
    }

    public float getNetRunRate() {
        return netRunRate;
    }

    public void setNetRunRate(float netRunRate) {
        this.netRunRate = netRunRate;
    }
}
