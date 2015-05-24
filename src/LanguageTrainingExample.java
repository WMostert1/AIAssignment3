import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by wernermostert on 2015/05/23.
 */
public class LanguageTrainingExample{
    static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public Hashtable<Character,Double> frequencies = new Hashtable<Character, Double>();
    public String dataset;
    public double afrikaansClassification;
    public double englishClassification;


    public LanguageTrainingExample(String text,boolean afrikaans,boolean english){
        dataset = text;
        if(afrikaans){
            afrikaansClassification = 1.0;
            englishClassification = 0.0;
        }
        else if(english){
            afrikaansClassification = 0.0;
            englishClassification = 1.0;
        }
    }

    public void calculateFrequencies(){
        frequencies.clear();
        for(int i = 0; i<ALPHABET.length();i++){
            char alphabetCharacter = ALPHABET.charAt(i);
            long occurrences = 0;

            for(char c : dataset.toCharArray())
                if(c == alphabetCharacter)
                    occurrences++;
            double characterFrequency = (double)occurrences/dataset.length();
            frequencies.put(alphabetCharacter,characterFrequency);
        }
    }

}
