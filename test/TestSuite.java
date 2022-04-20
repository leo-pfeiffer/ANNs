import data.BagOfWordsTest;
import data.TrecDatasetWordsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all JUnit tests.
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                TrecDatasetWordsTest.class,
                BagOfWordsTest.class
        }
)
public class TestSuite {
}