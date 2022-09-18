package com.example.villvaycodingchallengecodingwk22.dto;

import com.example.villvaycodingchallengecodingwk22.enums.WT20SuperSixTeamEnum;
import com.example.villvaycodingchallengecodingwk22.exceptions.PredictionVagueException;
import com.example.villvaycodingchallengecodingwk22.service.Fixture;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReadCSV {
    private final String splitBy = ",";
    protected List<String> teamDetails;
    BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/villvaycodingchallengecodingwk22/data/data.csv"));
    private String line = "";
    private int team = 1;

    public ReadCSV() throws FileNotFoundException {
    }

    public List<String> getCountry(String country_name) {
        teamDetails = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) {
                String[] t20_teams = line.split(splitBy);
                // Team, Matches_Played, Matches_Lost, Matches_Won, Points, RunsScored, OversFaced, RunsConceded, OversBowled, NR
                if (t20_teams[0].equalsIgnoreCase(country_name)) {
                    for (int i = 0; i < 10; i++)
                        teamDetails.add(t20_teams[i]);
                }
            }
            return teamDetails;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teamDetails;
    }

    public List<Standing> getPointsTable() throws IOException {
        int rank = 1;
        List<Standing> pointsTable = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] t20_teams = line.split(splitBy);
            // Team, Matches_Played, Matches_Lost, Matches_Won, Points, RunsScored, OversFaced, RunsConceded, OversBowled, NR
            Standing stand = new Standing();
            stand.setRank(rank);
            stand.setTeam(t20_teams[0]);
            stand.setMatchesPlayed(Integer.parseInt(t20_teams[1]));
            stand.setMatchesLost(Integer.parseInt(t20_teams[2]));
            stand.setMatchesWon(Integer.parseInt(t20_teams[3]));
            stand.setTotalRunsScored(Float.parseFloat(t20_teams[5]));
            stand.setTotalOversFaced(Float.parseFloat(t20_teams[6]));
            stand.setTotalRunsConceded(Float.parseFloat(t20_teams[7]));
            stand.setTotalOversBowled(Float.parseFloat(t20_teams[8]));
            stand.setNetRunRate(Double.parseDouble(t20_teams[9]));

            pointsTable.add(stand);
        }
        return sortAndRank(pointsTable);
    }

    public List<Standing> sortAndRank(List<Standing> pointsTable) {
        int rank = 1;
        // sort based on NRR
        pointsTable.stream().sorted(Comparator.comparingDouble(Standing::getNetRunRate)).collect(Collectors.toList());
        // assign a rank
        for (Standing stand : pointsTable) {
            stand.setRank(rank);
            rank++;
        }
        return pointsTable;
    }

    public WT20SuperSixTeam getTeamDetails(String country_name) {
        WT20SuperSixTeam team = new WT20SuperSixTeam();
        try {
            BufferedReader buffer = new BufferedReader(new FileReader("src/main/java/com/example/villvaycodingchallengecodingwk22/data/data.csv"));
            while ((line = buffer.readLine()) != null) {
                String[] t20_teams = line.split(splitBy);
                // Team, Matches_Played, Matches_Lost, Matches_Won, Points, RunsScored, OversFaced, RunsConceded, OversBowled, NRR
                if (country_name.equalsIgnoreCase(t20_teams[0])) {
                    team.setTeam(t20_teams[0]);
                    team.setCode(getCode(t20_teams[0]));
                    team.setMatchesPlayed(Integer.parseInt(t20_teams[1]));
                    team.setMatchesLost(Integer.parseInt(t20_teams[2]));
                    team.setMatchesWon(Integer.parseInt(t20_teams[3]));
                    team.setPoints(Integer.parseInt(t20_teams[4]));
                    team.setRunsScored(Integer.parseInt(t20_teams[5]));
                    team.setOversFaced(Integer.parseInt(t20_teams[6]));
                    team.setRunsConceded(Integer.parseInt(t20_teams[7]));
                    team.setOversBowled(Integer.parseInt(t20_teams[8]));
                    team.setNRR(Float.parseFloat(t20_teams[9]));
                }
            }
            return team;
        } catch (IOException e) {
            System.out.println("Error in reading Data.csv");
            e.printStackTrace();
        }
        return team;
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

    public WT20SuperSixTeamEnum getCode(String country) {
        WT20SuperSixTeamEnum code;
        switch (country) {
            case "Sri Lanka":
                code = WT20SuperSixTeamEnum.SL;
                break;
            case "Australia":
                code = WT20SuperSixTeamEnum.AUS;
                break;
            case "Afghanistan":
                code = WT20SuperSixTeamEnum.AFG;
                break;
            case "India":
                code = WT20SuperSixTeamEnum.IND;
                break;
            case "Pakistan":
                code = WT20SuperSixTeamEnum.PAK;
                break;
            case "England":
                code = WT20SuperSixTeamEnum.ENG;
                break;
            default:
                code = WT20SuperSixTeamEnum.OTHER;
                System.out.println("Not a matching country");
        }
        return code;
    }

    public List<Fixture> getFixtures() throws FileNotFoundException {
        BufferedReader fig = new BufferedReader(new FileReader("src/main/java/com/example/villvaycodingchallengecodingwk22/data/fixtures.csv"));
        int numberOfFixtures = 1;
        List<Fixture> fixtureList = new ArrayList<>();
        try {
            while ((line = fig.readLine()) != null)   //returns a Boolean value
            {
                String[] fixtures = line.split(splitBy);
                String firstCountry, secondCountry, tounermentDate;

                if (numberOfFixtures <= 3) {
                    firstCountry = fixtures[0];
                    secondCountry = fixtures[1];
                    tounermentDate = fixtures[2];
                } else {
                    throw new PredictionVagueException("Prediction Fixtures Too Vague");
                }

                Fixture fixture = new Fixture();
                fixture.setTeam1(getTeamDetails(firstCountry));
                fixture.setTeam2(getTeamDetails(secondCountry));

                Date fixtureDate = new SimpleDateFormat("dd/MM/yyyy").parse(tounermentDate);
                fixture.setFixtureDate(fixtureDate);

                fixtureList.add(fixture);
                numberOfFixtures++;
            }
            return fixtureList;

        } catch (IOException | PredictionVagueException | ParseException e) {
            e.printStackTrace();
        }
        return fixtureList;
    }
}
