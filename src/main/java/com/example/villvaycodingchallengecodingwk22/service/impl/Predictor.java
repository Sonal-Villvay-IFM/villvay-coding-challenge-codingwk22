package com.example.villvaycodingchallengecodingwk22.service.impl;

import com.example.villvaycodingchallengecodingwk22.dto.PointsTable;
import com.example.villvaycodingchallengecodingwk22.dto.Standing;
import com.example.villvaycodingchallengecodingwk22.dto.WT20SuperSixTeam;
import com.example.villvaycodingchallengecodingwk22.enums.WT20SuperSixTeamEnum;
import com.example.villvaycodingchallengecodingwk22.exceptions.NoPredictionsPossibleException;
import com.example.villvaycodingchallengecodingwk22.exceptions.PredictionVagueException;
import com.example.villvaycodingchallengecodingwk22.service.Fixture;
import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Predictor {

    protected Fixture match1 = null;
    protected Fixture match2 = null;
    protected Fixture match3 = null;
    protected Float qualifierNRR = 0f;
    protected WT20SuperSixTeamEnum code;
    protected String country;
    protected QualificationReport.Case bestCase = new QualificationReport.Case();
    protected QualificationReport.Case worstCase = new QualificationReport.Case();
    protected List<PointsTable> mapping = new ArrayList<>();
    protected DecimalFormat df = new DecimalFormat("#.###");
    protected double team1NRR;
    protected double team2NRR;

    /**
     * @param toToSimulateQualification - Team for which the prediction is done
     * @param pointsTable               - Array of Standings not guranteed to be in any Order, the order is determined by the Standing#rank property
     * @param fixtures                  - Array of Fixture which shows the remaining mataches to be played.
     */
    public QualificationReport simulateQualificationScenarios(WT20SuperSixTeam toToSimulateQualification,
                                                              List<Standing> pointsTable,
                                                              List<Fixture> fixtures) throws NoPredictionsPossibleException, PredictionVagueException, IOException, DocumentException {
        QualificationReport report = new QualificationReport();
        report.setQualificationTeam(toToSimulateQualification.getTeam());

        int numberOfRemainingMatches = fixtures.size();

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

        //sort out the fixtures based on Date and set Matches
        fixtures.stream().sorted(Comparator.comparing(Fixture::getFixtureDate)).collect(Collectors.toList());
        setMatches(fixtures, numberOfRemainingMatches);

        this.qualifierNRR = toToSimulateQualification.getNRR();
        this.code = toToSimulateQualification.getCode();
        this.country = toToSimulateQualification.getTeam();

        //Since only 1 team is left to play, date would be irrelevant
        if (numberOfRemainingMatches == 1) {
            playFirstGame();
            List<PointsTable> qualifiers = getQualifiers(mapping);
            boolean predictorQualified = qualifiers.stream().anyMatch(n -> code.equals(n.getCountry()));
            setDetails(report, qualifiers, predictorQualified);
        }
        // 2 fixture matches
        else if (numberOfRemainingMatches == 2) {
            playFirstGame();
            playSecondGame();

            List<PointsTable> finalists = getQualifiers(mapping);
            boolean predictorQualified = finalists.stream().anyMatch(n -> code.equals(n.getCountry()));
            setDetails(report, finalists, predictorQualified);
        } else {
            // 3 fixture matches
            playFirstGame();
            playSecondGame();
            playThirdGame();

            List<PointsTable> finalists = getQualifiers(mapping);
            boolean predictorQualified = finalists.stream().anyMatch(n -> code.equals(n.getCountry()));
            setDetails(report, finalists, predictorQualified);
        }
        report.setBestCase(bestCase);
        report.setWorstCase(worstCase);

        return report;
    }

    private void setDetails(QualificationReport report, List<PointsTable> qualifiers, boolean predictorQualified) {
        if (!predictorQualified) {
            report.setGeneralComment(this.country + " already Disqualified");
            bestCase.setToQualify(this.country + " already Disqualified");
            worstCase.setToQualify(this.country + " already Disqualified");

            bestCase.setWhoWouldWin(String.valueOf(qualifiers));
            if (team1NRR < team2NRR) {
                setBestDetails(match1.getTeam1(), true);
                setBestDetails(match1.getTeam2(), false);
            } else {
                setBestDetails(match1.getTeam2(), true);
                setBestDetails(match1.getTeam1(), false);
            }
        } else {
            report.setGeneralComment(this.country + " already Qualified");
            bestCase.setToQualify(this.country + " already Qualified");
            worstCase.setToQualify(this.country + " already Qualified");

            bestCase.setWhoWouldWin(String.valueOf(qualifiers));
            if (team1NRR > team2NRR) {
                setBestDetails(match1.getTeam1(), true);
                setBestDetails(match1.getTeam2(), false);
            } else {
                setBestDetails(match1.getTeam2(), true);
                setBestDetails(match1.getTeam1(), false);
            }
        }
    }

    private void playFirstGame() {
        this.team1NRR = calculateNRR(match1.getTeam1());
        this.team2NRR = calculateNRR(match1.getTeam2());

        for (PointsTable points : mapping) {
            if (points.getCountry().equals(match1.getTeam1().getCode()))
                points.setNRR(Double.parseDouble(df.format(team1NRR)));
            if (points.getCountry().equals(match1.getTeam2().getCode()))
                points.setNRR(Double.parseDouble(df.format(team2NRR)));
        }
    }

    private void playSecondGame() {
        double match2_team1NRR = calculateNRR(match2.getTeam1());
        double match2_team2NRR = calculateNRR(match2.getTeam2());

        for (PointsTable points : mapping) {
            if (points.getCountry().equals(match2.getTeam1().getCode()))
                points.setNRR(Double.parseDouble(df.format(match2_team1NRR)));
            if (points.getCountry().equals(match2.getTeam2().getCode()))
                points.setNRR(Double.parseDouble(df.format(match2_team2NRR)));
        }
    }

    private void playThirdGame() {
        double match3_team1NRR = calculateNRR(match3.getTeam1());
        double match3_team2NRR = calculateNRR(match3.getTeam2());

        for (PointsTable points : mapping) {
            if (points.getCountry().equals(match3.getTeam1().getCode()))
                points.setNRR(Double.parseDouble(df.format(match3_team1NRR)));
            if (points.getCountry().equals(match3.getTeam2().getCode()))
                points.setNRR(Double.parseDouble(df.format(match3_team2NRR)));
        }
    }

    private void setBestDetails(WT20SuperSixTeam team, boolean isBestCase) {
        float runsScored = team.getRunsScored();
        float runsConceded = team.getRunsConceded();
        float matchPlayed = team.getMatchesPlayed();
        float averagesRunsScored = Math.round(runsScored / matchPlayed);
        float averageRunsConceded = Math.round(runsConceded / matchPlayed);
        float opponentScore = Math.round(averageRunsConceded - 15);
        float runsScoredOversFaced = Math.round(runsScored + averagesRunsScored);
        float predictedRunsConcededOversBowled = Math.round(runsConceded + opponentScore);

        if (isBestCase) {
            bestCase.setWhoWouldWin(team.getTeam());
            bestCase.setAvgRunsScored("Averages RunsScored when Batting : " + Math.round(runsScored / matchPlayed));
            bestCase.setPredictedRunsScored("Predicted RunsScored/OversFaced would be : " + Math.round(runsScored + averagesRunsScored));
            bestCase.setAvgRunsConceded("Average RunsConceded when bowling : " + averageRunsConceded
                    + ". Due to the assumption, their opponent would score would be " + opponentScore);
            bestCase.setPredictedRunsConceded("Predicted RunsConceded/OversBowled would be" + Math.round(runsConceded + opponentScore));
            bestCase.setPredictedNRR("Predicted NRR Would be : " + ((runsScoredOversFaced / 100) - (predictedRunsConcededOversBowled / 100)));
        } else {
            worstCase.setWhoWouldWin(team.getTeam());
            worstCase.setAvgRunsScored("Averages RunsScored when Batting : " + Math.round(runsScored / matchPlayed));
            worstCase.setPredictedRunsScored("Predicted RunsScored/OversFaced would be : " + Math.round(runsScored + averagesRunsScored));
            worstCase.setAvgRunsConceded("Average RunsConceded when bowling : " + averageRunsConceded
                    + ". Due to the assumption, their opponent would score would be " + opponentScore);
            worstCase.setPredictedRunsConceded("Predicted RunsConceded/OversBowled would be" + Math.round(runsConceded + opponentScore));
            worstCase.setPredictedNRR("Predicted NRR Would be : " + ((runsScoredOversFaced / 100) - (predictedRunsConcededOversBowled / 100)));
        }
    }

    // Selects the final qualifiers
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
}