package src;

import org.jblas.util.Logger;

import java.util.Random;
import src.EmbeddingBag;

public class A4Main {

    /**
     * Example A4Main class. Feel free to edit this file 
     */
    public static void main(String[] args) {
        if (args.length < 6){
            System.out.println("Usage: java A4Main <part1/part2/part3/part4> <seed> <trainFile> <devFile> <testFile> <vocabFile> <classesFile>");
            return;
        }        

        // set jblas random seed (for reproducibility)
        int seed = Integer.parseInt(args[1]);
		org.jblas.util.Random.seed(seed);
		Random rnd = new Random(seed);
		
        // turn off jblas info messages
        Logger.getLogger().setLevel(Logger.WARNING);

    }
}
