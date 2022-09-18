package com.example.villvaycodingchallengecodingwk22.service.impl;

import com.example.villvaycodingchallengecodingwk22.dto.PointsTable;
import com.example.villvaycodingchallengecodingwk22.dto.Standing;
import com.example.villvaycodingchallengecodingwk22.dto.WT20SuperSixTeam;
import com.example.villvaycodingchallengecodingwk22.enums.WT20SuperSixTeamEnum;
import com.example.villvaycodingchallengecodingwk22.exceptions.NoPredictionsPossibleException;
import com.example.villvaycodingchallengecodingwk22.exceptions.PredictionVagueException;
import com.example.villvaycodingchallengecodingwk22.service.Fixture;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Predictor {

    protected Fixture match1 = null;
    protected Fixture match2 = null;
    protected Fixture match3 = null;
    protected Float qualifierNRR = 0f;
    protected WT20SuperSixTeamEnum code;
    protected String country;
    protected QualificationReport.Case bestCase;
    protected QualificationReport.Case worstCase;
    protected List<PointsTable> mapping = new ArrayList<>();


    public void setMatches(List<Fixture> fixtures, int numberOfRemainingMatches) {
        int counter = 0;
        Fixture[] features = new Fixture[3];

        for (Fixture match : fixtures) {
            features[counter] = match;
            counter++;
        }
        if (numberOfRemainingMatches == 1) {
            this.match1 = features[0];
        }
        if (numberOfRemainingMatches == 2) {
            this.match1 = features[0];
            this.match2 = features[1];
        }
        if (numberOfRemainingMatches == 3) {
            this.match1 = features[0];
            this.match2 = features[1];
            this.match3 = features[2];
        }
    }

    /**
     * @param toToSimulateQualification - Team for which the prediction is done
     * @param pointsTable               - Array of Standings not guranteed to be in any Order, the order is determined by the Standing#rank property
     * @param fixtures                  - Array of Fixture which shows the remaining mataches to be played.
     */
    public QualificationReport simulateQualificationScenarios(WT20SuperSixTeam toToSimulateQualification,
                                                              List<Standing> pointsTable,
                                                              List<Fixture> fixtures) throws NoPredictionsPossibleException, PredictionVagueException, FileNotFoundException {
        //sort out the fixtures based on Date
        //fixtures.stream().sorted(Comparator.comparing(Fixture::getFixtureDate)).collect(Collectors.toList());
        //Qualify check
        //WT20SuperSixTeam(team=Australia, code=AUS, matchesPlayed=4, matchesLost=3, matchesWon=1, points=2, runsScored=695, oversFaced=80, runsConceded=750, oversBowled=80, nRR=-0.688)

        //Fixture1
        //WT20SuperSixTeam(team=India, code=IND, matchesPlayed=4, matchesLost=3, matchesWon=1, points=2, runsScored=720, oversFaced=80, runsConceded=730, oversBowled=80, nRR=-0.125)
        //WT20SuperSixTeam(team=Australia, code=AUS, matchesPlayed=4, matchesLost=3, matchesWon=1, points=2, runsScored=695, oversFaced=80, runsConceded=750, oversBowled=80, nRR=-0.688)
        //Fri Oct 21 00:00:00 IST 2022

        //Fixture2 IND VS AUS


        QualificationReport report = new QualificationReport();
        report.setQualificationTeam(toToSimulateQualification.getTeam());


        int numberOfRemainingMatches = fixtures.size();
        DecimalFormat df = new DecimalFormat("#.###");

        //validation
        if (numberOfRemainingMatches == 0) {
            throw new NoPredictionsPossibleException("Error: No more remaining fixtures");
        } else if (numberOfRemainingMatches > 3) {
            throw new PredictionVagueException("Error: Prediction Too Vague");
        }

        // Initialize Mapping Table
        for (Standing map : pointsTable) {

            mapping.add(new PointsTable(map.getTeam(), map.getNetRunRate()));
        }

        setMatches(fixtures, numberOfRemainingMatches);

        //Since only 1 team is left to play, date would be irrelevant
        if (numberOfRemainingMatches == 1) {
            this.qualifierNRR = toToSimulateQualification.getNRR();
            this.code = toToSimulateQualification.getCode();
            this.country = toToSimulateQualification.getTeam();
            double team1NRR = calculateNRR(match1.getTeam1());
            double team2NRR = calculateNRR(match1.getTeam2());

            for (PointsTable points : mapping) {
                if (points.getCountry().equals(match1.getTeam1().getCode()))
                    points.setNRR(Double.parseDouble(df.format(team1NRR)));
                if (points.getCountry().equals(match1.getTeam2().getCode()))
                    points.setNRR(Double.parseDouble(df.format(team2NRR)));
            }

            List<PointsTable> qualifiers = getQualifiers(mapping);
            boolean predictorQualified = qualifiers.stream().anyMatch(n -> code.equals(n.getCountry()));

            if (!predictorQualified) {
                report.setGeneralComment(this.country + " Already Disqualified");
            }


        } else if (numberOfRemainingMatches == 2) {
            calculateNRRforAll(pointsTable, fixtures);
        }
        return null;
    }

    private List<PointsTable> getQualifiers(List<PointsTable> mapping) {
        List<PointsTable> first = mapping;

        PointsTable highest = Collections.max(first, Comparator.comparingDouble(PointsTable::getNRR));
        first.remove(highest);
        PointsTable secondHighest = Collections.max(first, Comparator.comparingDouble(PointsTable::getNRR));
        first.clear();
        first.add(highest);
        first.add(secondHighest);

        return first;
    }

    private void calculateNRRforAll(List<Standing> pointsTable, List<Fixture> fixtures) {
    }

    public double calculateNRR(WT20SuperSixTeam team) {
        float runsScored = team.getRunsScored();
        float runsConceded = team.getRunsConceded();
        float matchPlayed = team.getMatchesPlayed();
        float averagesRunsScored = Math.round(runsScored / matchPlayed);
        float runsScoredOversFaced = Math.round(runsScored + averagesRunsScored);
        float averageRunsConceded = Math.round(runsConceded / matchPlayed);
        float opponentScore = Math.round(averageRunsConceded - 15);
        float predictedRunsConcededOversBowled = Math.round(runsConceded + opponentScore);
        return (runsScoredOversFaced / 100) - (predictedRunsConcededOversBowled / 100);
    }
}


//    private String qualificationTeam;
//    private QualificationReport.Case bestCase;
//    private QualificationReport.Case worstCase;
//
//    @Data
//    public static class Case {
//        private String whoWouldWin;
//        private String avgRunsScored;
//        private String predictedRunsScored;
//        private String avgRunsConceded;
//        private String predictedRunsConceded;
//        private String predictedNRR;
//    }
//
//    const prediction=(runsScored=700,runsConceded=725,matchPlayed=4)=>{
//            const averagesRunsScored=Math.round(runsScored/matchPlayed);
//            const runsScoredOversFaced=Math.round(runsScored+averagesRunsScored);
//            const averageRunsConceded=Math.round(runsConceded/matchPlayed);
//            const opponentScore=Math.round(averageRunsConceded-15);
//            const predictedRunsConcededOversBowled=Math.round(
//            runsConceded+opponentScore
//            );
//
//            console.log("averagesRunsScored",averagesRunsScored);
//            console.log("runsScoredOversFaced",runsScoredOversFaced);
//            console.log("averageRunsConceded",averageRunsConceded);
//            console.log("opponentScore",opponentScore);
//
//            const nrr=
//            runsScoredOversFaced/100-predictedRunsConcededOversBowled/100;
//            console.log(nrr);
//            };
//            prediction();