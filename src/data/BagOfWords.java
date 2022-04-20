package src.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Wrapper around int[] representing a bag of words.
 * All elements of the bag are either 0 or 1.
 * If bag[i] == 1 => i-th element of vocabulary is part of the sentence, else not.
 * */
public class BagOfWords {

    private final int[] bag;
    private final int[] tokens;
    private final int bagSize;

    public BagOfWords(int[] tokens, int bagSize) {
        this.tokens = tokens;
        this.bagSize = bagSize;
        this.bag = createBag();
    }

    /**
     * Create a bag of words from a list of tokens given the bag size.
     * @return bag of words
     */
    private int[] createBag() {
        int[] bagOfWords = new int[bagSize];
        for (int token : tokens) {
            bagOfWords[token] = 1;
        }
        return bagOfWords;
    }

    public int[] getBag() {
        return bag;
    }

    public static int bagSizeFromFile(String path) throws IOException {
        return countLinesInFile(path);
    }

    /**
     * Count number of lines in a file (if the line is not empty).
     * */
    private static int countLinesInFile(String path) throws IOException {
        int numLines = 0;
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) numLines++;
        }
        return numLines;
    }
}
