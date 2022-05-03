package src.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import minet.data.Dataset;
import minet.util.Pair;

public class TrecDataset extends Dataset<BagOfWords, Integer> {

    private final int inputDims;

    /**
     * Constructor for Dataset
     * @param batchSize (int) size of each mini-batch
     * @param shuffle   (boolean) if true, shuffle the dataset at the beginning of each epoch
     * @param rnd       (java.util.Random) random generator for the shuffling
     */
    public TrecDataset(int batchSize, boolean shuffle, Random rnd, int bagSize) {
        super(batchSize, shuffle, rnd);
        this.inputDims = bagSize;
    }

    /**
     * Input dimensions (= number of words in the vocabulary)
     */
    public int getInputDims() {
        return inputDims;
    }

    @Override
    public void fromFile(String path) throws IOException {
        // Input data file:
        //      word1 word2 word3 ... : label
        //      where word* is an int corresponding to the lines in the vocab file
        //      and label is the line in the classes file
        BufferedReader br = new BufferedReader(new FileReader(path));

        items = new ArrayList<>();
        String[] splitted;
        String line;

        while ((line = br.readLine()) != null) {
            splitted = line.split(" ; ");
            String[] words = splitted[0].split(" ");
            int[] tokens = new int[words.length];

            Integer label = Integer.valueOf(splitted[1]);

            for (int j = 0; j < words.length; j++) {
                tokens[j] = Integer.parseInt(words[j]);
            }

            BagOfWords bag = new BagOfWords(tokens, inputDims);

            items.add(new Pair<>(bag, label));
        }
        br.close();
    }
}
