package com.example.villvaycodingchallengecodingwk22;

import com.example.villvaycodingchallengecodingwk22.dto.ReadCSV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class VillvayCodingChallengeCodingwk22Application {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(VillvayCodingChallengeCodingwk22Application.class, args);
        List<String> valid_countries = Arrays.asList("Sri Lanka", "Afghanistan", "India", "Pakistan", "Australia", "England");
        String selected_country;

        Scanner t20 = new Scanner(System.in);
        System.out.println("Which qualification scenario do you wish to see?");

        while (true) {
            String qualifier = t20.nextLine();
            boolean isValid = valid_countries.stream().anyMatch(qualifier::equalsIgnoreCase);
            if (isValid) {
                selected_country = qualifier;
                break;
            } else
                System.out.println("Oops! That's an invalid Country. Try again");
        }

        System.out.println("Which Fixture are you interested in?");
        System.out.println("[Option 1] - India Vs Australia on the 21st Of October 2022");
        System.out.println("[Option 2] - Sri Lanka Vs Pakistan on the 22nd of October 2022");

        while (true) {
            String option = t20.nextLine();
            if (option.equals("1") || option.equals("2"))
                break;
            else
                System.out.println("Oops! That's an invalid input. Try entering numeric 1 or 2");
        }

        ReadCSV reader = new ReadCSV();
        //reader.read();
        System.out.println(reader.getCountry(selected_country));
    }
}
