package src.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    /**
     * Count number of lines in a file (if the line is not empty).
     * */
    public static int countLinesInFile(String path) throws IOException {
        int numLines = 0;
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) numLines++;
        }
        return numLines;
    }
}
