package src.classifier;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import minet.layer.Layer;
import minet.layer.Linear;
import minet.layer.ReLU;
import minet.layer.Sequential;
import minet.layer.Softmax;
import minet.layer.init.WeightInitXavier;
import minet.loss.CrossEntropy;
import minet.loss.Loss;
import minet.optim.Optimizer;
import minet.optim.SGD;
import minet.util.Pair;
import org.jblas.DoubleMatrix;
import org.jblas.util.Logger;
import src.EmbeddingBag;
import src.data.BagOfWords;
import src.data.GloVe;
import src.data.TrecDataset;
import src.hyperparam.HyperParams;
import src.util.FileUtil;

public class TrecClassifier {

    private final Random rnd;
    private final String trainDataPath;
    private final String devDataPath;
    private final String testDataPath;
    private TrecDataset trainSet;
    private TrecDataset devSet;
    private TrecDataset testSet;

    private int bagSize;
    private int numClasses;

    private final HyperParams params;

    private Loss lossFunc;
    private Optimizer optimizer;
    private Layer net;

    /**
     * After instantiation:
     * 1. load()
     * 2. createNetwork(net)
     * 3. train()
     * 4. evaluate()
     * */
    public TrecClassifier(int seed, String trainData, String devDataPath, String testDataPath, HyperParams params) {
        Logger.getLogger().setLevel(Logger.WARNING);
        this.rnd = getSeededRandom(seed);
        this.params = params;
        this.trainDataPath = trainData;
        this.devDataPath = devDataPath;
        this.testDataPath = testDataPath;
    }

    public void load(String vocab, String classes) throws IOException {
        System.out.println("\nLoading data...");

        // input and output dimensions
        this.bagSize = FileUtil.countLinesInFile(vocab);
        this.numClasses = FileUtil.countLinesInFile(classes);

        // data sets
        this.trainSet = loadDataset(params, true, rnd, trainDataPath, "train");
        this.devSet = loadDataset(params, false, rnd, devDataPath, "dev");
        this.testSet = loadDataset(params, false, rnd, testDataPath, "dev");
    }

