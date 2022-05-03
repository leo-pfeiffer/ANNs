package src.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtil {
    /**
     * Count number of lines in a file (if the line is not empty).
     */
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

    @SafeVarargs
    public static <T> void listsToCsv(String path, String[] headers, List<T>... lists) throws IOException {
        assert lists.length == headers.length;
        assert lists.length > 0;
        int rows = lists[0].size();
        for (List<T> list : lists) {
            assert list.size() == rows;
        }

        StringBuilder sb = new StringBuilder();

        // Write headers
        for (int i = 0; i < headers.length; i++) {
            sb.append(headers[i]);
            if (i < headers.length - 1) sb.append(",");
        }
        sb.append("\n");

        // Write data
        for (int i = 0; i < rows; i++) {
            for (List<T> list : lists) {
                String str = list.get(i).toString();
                sb.append(escapeSpecialCharacters(str));
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }

        // Write to file
        stringToFile(path, sb.toString());
    }

    /**
     * Escape special characters in a string.
     * Source: https://www.baeldung.com/java-csv
     */
    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
