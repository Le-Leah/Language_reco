package langModel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by E154981H on 20/01/17.
 */
public class MyNaiveLanguageTest {
    public MyNaiveLanguageModel set(){
        MyNgramCounts ngramC = new MyNgramCounts();
        String texte = "<s> bonjour </s>\n<s> Ce cours est genial </s>\n<s> La carottes est cuite </s>\n" +
                "<s> le lapin est la </s>";
        ngramC.scanTextString(texte, 2);
        MyNaiveLanguageModel nlm = new MyNaiveLanguageModel();
        nlm.setNgramCounts(ngramC);
        return  nlm;
    }

    @Test
    public void testVocabularySize(){
        MyNaiveLanguageModel nlm = this.set();
        assertEquals(13,nlm.getVocabularySize());
    }


    @Test
    public void testGetNgramProb() {
        MyNaiveLanguageModel nlm = this.set();
        assertEquals(1, (double) nlm.getNgramProb("Ce cours"), 0.001);//delta =precision
        assertEquals(0, (double) nlm.getNgramProb("poule"), 0.001);
        assertEquals(0, (double) nlm.getNgramProb("Ce genial"), 0.001);
    }

    @Test
    public void testSentenceProb() {
        MyNaiveLanguageModel nlm = this.set();
        assertEquals((double)4/21, (double) nlm.getNgramProb("<s>"), 0.001);
        assertEquals((double)1/4, (double) nlm.getNgramProb("<s> Ce"), 0.001);
        assertEquals((double)1, (double) nlm.getNgramProb("Ce cours"), 0.001);
        assertEquals((double)1, (double) nlm.getNgramProb("cours est"), 0.001);
        assertEquals((double)1/3, (double) nlm.getNgramProb("est la"), 0.001);
        assertEquals((double)1, (double) nlm.getNgramProb("la </s>"), 0.001);
        assertEquals((double)(4/(double)(21*4*3)),(double)nlm.getSentenceProb("<s> Ce cours est la </s>"),0.001);//dela =precision
    }

}