    /**
     * Neural Net for Part 1
     */
    public Sequential getNetworkP1() {
        return new Sequential(new Layer[] {
                new Linear(bagSize, params.getSizeFirstHiddenLayer(), new WeightInitXavier()),
                new ReLU(),
                new Linear(params.getSizeFirstHiddenLayer(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new ReLU(),
                new Linear(params.getSizeOtherHiddenLayers(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new ReLU(),
                new Linear(params.getSizeOtherHiddenLayers(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new Softmax()});
    }

    /**
     * Neural Net for Part 2 with EmbeddingBag.
     */
    public Sequential getNetworkP2() {
        return new Sequential(new Layer[] {
                new EmbeddingBag(bagSize, params.getSizeFirstHiddenLayer(), new WeightInitXavier()),
                new ReLU(),
                new Linear(params.getSizeFirstHiddenLayer(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new ReLU(),
                new Linear(params.getSizeOtherHiddenLayers(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new ReLU(),
                new Linear(params.getSizeOtherHiddenLayers(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new Softmax()});
    }

    /**
     * Neural Net for Part 2 with GloVe embeddings.
     */
    public Sequential getNetworkP3(String glovePath) throws IOException {
        return new Sequential(new Layer[] {
                new EmbeddingBag(bagSize, params.getSizeFirstHiddenLayer(), GloVe.fromFile(glovePath)),
                new ReLU(),
                new Linear(params.getSizeFirstHiddenLayer(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new ReLU(),
                new Linear(params.getSizeOtherHiddenLayers(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new ReLU(),
                new Linear(params.getSizeOtherHiddenLayers(), params.getSizeOtherHiddenLayers(), new WeightInitXavier()),
                new Softmax()});
    }

    public void createNetwork(Layer net) {
        System.out.println("\nCreating network...");
        this.net = net;
        this.lossFunc = new CrossEntropy();
        this.optimizer = new SGD(net, params.getLearningRate());
        System.out.println(net);
    }

    public void train() {
        System.out.println("\nTraining...");
        int notAtPeak = 0;  // the number of times not at peak
        double peakAcc = -1;  // the best accuracy of the previous epochs
        double totalLoss;  // the total loss of the current epoch

        trainSet.reset(); // reset index and shuffle the data before training

        for (int e = 0; e < params.getMaxNumEpochs(); e++) {
            totalLoss = 0;

            while (true) {
                // get the next mini-batch
                Pair<DoubleMatrix, DoubleMatrix> batch = fromBatch(trainSet.getNextMiniBatch());

                if (batch == null) break;

                // always reset the gradients before performing backward
                optimizer.resetGradients();

                // calculate the loss value
                DoubleMatrix Yhat = net.forward(batch.first);
                double lossVal = lossFunc.forward(batch.second, Yhat);

                // calculate gradients of the weights using backpropagation algorithm
                net.backward(lossFunc.backward());

                // update the weights using the calculated gradients
                optimizer.updateWeights();

                totalLoss += lossVal;
            }

            // evaluate and print performance
            double trainAcc = eval(trainSet);
            double valAcc = eval(devSet);
            System.out.printf("epoch: %4d\tloss: %5.4f\ttrain-accuracy: %3.4f\tdev-accuracy: %3.4f\n", e, totalLoss, trainAcc, valAcc);

            // check termination condition
            if (valAcc <= peakAcc) {
                notAtPeak += 1;
                System.out.printf("not at peak %d times consecutively\n", notAtPeak);
            }
            else {
                notAtPeak = 0;
                peakAcc = valAcc;
            }
            if (notAtPeak == params.getPatience())
                break;
        }

        System.out.println("\ntraining is finished");
    }

    public double evaluate() {
        double testAcc = eval(testSet);
        System.out.printf("\nTest accuracy: %.4f\n", testAcc);
        return testAcc;
    }

    public double eval(TrecDataset data) {
        // reset index of the data
        data.reset();

        // the number of correct predictions so far
        double correct = 0;

        while (true) {
            // we evaluate per mini-batch
            Pair<DoubleMatrix, DoubleMatrix> batch = fromBatch(data.getNextMiniBatch());
            if (batch == null)
                break;

            // perform forward pass to compute Yhat (the predictions)
            DoubleMatrix Yhat = net.forward(batch.first);

            // the predicted class is the one with the highest probability
            int[] preds = Yhat.rowArgmaxs();

            // count how many predictions are correct
            for (int i = 0; i < preds.length; i++) {
                if (preds[i] == (int) batch.second.data[i])
                    correct++;
            }
        }

        // compute classification accuracy
        return correct / data.getSize();
    }

    public static Pair<DoubleMatrix, DoubleMatrix> fromBatch(List<Pair<BagOfWords, Integer>> batch) {
        if (batch == null)
            return null;

        double[][] xs = new double[batch.size()][];
        double[] ys = new double[batch.size()];
        for (int i = 0; i < batch.size(); i++) {
            xs[i] = batch.get(i).first.toDouble();
            ys[i] = (double)batch.get(i).second;
        }
        DoubleMatrix X = new DoubleMatrix(xs);
        DoubleMatrix Y = new DoubleMatrix(ys.length, 1, ys);
        return new Pair<>(X, Y);
    }

    public TrecDataset loadDataset(HyperParams params, boolean shuffle, Random rnd, String path, String name) throws IOException {
        TrecDataset dataset = new TrecDataset(params.getBatchSize(), shuffle, rnd, this.bagSize);
        dataset.fromFile(path);
        System.out.printf(name + ": %d instances\n", dataset.getSize());
        return dataset;
    }

    public Random getSeededRandom(Integer seed) {
        org.jblas.util.Random.seed(seed);
        return new Random(seed );
    }
}
