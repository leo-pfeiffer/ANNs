package test.data;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import src.data.BagOfWords;

public class BagOfWordsTest {

    @Test
    public void testCreate() {
        BagOfWords bow = new BagOfWords(new int[]{1, 2, 3}, 6);
        assertNotNull(bow);
    }

    @Test
    public void testGetBag() {
        BagOfWords bow = new BagOfWords(new int[]{1, 2, 3}, 6);
        assertArrayEquals(new int[]{0, 1, 1, 1, 0, 0}, bow.getBag());
    }

    @Test
    public void testToDouble() {
        BagOfWords bow = new BagOfWords(new int[]{1, 2, 3}, 6);
        double[] expected = new double[] {0., 1., 1., 1., 0., 0.};
        double[] actual = bow.toDouble();

        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i], 0.01);
        }
    }
}
