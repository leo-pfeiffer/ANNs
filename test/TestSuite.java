package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.data.BagOfWordsTest;
import test.data.PreComputedWordEmbeddingTest;
import test.data.TrecDatasetWordsTest;
import test.util.FileUtilTest;

/**
 * Test suite for all JUnit tests.
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                BagOfWordsTest.class,
                TrecDatasetWordsTest.class,
                FileUtilTest.class,
                EmbeddingBagTest.class,
                PreComputedWordEmbeddingTest.class
        }
)
public class TestSuite {
}