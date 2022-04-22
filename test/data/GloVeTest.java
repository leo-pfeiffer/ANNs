package test.data;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.jblas.DoubleMatrix;
import org.junit.Test;
import src.data.GloVe;

public class GloVeTest {

    String file = "data/part3/vocab.txt";

    @Test
    public void testFromFile() {
        try {
            DoubleMatrix m = GloVe.fromFile(file);
            assertEquals(m.rows, 8594);
            assertEquals(m.columns, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
