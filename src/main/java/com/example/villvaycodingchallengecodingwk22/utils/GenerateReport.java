package com.example.villvaycodingchallengecodingwk22.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateReport {
    public void generate() throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("test.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

        BaseFont bf = BaseFont.createFont("font/NotoEmoji-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Paragraph p = new Paragraph("✌✔☑   -   ❎❌✊✋", new Font(bf, 22));

        document.add(p);
        String content = "<b>Qualification For Team</b> U+1F44D : <html>\n" +
                "<body>\n" +
                "<span style='font-size:100px;'>&#128512;</span>\n" +
                "<p>I will display &#x1F602;</p>\n" +
                "<p>I will display &#x1F605;</p>\n" +
                "</body>\n" +
                "</html>";
        document.add(new Paragraph("Hello, World!"));

        // add a couple of blank lines
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        // add one more line with text
        document.add(new Paragraph(content));
        document.close();
    }
}
