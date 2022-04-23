package src;

import src.hyperparam.HyperParams;
import src.hyperparam.HyperParamsConfig;
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

        int seed = Integer.parseInt(args[1]);
        String trainFile = args[2];
        String devFile = args[3];
        String testFile = args[4];
        String vocabFile = args[5];
        String classesFile = args[6];

        HyperParams params = HyperParamsConfig.PART1.getParams();

        try {
            switch (args[0]) {
                case "part1": {
                    TrecClassifier classifier = new TrecClassifier(seed, trainFile, devFile, testFile, params);
                    classifier.load(vocabFile, classesFile);
                    classifier.createNetwork(classifier.getNetworkP1());
                    classifier.train();
                    classifier.evaluate();
                    break;
                }
                case "part2": {
                    TrecClassifier classifier = new TrecClassifier(seed, trainFile, devFile, testFile, params);
                    classifier.load(vocabFile, classesFile);
                    classifier.createNetwork(classifier.getNetworkP2());
                    classifier.train();
                    classifier.evaluate();
                    break;
                }
                case "part3": {
                    TrecClassifier classifier = new TrecClassifier(seed, trainFile, devFile, testFile, params);
                    classifier.load(vocabFile, classesFile);
                    classifier.createNetwork(classifier.getNetworkP3(vocabFile));
                    classifier.train();
                    classifier.evaluate();
                    break;
                }
                case "part4": {
                    throw new RuntimeException("Not implemented!!");
                }
                default: {
                    throw new IllegalArgumentException("First argument must be one of <part1/part2/part3/part4>.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
