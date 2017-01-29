package langReco.reco;

import langModel.LanguageModel;
import langModel.MyLaplaceLanguageModel;
import langModel.MyNgramCounts;
import langModel.NgramCounts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
/**This Language recognizer is able to compute 2 or more level of language models
 * and has a minimum probability to detect unknown language*/
public class MyLanguageRecognizer2 extends LanguageRecognizer {

	
	private HashMap<String, ArrayList<LanguageModel>> models;

	//fileParam doit comporter des chemins vers d'autres fichiers de configuration
	public MyLanguageRecognizer2(String fileParam){
		models = new HashMap();
		
		try {
			//On lit le "gros" fichier de configuration
			BufferedReader brGros=new BufferedReader(new FileReader(fileParam));
			String line;
			while ((line=brGros.readLine())!=null){
				// On lit les "petits" fichiers indiqués
				BufferedReader brPetit=new BufferedReader(new FileReader(line));
				String smallLine;
				while((smallLine=brPetit.readLine())!=null){
					String[] split = smallLine.split("\\s");
					NgramCounts NgramCounts = new MyNgramCounts();
					NgramCounts.readNgramCountsFile(split[2]);
					LanguageModel llm = new MyLaplaceLanguageModel();
					llm.setNgramCounts(NgramCounts);
					ArrayList<LanguageModel> tmpArr = models.get(split[0]);
					if (tmpArr == null)
						tmpArr = new ArrayList();
					tmpArr.add(llm);
					models.put(split[0], tmpArr);
				}
				brPetit.close();
			}
			brGros.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String recognizeSentenceLanguage(String sentence) {
		double size = sentence.split(" ").length;
		String lan = "unk";
		double max = 0;
		for (String lang : models.keySet()){
			ArrayList<LanguageModel> tmpArr = models.get(lang);
			double proba1 = tmpArr.get(0).getSentenceProb(sentence);
			double proba2 = tmpArr.get(1).getSentenceProb(sentence);
			double res = proba2/proba1;
			double seuil1 = Math.pow(1.0/tmpArr.get(0).getVocabularySize(), size*0.8);//prob d'un mot qui n'existe pas
			double seuil2 = Math.pow(1.0/tmpArr.get(1).getVocabularySize(), size*0.8);//prob d'un mot qui n'existe pas
			double seuilTot =  seuil2/seuil1;
			// On vérifie qu'on dépasse le seuil d'une langue inconnue
			if(res>max && res > seuilTot){
				max = res;
				lan = lang;

			}
		}
		return lan;
	}

}
