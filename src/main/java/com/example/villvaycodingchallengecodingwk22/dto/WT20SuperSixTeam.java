package com.example.villvaycodingchallengecodingwk22.dto;

import com.example.villvaycodingchallengecodingwk22.enums.WT20SuperSixTeamEnum;
import lombok.Data;

@Data
public class WT20SuperSixTeam {
    //Team, Matches_Played, Matches_Lost, Matches_Won, Points, RunsScored, OversFaced, RunsConceded, OversBowled, NR
    private String team;
    private WT20SuperSixTeamEnum code;
    private int matchesPlayed;
    private int matchesLost;
    private int matchesWon;
    private int points;
    private int runsScored;
    private int oversFaced;
    private int runsConceded;
    private int oversBowled;
    private float nRR;
}
