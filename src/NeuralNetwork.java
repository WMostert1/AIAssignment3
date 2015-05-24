import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wernermostert on 2015/05/23.
 */
public class NeuralNetwork{
    private double learningRate;
    private double momentum;
    private int hiddenNodes;
    private int maxEpochs;
    private ArrayList<Node> inputLayer;
    private ArrayList<Node> hiddenLayer;
    private ArrayList<Node> outputLayer;

    public NeuralNetwork(int hiddenNodes, double momentum, double learningRate,int maxEpochs,
                         ActivationFunction bias, ActivationFunction activationFunction) {
        this.hiddenNodes = hiddenNodes;
        this.momentum = momentum;
        this.learningRate = learningRate;
        this.maxEpochs = maxEpochs;

        inputLayer = new ArrayList<Node>();
        for(int i = 0; i < LanguageTrainingExample.ALPHABET.length();i++){
            inputLayer.add(new Node(activationFunction,""+i));
        }
        inputLayer.add(new Node(bias,""+LanguageTrainingExample.ALPHABET.length()));

        hiddenLayer = new ArrayList<Node>();
        for(int i = 0; i < hiddenNodes;i++){
            hiddenLayer.add(new Node(activationFunction,""+i));
        }
        hiddenLayer.add(new Node(bias,""+hiddenNodes));

        outputLayer = new ArrayList<Node>();
        outputLayer.add(new Node(activationFunction,"Afrikaans")); //afrikaans
        outputLayer.add(new Node(activationFunction,"English")); //english

        //link input -> hidden
        double range = 1.0/Math.sqrt(LanguageTrainingExample.ALPHABET.length());
        Random r = new Random();
        for(int input = 0; input < LanguageTrainingExample.ALPHABET.length()+1;input++){
            for(int hidden = 0; hidden < hiddenNodes;hidden++){

                double randomWeight = -range + (range - (-range) * r.nextDouble());

                inputLayer.get(input).weightsOut.add(
                        new Edge(inputLayer.get(input),hiddenLayer.get(hidden),input+"->"+hidden,randomWeight)
                );
                hiddenLayer.get(hidden).weightsIn.add(inputLayer.get(input).weightsOut.get(hidden));
            }
        }

        //link hidden -> output
        range = 1.0/Math.sqrt(hiddenNodes);
        for(int hidden = 0; hidden < hiddenNodes+1;hidden++){
            for(int output = 0; output<outputLayer.size();output++){
                double randomWeight = -range + (range - (-range) * r.nextDouble());
                hiddenLayer.get(hidden).weightsOut.add(
                        new Edge(hiddenLayer.get(hidden),outputLayer.get(output),hidden+"->"+outputLayer.get(output).label,randomWeight)
                );
                outputLayer.get(output).weightsIn.add(hiddenLayer.get(hidden).weightsOut.get(output));
            }
        }

    }

    public void train(ArrayList<LanguageTrainingExample> trainingExamples){
        for( int epochCounter = 0; epochCounter < maxEpochs; epochCounter++){
            double trainingAccuracy = 0.0;
            for(LanguageTrainingExample trainingExample : trainingExamples){


            }
        }


    }

    @Override
    public String toString() {
        String output = "Input -> Hidden:\n----------\n";
        for(Node n : inputLayer){
            for(Edge e : n.weightsOut){
                output += e.label+" "+e.weight+"\n";
            }
        }
        output += "Hidden -> Output:\n-----------\n";
        for(Node n : hiddenLayer){
            for(Edge e : n.weightsOut){
                output += e.label+" "+e.weight+"\n";
            }
        }

        return output;
    }
}
