package com.example.villvaycodingchallengecodingwk22.utils;

import com.example.villvaycodingchallengecodingwk22.service.Fixture;
import com.example.villvaycodingchallengecodingwk22.service.impl.QualificationReport;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GenerateReport {
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static final Font greyFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.GRAY);

    private static final Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
            Font.BOLD);
    private static final Font details = new Font(Font.FontFamily.COURIER, 11,
            Font.ITALIC);
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    private static void createList(Document doc, QualificationReport data, boolean isBest) throws DocumentException {
        String winner = "";
        String qualify = "";
        if (isBest) {
            winner = data.getBestCase().getWhoWouldWin();
            qualify = data.getBestCase().getToQualify();
        }
        List list = new List(true, false, 10);
        list.add(new ListItem("Who Should win : " + winner));

        list.add(new ListItem("To Qualify : " + qualify));
        doc.add(list);
    }

    public void generate(QualificationReport data, java.util.List<Fixture> fixtureList) throws IOException, DocumentException, ParseException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Predicted_Report.pdf"));
        BaseFont bf = BaseFont.createFont("font/NotoEmoji-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);


        document.open();
        document.add(new Paragraph("T20 World Cup Qualification Simulator", catFont));
        document.add(new Paragraph("Qualification For Team : " + data.getQualificationTeam()));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Matches to be played", subFont));

        for (Fixture matchesDue : fixtureList) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            String strDate = dateFormat.format(matchesDue.getFixtureDate());

            document.add(new Paragraph(matchesDue.getTeam1().getTeam() + " vs "
                    + matchesDue.getTeam2().getTeam() + " on " + strDate, greyFont));
        }

        document.add(Chunk.NEWLINE);
        if (data.getGeneralComment() != null)
            document.add(new Paragraph("General Comment: " + data.getGeneralComment(), redFont));
        document.add(Chunk.NEWLINE);

        Paragraph bestCase = new Paragraph("✌✔", new Font(bf, 22));
        Paragraph worstCase = new Paragraph("✋❌ Worst Case", new Font(bf, 22));

        document.add(bestCase);
        document.add(new Paragraph("Best Case", smallBold));
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("1.  Who Should win : " + data.getBestCase().getWhoWouldWin(), subFont));

        document.add(new Paragraph(data.getBestCase().getAvgRunsScored(), details));
        document.add(new Paragraph(data.getBestCase().getPredictedRunsScored(), details));
        document.add(new Paragraph(data.getBestCase().getPredictedRunsScored(), details));
        document.add(new Paragraph(data.getBestCase().getAvgRunsScored(), details));
        document.add(new Paragraph(data.getBestCase().getAvgRunsConceded(), details));
        document.add(new Paragraph(data.getBestCase().getPredictedNRR(), details));
        document.add(new Paragraph("2.  To Qualify : " + data.getBestCase().getToQualify(), subFont));
        document.add(new Paragraph("When batting First : N/A", details));
        document.add(new Paragraph("When bowling First : N/A", details));


        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        document.add(worstCase);
        document.add(new Paragraph("Worst Case", smallBold));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("1.  Who Should win : " + data.getWorstCase().getWhoWouldWin(), subFont));

        document.add(new Paragraph(data.getWorstCase().getAvgRunsScored(), details));
        document.add(new Paragraph(data.getWorstCase().getPredictedRunsScored(), details));
        document.add(new Paragraph(data.getWorstCase().getPredictedRunsScored(), details));
        document.add(new Paragraph(data.getWorstCase().getAvgRunsScored(), details));
        document.add(new Paragraph(data.getWorstCase().getAvgRunsConceded(), details));
        document.add(new Paragraph(data.getWorstCase().getPredictedNRR(), details));
        document.add(new Paragraph("2.  To Qualify : " + data.getWorstCase().getToQualify(), subFont));
        document.add(new Paragraph("When batting First : N/A", details));
        document.add(new Paragraph("When bowling First : N/A", details));

        // add a couple of blank lines
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        document.close();
    }
}
