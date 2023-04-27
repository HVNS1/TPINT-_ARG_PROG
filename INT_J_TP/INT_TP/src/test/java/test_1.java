import com.opencsv.exceptions.CsvValidationException;
import org.example.utils.LectorCSV;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class test_1 {
    private LectorCSV lectorCSV;
    @Test
    public void testArrayStringComparison() throws CsvValidationException, IOException {
        String ruta = "C:\\Users\\godoy\\Desktop\\INT_J_TP\\INT_TP\\src\\main\\resources\\results.csv";
        lectorCSV = new LectorCSV();
        String[] algo = lectorCSV.leerFila(ruta);
        String string = "Argentina";

        boolean result = compareStringWithArrayItem(algo[0], string);
        assertTrue(result);
    }

    private boolean compareStringWithArrayItem(String arrayItem, String string) {
        return arrayItem.equals(string);
    }
}
