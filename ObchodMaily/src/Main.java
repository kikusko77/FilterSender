public class Main {
    public static void main(String[] args) {
        try {
            // CSV file path
            String csvFilePath = "C:\\Users\\ChristianKu\\GOPAS, a.s\\OO working place 2023 - Leady\\Export.csv";
            Export export = new Export(csvFilePath);
            export.getCsvRecordMap();

            // Create a new instance of Data and pass the Export instance to it
            Data data = new Data(export);
            data.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
