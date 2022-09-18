package com.example.villvaycodingchallengecodingwk22.dto;

import lombok.Data;

@Data
public class Standing {
    private int rank;
    private String team;
    private int matchesPlayed;
    private int matchesLost;
    private int matchesWon;
    private float totalRunsScored;
    private float totalOversFaced;
    private float totalRunsConceded;
    private float totalOversBowled;
    private double netRunRate;
}
