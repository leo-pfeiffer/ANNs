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
            System.out.println("Usage: java A4Main <part1/part2/part3/part4> <seed> <trainFile> " +
                    "<devFile> <testFile> <vocabFile> <classesFile> [<tracker-output-file>]");
            return;
        }

        int seed = Integer.parseInt(args[1]);
        String trainFile = args[2];
        String devFile = args[3];
        String testFile = args[4];
        String vocabFile = args[5];
        String classesFile = args[6];

        String trackerOutputFile = (args.length > 7) ? args[7] : null;

        HyperParams params = HyperParamsConfig.PART1.getParams();

        TrecClassifier classifier;

        try {
            switch (args[0]) {
                case "part1": {
                    classifier = new TrecClassifier(seed, trainFile, devFile, testFile, params);
                    classifier.load(vocabFile, classesFile);
                    classifier.createNetwork(classifier.getNetworkP1());
                    classifier.train();
                    classifier.evaluate();
                    break;
                }
                case "part2": {
                    classifier = new TrecClassifier(seed, trainFile, devFile, testFile, params);
                    classifier.load(vocabFile, classesFile);
                    classifier.createNetwork(classifier.getNetworkP2());
                    classifier.train();
                    classifier.evaluate();
                    break;
                }
                case "part3": {
                    classifier = new TrecClassifier(seed, trainFile, devFile, testFile, params);
                    classifier.load(vocabFile, classesFile);
                    classifier.createNetwork(classifier.getNetworkP3(vocabFile));
                    classifier.train();
                    classifier.evaluate();
                    break;
                }
                case "part4": {
                    classifier = new TrecClassifier(seed, trainFile, devFile, testFile, params);
                    classifier.load(vocabFile, classesFile);
                    classifier.createNetwork(classifier.getNetworkP4(vocabFile));
                    classifier.train();
                    classifier.evaluate();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("First argument must be one of <part1/part2/part3/part4>.");
                }
            }

            if (trackerOutputFile != null) classifier.getTracker().toCsv(trackerOutputFile);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
