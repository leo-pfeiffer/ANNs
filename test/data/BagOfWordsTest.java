package data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.junit.Test;
import src.data.BagOfWords;

public class BagOfWordsTest {
    @Test
    public void testBagSizeFromFile() {
        String vocabFile = "data/part1/vocab.txt";
        try {
            int result = BagOfWords.bagSizeFromFile(vocabFile);
            assertEquals(result, 3249);
        } catch (IOException e) {
            fail(e.getMessage());
        }

    }
}
