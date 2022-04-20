package src.classifier;

public enum HyperParamsConfig {
    PART1(0.1,
            500,
            10,
            50,
            3,
            100,
            200);

    final HyperParams params;

    HyperParamsConfig(
            double learningRate,
            int maxNumEpochs,
            int patience,
            int batchSize,
            int numHiddenLayers,
            int numInputNodes,
            int numNodesPerHiddenLayer
    ) {
        params = new HyperParams(
                learningRate,
                maxNumEpochs,
                patience,
                batchSize,
                numHiddenLayers,
                numInputNodes,
                numNodesPerHiddenLayer
        );
    }
}
