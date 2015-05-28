import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wernermostert on 2015/05/27.
 */
public class ExperimentalTesting {
    private ArrayList<LanguageTrainingExample> trainingExamples;
    private ArrayList<LanguageTrainingExample> generalizationExamples;
    private ArrayList<LanguageTrainingExample> dataSet;
    private double percentageTrainingExample;

    public ExperimentalTesting(ArrayList<LanguageTrainingExample> dataSet,double percentageTrainingExample) {
        trainingExamples = new ArrayList<LanguageTrainingExample>();
        generalizationExamples = new ArrayList<LanguageTrainingExample>();
        this.dataSet = dataSet;
        this.percentageTrainingExample = percentageTrainingExample;
    }

    public String getExcelAveragesFormula(int runs,int epochNo){
        int alphabetCounter = 1;
        int alphabetPassCounter = -1;
        String result= epochNo+",=AVERAGE(";
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        //At

        for(int i = 0;i<runs;i++){

            if(alphabetPassCounter > -1){
                result+= ALPHABET.charAt(alphabetPassCounter);
            }
            result+=ALPHABET.charAt(alphabetCounter);
            result+= epochNo+3+";";


            alphabetCounter += 3;
            if(alphabetCounter > 25) alphabetPassCounter++;
            alphabetCounter %= 26;
        }
        result = result.substring(0,result.length()-1)+"),=AVERAGE(";

        //Ag
        alphabetCounter = 2;
        alphabetPassCounter = -1;
        for(int i = 0;i<runs;i++){

            if(alphabetPassCounter > -1){
                result+= ALPHABET.charAt(alphabetPassCounter);
            }
            result+=ALPHABET.charAt(alphabetCounter);
            result+= epochNo+3+";";


            alphabetCounter += 3;
            if(alphabetCounter > 25) alphabetPassCounter++;
            alphabetCounter %= 26;
        }
        return result.substring(0,result.length()-1)+")";



    }

    public void generateCompleteSet(){
        //generateTrainingData(1,0.98,0.07,100,"Figure2(1).csv");
        int counter = 0;
//        for(double momentum = 0.0; momentum <= 1.0; momentum += 0.1){
//            generateTrainingData(1,momentum,0.07,100,"PM"+(counter++)+".csv");
//        }
//        counter = 0;
//        for(double momentum = 0.9; momentum <= 1.0; momentum += 0.01){
//            generateTrainingData(1,momentum,0.07,30,"PA"+(counter++)+".csv");
//        }

//        counter = 0;
//        for(double learningRate = 0.0; learningRate <= 1.0; learningRate += 0.1){
//            generateTrainingData(1,0.98,learningRate,30,"PL"+(counter++)+".csv");
//        }

        counter = 0;
        for(int hiddenNodes = 1; hiddenNodes <= 52; hiddenNodes ++){
            generateTrainingData(hiddenNodes,0.98,0.2,30,"PH"+(counter++)+".csv");
        }

        System.out.println("Data Generated");
    }

    public void generateTrainingData(int hiddenNodes,double momentum,double learningRate,int maxEpochs,String fileName){
        //Hidden nodes, momentum, learningRate, maxEpochs
//        NetworkConfiguration bestConfig = new NetworkConfiguration();
//        for(int hiddenNodes = 1; hiddenNodes <= 1;hiddenNodes++) {
//            System.out.println((hiddenNodes/20.0)*100+"%");
//            for(double momentum = 0.98; momentum <= 0.98; momentum+=0.01) {
//                for(double learningRate = 0.07; learningRate <= 0.07; learningRate += 0.01) {
//                    for(int maxEpochs = 25; maxEpochs <= 25; maxEpochs++) {
                        //Shuffle the generalization and training examples
                        trainingExamples = new ArrayList<LanguageTrainingExample>();
                        generalizationExamples = new ArrayList<LanguageTrainingExample>();
                        Random r = new Random();
                        int size = (int)(percentageTrainingExample*dataSet.size());
                        for(int i = 0; i < size ;i++){
                            int randomIndex = r.nextInt()%dataSet.size();
                            if(randomIndex < 0) randomIndex *= -1;

                            if(!trainingExamples.contains(dataSet.get(randomIndex)))
                                trainingExamples.add(dataSet.get(randomIndex));
                            else
                                i--;
                        }
                        for(LanguageTrainingExample ex : dataSet){
                            if(!trainingExamples.contains(ex))
                                generalizationExamples.add(ex);
                        }
                        //Run configuration test
                        String [] finalResults = new String[maxEpochs+1];
                        for(int i =0 ; i < finalResults.length;i++)
                            finalResults[i] = "";
                        final int MAX_COUNT = 50;
                        for(int runCount = 0; runCount < MAX_COUNT;runCount++) {
                            NeuralNetwork network = new NeuralNetwork(hiddenNodes, momentum, learningRate, maxEpochs, new Bias(), new SigmoidActivationFunction());
                            String configurationResult = network.train(trainingExamples, generalizationExamples);
                            String [] resultsPerLine = configurationResult.split("\n");
                            for(int i = 0; i < finalResults.length;i++){
                                 finalResults[i] += resultsPerLine[i];
                            }
                        }
                        String finalResult = "";
                        for(int i = 0; i<MAX_COUNT;i++){
                            finalResult += "SampleRun"+i+",,,";
                        }
                        finalResult+="\n";
                        finalResult+=finalResults[0]+",EpochNo,Average At, Average Ag, for HiddenNodes = "+hiddenNodes+"; Momentum = "+momentum+"; LearningRate = "+learningRate+"\n";
                        for(int i = 1; i<finalResults.length;i++){
                            finalResult += finalResults[i]+","+getExcelAveragesFormula(MAX_COUNT,i-1)+"\n";
                        }

                        FileOperations.writeToFile(finalResult,fileName);


//                    }
//                }
//            }
//        }
    }
}
