import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by wernermostert on 2015/05/23.
 * A container class representing training examples
 */
public class LanguageTrainingExample{
    static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private Hashtable<Character,Double> frequencies = new Hashtable<Character, Double>();
    public String dataset;
    public double afrikaansClassification;
    public double englishClassification;

    /**
     * Simple constructor
     * @param text The raw input text
     * @param afrikaans Classification value
     * @param english Classification value
     */
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

    /**
     * Calculates and scales the frequencies of each character in the text to the active
     * domain of the sigmoid function
     */
    public void calculateFrequencies(){
        frequencies.clear();
        for(int i = 0; i<ALPHABET.length();i++){
            char alphabetCharacter = ALPHABET.charAt(i);
            long occurrences = 0;

            for(char c : dataset.toCharArray())
                if(c == alphabetCharacter)
                    occurrences++;
            double characterFrequency = (double)occurrences/dataset.length();

            //Scale the frequencies to the sigmoid activation function
            //TODO: Make sure this is right
            //System.out.println(characterFrequency);

            frequencies.put(alphabetCharacter,characterFrequency);
        }

        double MaxFreq = frequencies.get('a');
        double MinFreq = frequencies.get('a');

        for(Character c : frequencies.keySet()){
            double freq = frequencies.get(c);
            if(freq > MaxFreq)
                MaxFreq = freq;
            if(freq < MinFreq)
                MinFreq = freq;
        }

        for(Character c : frequencies.keySet()) {
            frequencies.replace(c,(frequencies.get(c) - MinFreq) / (MaxFreq - MinFreq) * (Math.sqrt(3) - (-Math.sqrt(3))) + (-Math.sqrt(3)));
        }



    }

    /**
     * Converts hash table to an array
     * @return character frequencies
     */
    public ArrayList<Double> getInputValues(){
        ArrayList<Double> result = new ArrayList<Double>();
        for(Character c : frequencies.keySet()){
            result.add(frequencies.get(c));
        }
        result.add(-1.0);
        return result;
    }

}
