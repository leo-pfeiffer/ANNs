package src.classifier;

public class HyperParams {

    private double learningRate;
    private int maxNumEpochs;
    private int patience;
    private int batchSize;
    private int numHiddenLayers;
    private int numInputNodes;
    private int numNodesPerHiddenLayer;

    public HyperParams() {}

    public HyperParams(
            double learningRate,
            int maxNumEpochs,
            int patience,
            int batchSize,
            int numHiddenLayers,
            int numInputNodes,
            int numNodesPerHiddenLayer) {
        this.learningRate = learningRate;
        this.maxNumEpochs = maxNumEpochs;
        this.patience = patience;
        this.batchSize = batchSize;
        this.numHiddenLayers = numHiddenLayers;
        this.numInputNodes = numInputNodes;
        this.numNodesPerHiddenLayer = numNodesPerHiddenLayer;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public int getMaxNumEpochs() {
        return maxNumEpochs;
    }

    public int getPatience() {
        return patience;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getNumHiddenLayers() {
        return numHiddenLayers;
    }

    public int getNumInputNodes() {
        return numInputNodes;
    }

    public int getNumNodesPerHiddenLayer() {
        return numNodesPerHiddenLayer;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMaxNumEpochs(int maxNumEpochs) {
        this.maxNumEpochs = maxNumEpochs;
    }

    public void setPatience(int patience) {
        this.patience = patience;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setNumHiddenLayers(int numHiddenLayers) {
        this.numHiddenLayers = numHiddenLayers;
    }

    public void setNumInputNodes(int numInputNodes) {
        this.numInputNodes = numInputNodes;
    }

    public void setNumNodesPerHiddenLayer(int numNodesPerHiddenLayer) {
        this.numNodesPerHiddenLayer = numNodesPerHiddenLayer;
    }

}
