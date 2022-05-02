package test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.junit.Test;
import src.util.FileUtil;

public class FileUtilTest {
    @Test
    public void testCountLinesInFile() {
        String vocabFile = "data/part1/vocab.txt";
        try {
            int result = FileUtil.countLinesInFile(vocabFile);
            assertEquals(result, 3249);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
