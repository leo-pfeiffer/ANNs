package src.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static void stringToFile(String path, String content) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(content);
        bw.close();
    }
}
