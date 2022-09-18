package com.example.villvaycodingchallengecodingwk22.service;

import com.example.villvaycodingchallengecodingwk22.dto.WT20SuperSixTeam;
import lombok.Data;

import java.util.Date;

@Data
public class Fixture {
    WT20SuperSixTeam team1;
    WT20SuperSixTeam team2;
    Date fixtureDate;
}
