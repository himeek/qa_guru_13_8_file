package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFileTest {

    ClassLoader classLoader = ZipFileTest.class.getClassLoader();

    @Test
    void zipTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("zipfile.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().contains("pdf")) {
                PDF pdf = new PDF(zis);
                assertThat(pdf.text).contains("test");
                System.out.println("PDF Test Success!");
            } else if (entry.getName().contains("xls")) {
                XLS xls = new XLS(zis);
                assertThat(
                        xls.excel.getSheetAt(0)
                                .getRow(7)
                                .getCell(4)
                                .getStringCellValue()
                ).contains("Name");
                System.out.println("XLS Test Success");
            } else if (entry.getName().contains("csv")) {
                CSVReader csvReader = new CSVReader(new InputStreamReader(zis, UTF_8));
                List<String[]> csvList = csvReader.readAll();
                assertThat(csvList).contains(
                        new String[]{"name", "date", "rating"},
                        new String[]{"sense8", "2015", "5"},
                        new String[]{"hannibal", "2013", "4"});
                System.out.println("CSV file is checked");
            }
        }
    }
}
