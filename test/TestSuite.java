package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.data.GloVeTest;
import test.data.TrecDatasetWordsTest;
import test.util.FileUtilTest;

/**
 * Test suite for all JUnit tests.
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                TrecDatasetWordsTest.class,
                FileUtilTest.class,
                EmbeddingBagTest.class,
                GloVeTest.class
        }
)
public class TestSuite {
}