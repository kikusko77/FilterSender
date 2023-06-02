import org.apache.commons.csv.CSVRecord;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // CSV file path
            String csvFilePath = "C:\\Users\\ChristianKu\\GOPAS, a.s\\OO working place 2023 - Leady\\Export.csv";

            // Create an instance of Export and populate the CSV record map
            Export export = new Export(csvFilePath);
            export.getCsvRecordMap();

            // Create a new instance of Data and pass the Export instance to it
            Data data = new Data(export);
            List<Data.MatchingRecord> matchingRecords = data.process();

            // Create an instance of Mails and send the email with the matching records
            Mails mails = new Mails();
            mails.sendEmail(matchingRecords);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
