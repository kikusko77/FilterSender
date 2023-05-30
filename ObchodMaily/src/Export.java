import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Export {
    private String csvFilePath;
    private Map<String, List<CSVRecord>> csvRecordMap;

    public Export(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        this.csvRecordMap = new HashMap<>();
    }

    public Map<String, List<CSVRecord>> getCsvRecordMap() throws Exception {
        try (Reader in = new FileReader(csvFilePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);

            for (CSVRecord record : records) {
                String key = record.get(3).toUpperCase();
                if (!csvRecordMap.containsKey(key)) {
                    csvRecordMap.put(key, new ArrayList<>());
                }
                csvRecordMap.get(key).add(record);
            }
        }

        return csvRecordMap;
    }

    public void printRecordsByKod(String kod) {
        List<CSVRecord> records = csvRecordMap.get(kod);
        if (records != null) {
            for (CSVRecord record : records) {
                System.out.println(record);
            }
        }
    }

    public List<CSVRecord> getRecordsByCode(String code) {
        return this.csvRecordMap.getOrDefault(code.toUpperCase(), new ArrayList<>());
    }
}



