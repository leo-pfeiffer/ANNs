package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void testGradient() {
        DoubleMatrix X = new DoubleMatrix(
                new double[][]{
                        {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 1, 0, 0, 1}
                });
        DoubleMatrix Y = new DoubleMatrix(new double[]{1, 3, 5});
        Sequential net = new Sequential(new Layer[]{
                new EmbeddingBag(10, 100, new WeightInitUniform(-1, 1)),
        });

        CrossEntropy loss = new CrossEntropy();
        boolean pass = GradientChecker.checkGradient(net, loss, X, Y);
        assertTrue(pass);
    }

    @Test
    public void testGradientRandom() {

        Random rnd = new Random();

        int maxSampSize = 50;
        int maxVocabSize = 30;

        int numIt = 100;
        for (int i = 0; i < numIt; i++) {
            int vocabSize = rnd.nextInt(maxVocabSize) + 1;
            int sampSize = rnd.nextInt(maxSampSize) + 1;

            double[][] x = new double[sampSize][];
            double[] y = new double[sampSize];

            for (int j = 0; j < sampSize; j++) {
                double[] row = new double[vocabSize];
                y[j] = rnd.nextInt(vocabSize);
                for (int k = 0; k < vocabSize; k++) {
                    row[k] = rnd.nextInt(2);
                }
                x[j] = row;
            }

            DoubleMatrix X = new DoubleMatrix(x);
            DoubleMatrix Y = new DoubleMatrix(y);

            Sequential net = new Sequential(new Layer[]{
                    new EmbeddingBag(vocabSize, 100, new WeightInitUniform(-1, 1)),
            });

            CrossEntropy loss = new CrossEntropy();
            boolean pass = GradientChecker.checkGradient(net, loss, X, Y);
            assertTrue(pass);
        }
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


    @Test
    public void testTransposeExplicitEmpty1() {
        ArrayList<int[]> explicit = new ArrayList<>();
        explicit.add(new int[]{});
        explicit.add(new int[]{});
        explicit.add(new int[]{});

        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[]{});
        expected.add(new int[]{});
        expected.add(new int[]{});
        expected.add(new int[]{});

        ArrayList<int[]> transposed = EmbeddingBag.transposeExplicit(explicit, 4);
        assertEquals(transposed.size(), 4);

        for (int i = 0; i < transposed.size(); i++) {
            assertArrayEquals(transposed.get(i), expected.get(i));
        }
    }

    @Test
    public void testTransposeExplicitEmpty2() {
        ArrayList<int[]> explicit = new ArrayList<>();

        ArrayList<int[]> expected = new ArrayList<>();
        expected.add(new int[]{});
        expected.add(new int[]{});

        ArrayList<int[]> transposed = EmbeddingBag.transposeExplicit(explicit, 2);
        assertEquals(transposed.size(), 2);

        for (int i = 0; i < transposed.size(); i++) {
            assertArrayEquals(transposed.get(i), expected.get(i));
        }
    }

    @Test
    public void testToExplicit() {
        DoubleMatrix X = new DoubleMatrix(
                new double[][]{
                        {0, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 1, 0, 0, 1}
                });

        List<int[]> explicit = EmbeddingBag.toExplicit(X);
        assertEquals(3, explicit.size());
        assertArrayEquals(new int[]{1, 3}, explicit.get(0));
        assertArrayEquals(new int[]{0, 2, 8}, explicit.get(1));
        assertArrayEquals(new int[]{0, 6, 9}, explicit.get(2));
    }

    @Test
    public void testToExplicitEmpty() {
        DoubleMatrix X = new DoubleMatrix(
                new double[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                });

        List<int[]> explicit = EmbeddingBag.toExplicit(X);
        assertEquals(3, explicit.size());
        assertArrayEquals(new int[]{}, explicit.get(0));
        assertArrayEquals(new int[]{}, explicit.get(1));
        assertArrayEquals(new int[]{}, explicit.get(2));
    }

    @Test
    public void testCalcElem() {

        DoubleMatrix X = new DoubleMatrix(
                new double[][]{
                        {1, 0},
                        {1, 1},
                        {0, 1},
                        {0, 0}
                });

        DoubleMatrix Y = new DoubleMatrix(
                new double[][]{
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                });

        List<int[]> explicit = EmbeddingBag.toExplicit(X);

        double[][] expected = new double[][]{
                new double[]{1, 2, 3, 4},
                new double[]{6, 8, 10, 12},
                new double[]{5, 6, 7, 8},
                new double[]{0, 0, 0, 0},
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                double exp = expected[i][j];
                double act = EmbeddingBag.calcElem(i, j, explicit, Y);
                assertEquals(exp, act, 0.0);
            }
        }
    }
}
