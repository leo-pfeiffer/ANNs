package evaluation;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import src.classifier.TrecClassifier;
import src.hyperparam.HyperParams;
import src.hyperparam.Tuning;
import src.util.FileUtil;

public class HyperParamTuning {

    static String trainFile = "data/part1/train.txt";
    static String devFile = "data/part1/dev.txt";
    static String testFile = "data/part1/test.txt";
    static String vocabFile = "data/part1/vocab.txt";
    static String classesFile = "data/part1/classes.txt";

    public static Tuning getTuning() {
        Tuning tuning = new Tuning();

        double[] learningRates = new double[]{0.05, 0.1, 0.15, 0.2};
        int[] sizeFirst = new int[]{25, 50, 100};
        int[] sizeOthers = new int[]{50, 100, 200, 400};

        int patience = 10;
        int maxNumEpoch = 500;
        int batchSize = 50;
        int numHidden = 3;

        for (double lr : learningRates) {
            for (int sf : sizeFirst) {
                for (int so : sizeOthers) {
                    System.out.print("Learning rate: " + lr + "\nSize First: " + sf + "\nSize Others: " + so + "\n");
                    tuning.addSetting(new HyperParams(lr, maxNumEpoch, patience, batchSize, numHidden, sf, so));
                }
            }
        }

        return tuning;
    }

    public static void tunePart1() {
        Tuning tuning = getTuning();
        HyperParams param = tuning.getNextSetting();
        do {
            try {
                TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
                classifier.load(vocabFile, classesFile);
                classifier.createNetwork(classifier.getNetworkP1());
                classifier.train();
                double acc = classifier.evaluate();
                tuning.setAccuracy(acc);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        } while ((param = tuning.getNextSetting()) != null);

        toFile("tuning1.txt", tuning);
    }

    // should give same result as part 1
    public static void tunePart2() {
        Tuning tuning = getTuning();
        HyperParams param = tuning.getNextSetting();
        do {
            try {
                TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
                classifier.load(vocabFile, classesFile);
                classifier.createNetwork(classifier.getNetworkP2());
                classifier.train();
                double acc = classifier.evaluate();
                tuning.setAccuracy(acc);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        } while ((param = tuning.getNextSetting()) != null);

        toFile("tuning2.txt", tuning);
    }

    public static void tunePart3() {

        String trainFile = "data/part3/train.txt";
        String devFile = "data/part3/dev.txt";
        String testFile = "data/part3/test.txt";
        String vocabFile = "data/part3/vocab.txt";
        String classesFile = "data/part3/classes.txt";

        Tuning tuning = getTuning();
        HyperParams param = tuning.getNextSetting();
        do {
            try {
                TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
                classifier.load(vocabFile, classesFile);
                classifier.createNetwork(classifier.getNetworkP3(vocabFile));
                classifier.train();
                double acc = classifier.evaluate();
                tuning.setAccuracy(acc);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        } while ((param = tuning.getNextSetting()) != null);

        toFile("tuning3.txt", tuning);
    }

    public static void tunePart4() {

        String trainFile = "data/part4/output/train.txt";
        String devFile = "data/part4/output/dev.txt";
        String testFile = "data/part4/output/test.txt";
        String vocabFile = "data/part4/output/subset_model.txt";
        String classesFile = "data/part4/input/classes.txt";

        Tuning tuning = getTuning();
        HyperParams param = tuning.getNextSetting();
        do {
            try {
                TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
                classifier.load(vocabFile, classesFile);
                classifier.createNetwork(classifier.getNetworkP4(vocabFile));
                classifier.train();
                double acc = classifier.evaluate();
                tuning.setAccuracy(acc);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        } while ((param = tuning.getNextSetting()) != null);

        toFile("tuning4.txt", tuning);
    }

    public static void toFile(String name, Tuning tuning) {
        try {
            FileUtil.stringToFile("evaluation/out/" + name, tuning.toJson());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Executes parameter tuning for all four parts in parallel
     * */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(HyperParamTuning::tunePart1);
        executor.execute(HyperParamTuning::tunePart2);
        executor.execute(HyperParamTuning::tunePart3);
        executor.execute(HyperParamTuning::tunePart4);
    }
}
