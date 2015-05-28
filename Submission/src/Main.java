import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	    //Get a list of file names from the data directories
        ArrayList<String> afrikaansFiles = FileOperations.getFileNamesAt("./Training Data/Afrikaans",".txt");
        ArrayList<String> englishFiles = FileOperations.getFileNamesAt("./Training Data/English",".txt");

        //Read the data from each text file
        ArrayList<String> afrikaansRawDataSets = new ArrayList<String>();
        for(String afrikaansFile : afrikaansFiles){
            try {
                afrikaansRawDataSets.add(FileOperations.readFile("./Training Data/Afrikaans/"+afrikaansFile));
            }catch (IOException ioe){
                System.out.println("Error reading data from : "+"./Training Data/Afrikaans/"+afrikaansFile);
            }
        }

        ArrayList<String> englishRawDataSets = new ArrayList<String>();
        for(String englishFile : englishFiles){
            try {
                englishRawDataSets.add(FileOperations.readFile("./Training Data/English/"+englishFile));
            }catch (IOException ioe){
                System.out.println("Error reading data from : "+"./Training Data/Afrikaans"+englishFile);
            }
        }
        ArrayList<LanguageTrainingExample> examples = new ArrayList<LanguageTrainingExample>();

        //Create Training Examples
        for(String dataSet : afrikaansRawDataSets){
            examples.add(new LanguageTrainingExample(dataSet,true,false));
        }

        for(String dataSet: englishFiles){
            examples.add(new LanguageTrainingExample(dataSet,false,true));
        }

        //Pre Processing phase

        DataPreProcessor preProcessor = new DataPreProcessor();
        preProcessor.addDataPrepStrategy(new CharacterOnlyFormatPrepStrategy());
        preProcessor.applyTransforms(examples);

        //Calculate the frequencies after pre-processing
        for(LanguageTrainingExample ex : examples)
            ex.calculateFrequencies();


        ExperimentalTesting experimentalTesting = new ExperimentalTesting(examples,0.8);
        experimentalTesting.generateCompleteSet();




    }
}
