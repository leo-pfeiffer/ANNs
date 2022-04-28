package evaluation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import src.classifier.TrecClassifier;
import src.hyperparam.HyperParams;
import src.hyperparam.HyperParamsConfig;

public class RunTuned {

    public static void runPart1() {
        String trainFile = "data/part1/train.txt";
        String devFile = "data/part1/dev.txt";
        String testFile = "data/part1/test.txt";
        String vocabFile = "data/part1/vocab.txt";
        String classesFile = "data/part1/classes.txt";

        HyperParams param = HyperParamsConfig.TUNED1.getParams();

        try {
            TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
            classifier.load(vocabFile, classesFile);
            classifier.createNetwork(classifier.getNetworkP1());
            classifier.train();
            classifier.getTracker().toCsv("evaluation/out/part1.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void runPart2() {
        String trainFile = "data/part1/train.txt";
        String devFile = "data/part1/dev.txt";
        String testFile = "data/part1/test.txt";
        String vocabFile = "data/part1/vocab.txt";
        String classesFile = "data/part1/classes.txt";

        HyperParams param = HyperParamsConfig.TUNED2.getParams();

        try {
            TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
            classifier.load(vocabFile, classesFile);
            classifier.createNetwork(classifier.getNetworkP2());
            classifier.train();
            classifier.getTracker().toCsv("evaluation/out/part2.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static void runPart3() {
        String trainFile = "data/part3/train.txt";
        String devFile = "data/part3/dev.txt";
        String testFile = "data/part3/test.txt";
        String vocabFile = "data/part3/vocab.txt";
        String classesFile = "data/part3/classes.txt";

        HyperParams param = HyperParamsConfig.TUNED3.getParams();

        try {
            TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
            classifier.load(vocabFile, classesFile);
            classifier.createNetwork(classifier.getNetworkP3(vocabFile));
            classifier.train();
            classifier.getTracker().toCsv("evaluation/out/part3.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static void runPart4() {
        String trainFile = "data/part4/output/train.txt";
        String devFile = "data/part4/output/dev.txt";
        String testFile = "data/part4/output/test.txt";
        String vocabFile = "data/part4/output/subset_model.txt";
        String classesFile = "data/part4/input/classes.txt";

        HyperParams param = HyperParamsConfig.TUNED4.getParams();

        try {
            TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
            classifier.load(vocabFile, classesFile);
            classifier.createNetwork(classifier.getNetworkP4(vocabFile));
            classifier.train();
            classifier.getTracker().toCsv("evaluation/out/part4.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        // execute in parallel
        executor.execute(RunTuned::runPart1);
        executor.execute(RunTuned::runPart2);
        executor.execute(RunTuned::runPart3);
        executor.execute(RunTuned::runPart4);
    }
}
