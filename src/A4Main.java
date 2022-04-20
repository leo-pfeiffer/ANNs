package src;

import src.classifier.HyperParams;
import src.classifier.HyperParamsConfig;
import src.classifier.TrecClassifier;

public class A4Main {

    /**
     * Example A4Main class. Feel free to edit this file 
     */
    public static void main(String[] args) {
        if (args.length < 6){
            System.out.println("Usage: java A4Main <part1/part2/part3/part4> <seed> <trainFile> <devFile> <testFile> <vocabFile> <classesFile>");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        String trainFile = args[1];
        String devFile = args[2];
        String testFile = args[3];
        String vocabFile = args[4];
        String classesFile = args[5];

        HyperParams params = HyperParamsConfig.PART1.getParams();

        try {
            TrecClassifier classifier = new TrecClassifier(seed, trainFile, devFile, testFile, params);
            classifier.load(vocabFile, classesFile);
            classifier.createNetwork();
            classifier.train();
            classifier.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}
