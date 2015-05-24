import java.text.Normalizer;

/**
 * Created by wernermostert on 2015/05/23.
 */
public class CharacterOnlyFormatPrepStrategy implements DataPrepStrategy{
    @Override
    public LanguageTrainingExample transform(LanguageTrainingExample languageTrainingExample) {
        //Remove diacritical marks
        languageTrainingExample.dataset = Normalizer.normalize(languageTrainingExample.dataset, Normalizer.Form.NFD);
        languageTrainingExample.dataset = languageTrainingExample.dataset.replaceAll("\\p{M}", "");

        //To lower case
        languageTrainingExample.dataset = languageTrainingExample.dataset.toLowerCase();

        //Remove any whitespace / other characters that may have slipped through
        String newDataSet = "";
        for(char c : languageTrainingExample.dataset.toCharArray()){
            if(LanguageTrainingExample.ALPHABET.contains(""+c)){
                newDataSet += c;
            }
        }
        languageTrainingExample.dataset = newDataSet;
        return languageTrainingExample;
    }
}
