package langReco.reco;

import langReco.eval.Performance;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * Created by Lea on 28/01/2017.
 */
public class TestRuns {
    @Test
    public void TestRuns() {
        String goldSentPath = "data/gold/test-sent.txt";
        //test with
        //LanguageRecognizer my2 = new MyLanguageRecognizer2("lm/ULTIME_CONFIG.txt");
        //LanguageRecognizer my3 = new MyLanguageRecognizer3("lm/ULTIME_CONFIG.txt");

        String hypLangFilePath = "data/test/test-sent-hyp";
        //my2.recognizeFileLanguage(goldSentPath, hypLangFilePath+"1.txt");
        //my3.recognizeFileLanguage(goldSentPath, hypLangFilePath+"2.txt");

    }


    @Rule
    public TestName name = new TestName();


    @Before
    public void printSeparator()
    {
        System.out.println("\n=== " + name.getMethodName() + " =====================");
    }

}
