package com.example.villvaycodingchallengecodingwk22.dto;

import com.example.villvaycodingchallengecodingwk22.enums.WT20SuperSixTeamEnum;
import lombok.Data;

import java.io.FileNotFoundException;

@Data
public class PointsTable {
    WT20SuperSixTeamEnum country;
    private double NRR;

    public PointsTable(String country, double NRR) {
        try {
            ReadCSV reader = new ReadCSV();
            this.NRR = NRR;
            this.country = reader.getCode(country);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}