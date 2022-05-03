package src.hyperparam;

public class HyperParams {

    private double learningRate;
    private int maxNumEpochs;
    private int patience;
    private int batchSize;
    private int numHiddenLayers;
    private int sizeFirstHiddenLayer;
    private int sizeOtherHiddenLayers;

    public HyperParams() {
    }

    public HyperParams(
            double learningRate,
            int maxNumEpochs,
            int patience,
            int batchSize,
            int numHiddenLayers,
            int sizeFirstHiddenLayer,
            int sizeOtherHiddenLayers) {
        this.learningRate = learningRate;
        this.maxNumEpochs = maxNumEpochs;
        this.patience = patience;
        this.batchSize = batchSize;
        this.numHiddenLayers = numHiddenLayers;
        this.sizeFirstHiddenLayer = sizeFirstHiddenLayer;
        this.sizeOtherHiddenLayers = sizeOtherHiddenLayers;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public int getMaxNumEpochs() {
        return maxNumEpochs;
    }

    public void setMaxNumEpochs(int maxNumEpochs) {
        this.maxNumEpochs = maxNumEpochs;
    }

    public int getPatience() {
        return patience;
    }

    public void setPatience(int patience) {
        this.patience = patience;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getNumHiddenLayers() {
        return numHiddenLayers;
    }

    public void setNumHiddenLayers(int numHiddenLayers) {
        this.numHiddenLayers = numHiddenLayers;
    }

    public int getSizeFirstHiddenLayer() {
        return sizeFirstHiddenLayer;
    }

    public void setSizeFirstHiddenLayer(int sizeFirstHiddenLayer) {
        this.sizeFirstHiddenLayer = sizeFirstHiddenLayer;
    }

    public int getSizeOtherHiddenLayers() {
        return sizeOtherHiddenLayers;
    }

    public void setSizeOtherHiddenLayers(int sizeOtherHiddenLayers) {
        this.sizeOtherHiddenLayers = sizeOtherHiddenLayers;
    }

    @Override
    public String toString() {
        return "learningRate: " + learningRate + "\n" +
                "maxNumEpochs: " + maxNumEpochs + "\n" +
                "patience: " + patience + "\n" +
                "batchSize: " + batchSize + "\n" +
                "numHiddenLayers: " + numHiddenLayers + "\n" +
                "sizeFirstHiddenLayer: " + sizeFirstHiddenLayer + "\n" +
                "sizeOtherHiddenLayers: " + sizeOtherHiddenLayers + "\n";
    }

}
