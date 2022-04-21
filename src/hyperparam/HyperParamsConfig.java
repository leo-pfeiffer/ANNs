package src.hyperparam;

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
            int sizeFirstHiddenLayer,
            int sizeOtherHiddenLayers
    ) {
        params = new HyperParams(
                learningRate,
                maxNumEpochs,
                patience,
                batchSize,
                numHiddenLayers,
                sizeFirstHiddenLayer,
                sizeOtherHiddenLayers
        );
    }

    public HyperParams getParams() {
        return params;
    }
}
