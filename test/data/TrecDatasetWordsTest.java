package data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Random;
import org.junit.Test;
import src.data.BagOfWords;
import src.data.TrecDataset;
import src.util.FileUtil;

public class TrecDatasetWordsTest {

    String trainData = "data/part1/train.txt";
    String vocabFile = "data/part1/vocab.txt";
    static {
        org.jblas.util.Random.seed(42);
    }
    Random rnd = new Random(42);

    @Test
    public void testFromFile() {
        try {
            int bagSize = FileUtil.countLinesInFile(vocabFile);
            TrecDataset ds = new TrecDataset(50, false, rnd, bagSize);
            ds.fromFile(trainData);
            assertEquals(ds.getSize(), 5000);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
