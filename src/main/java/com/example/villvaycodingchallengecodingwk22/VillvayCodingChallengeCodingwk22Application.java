package com.example.villvaycodingchallengecodingwk22;

import com.example.villvaycodingchallengecodingwk22.dto.ReadCSV;
import com.example.villvaycodingchallengecodingwk22.dto.Standing;
import com.example.villvaycodingchallengecodingwk22.dto.WT20SuperSixTeam;
import com.example.villvaycodingchallengecodingwk22.exceptions.NoPredictionsPossibleException;
import com.example.villvaycodingchallengecodingwk22.exceptions.PredictionVagueException;
import com.example.villvaycodingchallengecodingwk22.service.Fixture;
import com.example.villvaycodingchallengecodingwk22.service.impl.Predictor;
import com.example.villvaycodingchallengecodingwk22.service.impl.QualificationReport;
import com.itextpdf.text.DocumentException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class VillvayCodingChallengeCodingwk22Application {

    public static void main(String[] args) throws IOException, NoPredictionsPossibleException, PredictionVagueException, DocumentException {
        SpringApplication.run(VillvayCodingChallengeCodingwk22Application.class, args);
        List<String> valid_countries = Arrays.asList("Sri Lanka", "Afghanistan", "India", "Pakistan", "Australia", "England");
        String selected_country = "";

        Scanner t20 = new Scanner(System.in);
        System.out.println("Which country prediction would you wish to see?");

        while (true) {
            String qualifier = t20.nextLine();
            boolean isValid = valid_countries.stream().anyMatch(qualifier::equalsIgnoreCase);
            if (isValid) {
                selected_country = qualifier;
                break;
            } else
                System.out.println("Oops! That's an invalid Country. Try again");
        }

        ReadCSV reader = new ReadCSV();

        WT20SuperSixTeam predictingCountry = reader.getTeamDetails(selected_country);
        List<Fixture> fixtures = reader.getFixtures();

        List<Standing> pointsTable = reader.getPointsTable();

        // Qualification Report
        Predictor predict = new Predictor();
        QualificationReport statistics = predict.simulateQualificationScenarios(predictingCountry, pointsTable, fixtures);
    }
}
