package evaluation.out;

import src.classifier.TrecClassifier;
import src.hyperparam.HyperParams;
import src.hyperparam.Tuning;

public class HyperParamTuning {

    static String trainFile = "data/part1/train.txt";
    static String devFile = "data/part1/dev.txt";
    static String testFile = "data/part1/test.txt";
    static String vocabFile = "data/part1/vocab.txt";
    static String classesFile = "data/part1/classes.txt";

    public static Tuning getTuning() {
        Tuning tuning = new Tuning();

//        double[] learningRates = new double[]{0.05, 0.1, 0.15};
//        int[] patiences = new int[]{5, 10, 15, 50};
//        int[] sizeFirst = new int[]{50, 100, 200};
//        int[] sizeOthers = new int[]{50, 200, 400};
        double[] learningRates = new double[]{0.1, 0.15};
        int[] patiences = new int[]{3};
        int[] sizeFirst = new int[]{50};
        int[] sizeOthers = new int[]{50};

        int maxNumEpoch = 5;
        int batchSize = 50;
        int numHidden = 3;

        for (double lr : learningRates) {
            for (int p : patiences) {
                for (int sf : sizeFirst) {
                    for (int so : sizeOthers) {
                        tuning.addSetting(new HyperParams(lr, maxNumEpoch, p, batchSize, numHidden, sf, so));
                    }
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
                classifier.createNetwork();
                classifier.train();
                double acc = classifier.evaluate();
                tuning.setAccuracy(acc);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        } while ((param = tuning.getNextSetting()) != null);

        HyperParams optim = tuning.getOptimalParams();
    }

    // should give same result as part 1
    public static void tunePart2() {
        Tuning tuning = getTuning();
        HyperParams param = tuning.getNextSetting();
        do {
            try {
                TrecClassifier classifier = new TrecClassifier(42, trainFile, devFile, testFile, param);
                classifier.load(vocabFile, classesFile);
                classifier.createNetworkWithEmbedding();
                classifier.train();
                double acc = classifier.evaluate();
                tuning.setAccuracy(acc);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        } while ((param = tuning.getNextSetting()) != null);

        HyperParams optim = tuning.getOptimalParams();
    }

    public static void main(String[] args) {
        tunePart1();
        // tunePart2
    }

}
