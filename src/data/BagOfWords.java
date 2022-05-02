package src.data;

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
    public int[] createBag() {
        int[] bagOfWords = new int[bagSize];
        for (int token : tokens) {
            bagOfWords[token] = 1;
        }
        return bagOfWords;
    }

    public int[] getBag() {
        return bag;
    }

    public double[] toDouble() {
        double[] doubles = new double[bagSize];
        for (int i = 0; i < bagSize; i++) {
            doubles[i] = bag[i];
        }
        return doubles;
    }
}
