package com.example.villvaycodingchallengecodingwk22.service.impl;

import lombok.Data;

@Data
public class QualificationReport {
    private String generalComment;
    private String qualificationTeam;
    private Case bestCase;
    private Case worstCase;

    @Data
    public static class Case {
        private String whoWouldWin;
        private String avgRunsScored;
        private String predictedRunsScored;
        private String avgRunsConceded;
        private String predictedRunsConceded;
        private String predictedNRR;
    }
}


