import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.apache.commons.csv.CSVRecord;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.Map;
import java.util.List;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Data {
    private Export export;

    public Data(Export export) {
        this.export = export;
    }

    public void process() {
        try {
            File inputFile = new File("C:\\Users\\ChristianKu\\Desktop\\BratislavaCourses.my");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Course");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String status = eElement.getElementsByTagName("STATUS").item(0).getTextContent();
                    String pobocka = eElement.getElementsByTagName("POBOCKA").item(0).getTextContent();
                    String kod = eElement.getElementsByTagName("KOD").item(0).getTextContent().toUpperCase();
                    String datum = eElement.getElementsByTagName("DATUM").item(0).getTextContent();

                    if ("Bratislava".equals(pobocka) && "OK".equals(status)) {
                        // Parse the date and check if it is more than 14 days in the future
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                        LocalDate date = LocalDate.parse(datum.split("-")[1].trim(), formatter);

                        if (LocalDate.now().until(date, ChronoUnit.DAYS) > 10) {
                            // Check if the date falls within a 6-day range
                            if (LocalDate.now().until(date, ChronoUnit.DAYS) <= 15) {
                                System.out.println("KOD: " + kod +" "+ "Datum: "+datum);
                                List<CSVRecord> records = export.getRecordsByCode(kod);
                                if (records != null) {
                                    for (CSVRecord record : records) {
                                        System.out.println(record);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
