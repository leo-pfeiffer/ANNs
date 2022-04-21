import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Random;
import minet.layer.Layer;
import minet.layer.Linear;
import minet.layer.init.WeightInitXavier;
import minet.util.Pair;
import org.jblas.DoubleMatrix;
import org.junit.Test;
import src.EmbeddingBag;
import src.classifier.TrecClassifier;
import src.data.TrecDataset;
import src.util.FileUtil;

public class EmbeddingBagTest {

    String trainData = "data/part1/train.txt";
    String vocabFile = "data/part1/vocab.txt";
    static {
        org.jblas.util.Random.seed(42);
    }
    Random rnd = new Random(42);

}
