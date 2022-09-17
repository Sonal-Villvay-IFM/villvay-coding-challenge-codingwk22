package com.example.villvaycodingchallengecodingwk22.dto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {
    protected List<String> teamDetails;
    BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/villvaycodingchallengecodingwk22/data/data.csv"));
    private String line = "";
    private final String splitBy = ",";
    private int team = 1;

    public ReadCSV() throws FileNotFoundException {
    }

    public List<String> getCountry(String country_name) {
        teamDetails = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null)
            {
                String[] t20_teams = line.split(splitBy);
                // Team, Matches_Played, Matches_Lost, Matches_Won, Points, RunsScored, OversFaced, RunsConceded, OversBowled, NR
                if (t20_teams[0].equals(country_name)) {
                    for (int i = 0; i < 10; i++)
                        teamDetails.add(t20_teams[i]);
                }
            }
            return teamDetails;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void read() throws FileNotFoundException {
        try {
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] t20_teams = line.split(splitBy);    // Team, Matches_Played, Matches_Lost, Matches_Won, Points, RunsScored, OversFaced, RunsConceded, OversBowled, NRR
                System.out.println("Team 0" + team +
                        " [Country =" + t20_teams[0] +
                        ", Matches_Played=" + t20_teams[1] +
                        ", Matches_Lost=" + t20_teams[2] +
                        ", Matches_Won=" + t20_teams[3] +
                        ", Points= " + t20_teams[4] +
                        ", RunsScored= " + t20_teams[5] +
                        ", OversFaced= " + t20_teams[6] +
                        ", RunsConceded= " + t20_teams[7] +
                        ", OversBowled= " + t20_teams[8] +
                        ", NRR= " + t20_teams[9] + "]");
                team++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
