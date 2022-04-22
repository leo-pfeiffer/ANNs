package evaluation;

import java.util.Arrays;
import java.util.Random;
import minet.layer.Layer;
import minet.layer.Linear;
import minet.layer.Sequential;
import minet.layer.init.WeightInitUniform;
import org.jblas.DoubleMatrix;
import src.EmbeddingBag;

public class Timer {

    public static DoubleMatrix randMat(int rows, int cols, int numOne) {
        assert cols <= numOne;
        double percOne = numOne / (double) cols;
        Random rnd = new Random();
        double[][] mat = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rnd.nextDouble() < percOne) {
                    mat[i][j] = 1;
                } else {
                    mat[i][j] = 0;
                }
            }
        }
        return new DoubleMatrix(mat);
    }

    public static void withDimension(int outDims, int numExp, int numSamples, int vocabSize) {
        Sequential net1 = new Sequential(new Layer[] {
                new Linear(vocabSize, outDims, new WeightInitUniform(-1, 1)),
        });

        Sequential net2 = new Sequential(new Layer[] {
                new EmbeddingBag(vocabSize, outDims, new WeightInitUniform(-1, 1)),
        });

        long[] times1 = new long[numExp];
        long[] times2 = new long[numExp];

        long s1 = 0;
        long s2 = 0;

        for (int i = 0; i < numExp; i++) {
            DoubleMatrix x = randMat(numSamples, vocabSize, 10);
            long p1 = System.nanoTime();
            net1.forward(x);
            long p2 = System.nanoTime();
            net2.forward(x);
            long p3 = System.nanoTime();
            times1[i] = p2 - p1;
            times2[i] = p3 - p2;
            s1 += times1[i];
            s2 += times2[i];
        }

        System.out.println("Net 1: " + s1 / numExp);
        System.out.println("Net 2: " + s2 / numExp);
    }

    public static void main(String[] args) {
        Timer.withDimension(100, 50, 100, 100000);
    }

}