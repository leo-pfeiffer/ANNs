package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;
import minet.layer.Layer;
import minet.layer.Sequential;
import minet.layer.init.WeightInitUniform;
import minet.loss.CrossEntropy;
import minet.util.GradientChecker;
import org.jblas.DoubleMatrix;
import org.junit.Test;
import src.EmbeddingBag;

public class EmbeddingBagTest {

    String trainData = "data/part1/train.txt";
    String vocabFile = "data/part1/vocab.txt";
    static {
        org.jblas.util.Random.seed(42);
    }
    Random rnd = new Random(42);

    @Test
    public void testGradient() {
        // todo not sure if this is correct

        DoubleMatrix X = new DoubleMatrix(
                new double[][] {
                        {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 1, 0, 0, 1}
                });
        DoubleMatrix Y = new DoubleMatrix(new double[] {1, 3, 5});
        Sequential net = new Sequential(new Layer[] {
                new EmbeddingBag(10, 100, new WeightInitUniform(-1, 1)),
        });

        CrossEntropy loss = new CrossEntropy();
        boolean pass = GradientChecker.checkGradient(net, loss, X, Y);
        assertTrue(pass);
    }

    @Test
    public void testTransposeExplicit() {
        ArrayList<int[]> explicit = new ArrayList<>();
        explicit.add(new int[]{2, 3});
        explicit.add(new int[]{7, 8});
        explicit.add(new int[]{9});

        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[]{});
        expected.add(new int[]{});
        expected.add(new int[]{0});
        expected.add(new int[]{0});
        expected.add(new int[]{});
        expected.add(new int[]{});
        expected.add(new int[]{});
        expected.add(new int[]{1});
        expected.add(new int[]{1});
        expected.add(new int[]{2});

        ArrayList<int[]> transposed = EmbeddingBag.transposeExplicit(explicit, 10);
        assertEquals(transposed.size(), 10);

        for (int i = 0; i < transposed.size(); i++) {
            assertArrayEquals(transposed.get(i), expected.get(i));
        }



    }

}
