package com.example.villvaycodingchallengecodingwk22;

import com.example.villvaycodingchallengecodingwk22.dto.ReadCSV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.Scanner;

@SpringBootApplication
public class VillvayCodingChallengeCodingwk22Application {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(VillvayCodingChallengeCodingwk22Application.class, args);

        Scanner t20 = new Scanner(System.in);
        System.out.println("Which qualification scenario do you wish to see?");
        ReadCSV r1 = new ReadCSV();
        String qualifier = t20.nextLine();
        System.out.println(r1.getCountry(qualifier));
    }

}
