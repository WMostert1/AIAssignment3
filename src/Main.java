import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<String> afrikaansFiles = FileOperations.getFileNamesAt("./Training Data/Afrikaans",".txt");
        ArrayList<String> englishFiles = FileOperations.getFileNamesAt("./Training Data/English",".txt");
        
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

        ArrayList<LanguageTrainingExample> trainingExamples = new ArrayList<LanguageTrainingExample>();

        for(String dataSet : afrikaansRawDataSets){
            trainingExamples.add(new LanguageTrainingExample(dataSet,true,false));
        }

        for(String dataSet: englishFiles){
            trainingExamples.add(new LanguageTrainingExample(dataSet,false,true));
        }

        DataPreProcessor preProcessor = new DataPreProcessor();
        preProcessor.addDataPrepStrategy(new CharacterOnlyFormatPrepStrategy());
        preProcessor.applyTransforms(trainingExamples);

        for(LanguageTrainingExample example : trainingExamples) {
            example.calculateFrequencies();
            System.out.println(example.frequencies.toString());
        }

        NeuralNetwork network = new NeuralNetwork(10,0.9,0.2,10,new Bias(),new SigmoidActivationFunction());

        System.out.println(network.toString());



    }
}
