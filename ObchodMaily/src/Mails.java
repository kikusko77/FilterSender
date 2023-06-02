import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;
import org.apache.commons.csv.CSVRecord;
import java.util.List;
import java.util.Properties;
import java.util.Arrays;

public class Mails {
    // email ID of Recipient.
    String recipient = "alecborovy@icloud.com";
    // email ID of Sender.
    String sender = "prahamail@gopas.cz";
    // SMTP Server
    String host = "prahamail.gopas.cz";
    // SMTP Server Port
    int port = 25;

    // Prepare Properties
    Properties properties = new Properties();

    public void sendEmail(List<Data.MatchingRecord> matchingRecords) {
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding sender's email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("skuska");

            // Prepare HTML table for email content
            List<String> myHeaders = Arrays.asList("Datum kurzu","Nadpis", "zdroj", "Meno", "Firma", "Garant", "Pozicia", "email", "Telefon", "Datum akcie" );

            StringBuilder sb = new StringBuilder("<table style='border:1px solid black; border-collapse: collapse;'>");
            sb.append("<tr>");
            for (String header : myHeaders) {
                sb.append("<th style='border:1px solid black; padding: 10px;'>" + header + "</th>");
            }
            sb.append("</tr>");

            for (Data.MatchingRecord matchingRecord : matchingRecords) {
                String datum = matchingRecord.getDatum();
                CSVRecord record = matchingRecord.getRecord();


                sb.append("<tr>");
                sb.append("<td style='border:1px solid black; padding: 10px;'>" + datum + "</td>");
                for (int i = 3; i <= 11; i++) {
                    sb.append("<td style='border:1px solid black; padding: 10px;'>" + record.get(i) + "</td>");
                }

                sb.append("</tr>");
            }
            sb.append("</table>");

            // set body of the email.
            message.setContent(sb.toString(), "text/html; charset=UTF-8");


            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

