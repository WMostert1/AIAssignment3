import java.text.DecimalFormat;
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
        inputLayer.add(new Node(bias,""+LanguageTrainingExample.ALPHABET.length(),true));
        inputLayer.get(inputLayer.size()-1).nodeValue = -1.0;


        hiddenLayer = new ArrayList<Node>();
        for(int i = 0; i < hiddenNodes;i++){
            hiddenLayer.add(new Node(activationFunction,""+i));
        }
        hiddenLayer.add(new Node(bias,""+hiddenNodes,true));
        hiddenLayer.get(hiddenLayer.size()-1).nodeValue = -1.0;


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

    public String train(ArrayList<LanguageTrainingExample> trainingExamples,ArrayList<LanguageTrainingExample> generalizationExamples){
        if(trainingExamples == null || trainingExamples.size() == 0){
            System.out.println("ERROR: No Training Set");
            return null;
        }
        if(generalizationExamples == null || generalizationExamples.size() == 0){
            System.out.println("ERROR: No Generalization Set Specified");
            return null;
        }

        String result = "Epoch,At,Ag,\n";
        double generalizationAccuracy = 0.0;
        double trainingAccuracy = 0.0;
        for( int epochCounter = 0; epochCounter < maxEpochs; epochCounter++){
            trainingAccuracy = 0.0;
            for(LanguageTrainingExample trainingExample : trainingExamples){
                ArrayList<Double> inputFields = trainingExample.getInputValues();

                for(int i = 0; i < LanguageTrainingExample.ALPHABET.length();i++){
                    inputLayer.get(i).nodeValue = inputFields.get(i);
                }

                inputLayer.get(LanguageTrainingExample.ALPHABET.length()).nodeValue = -1.0;

                for(Node hiddenNode : hiddenLayer){
                    double net = 0.0;
                    for(Edge e : hiddenNode.weightsIn){
                        net += e.weight*e.from.nodeValue;
                    }
                    hiddenNode.f(net);
                }

                for(Node outputNode : outputLayer){
                    double net = 0.0;
                    for(Edge e : outputNode.weightsIn){
                        net += e.weight*e.from.nodeValue;
                    }
                    outputNode.f(net);
                    if(outputNode.nodeValue >= 0.7){
                        outputNode.nodeValue = 1.0;
                    }
                    else if(outputNode.nodeValue <= 0.3){
                        outputNode.nodeValue = 0.0;
                    }
                }

                double accuracy = 1.0;
                if(outputLayer.get(0).nodeValue != trainingExample.afrikaansClassification)
                    accuracy = 0.0;
                if(outputLayer.get(1).nodeValue != trainingExample.englishClassification)
                    accuracy = 0.0;

                trainingAccuracy += accuracy;

                //Back propagation
                //Output later error signals
                Node afrikaansOutput = outputLayer.get(0);
                afrikaansOutput.errorSignal = -(trainingExample.afrikaansClassification - afrikaansOutput.nodeValue)*
                                                (1.0-afrikaansOutput.nodeValue)*afrikaansOutput.nodeValue;

                Node englishOutput = outputLayer.get(1);
                englishOutput.errorSignal = -(trainingExample.englishClassification - englishOutput.nodeValue)*
                        (1.0-englishOutput.nodeValue)*englishOutput.nodeValue;


                //Hidden layer error signals
                for(Node hiddenNode : hiddenLayer){
                    double errorSignal = 0.0;
                    for(Edge e : hiddenNode.weightsOut){
                        errorSignal += e.to.errorSignal*e.weight*(1.0-hiddenNode.nodeValue)*hiddenNode.nodeValue;
                    }
                    hiddenNode.errorSignal = errorSignal;
                }

                //adjust weights for hidden to output
                for(Node hiddenNode : hiddenLayer){
                    for(Edge e: hiddenNode.weightsOut){
                        e.changeInWeight = -learningRate*e.to.errorSignal*e.from.nodeValue+momentum*e.changeInWeight;
                        e.weight += e.changeInWeight;
                    }
                }

                //adjust weights for input to hidden
                for(Node inputNode : inputLayer){
                    for(Edge e : inputNode.weightsOut){
                        e.changeInWeight = -learningRate*e.to.errorSignal*e.from.nodeValue+momentum*e.changeInWeight;
                        e.weight = e.changeInWeight;
                    }
                }
            }
            //Done each training pattern once
            trainingAccuracy = trainingAccuracy/trainingExamples.size()*100;

             generalizationAccuracy = 0.0;

            for(LanguageTrainingExample genEx : generalizationExamples){

                    ArrayList<Double> inputFields = genEx.getInputValues();

                    for(int i = 0; i < LanguageTrainingExample.ALPHABET.length();i++){
                        inputLayer.get(i).nodeValue = inputFields.get(i);
                    }

                    inputLayer.get(LanguageTrainingExample.ALPHABET.length()).nodeValue = -1.0;


                    for(Node hiddenNode : hiddenLayer){
                        double net = 0.0;
                        for(Edge e : hiddenNode.weightsIn){
                            net += e.weight*e.from.nodeValue;
                        }
                        hiddenNode.f(net);
                    }

                    for(Node outputNode : outputLayer){
                        double net = 0.0;
                        for(Edge e : outputNode.weightsIn){
                            net += e.weight*e.from.nodeValue;
                        }
                        outputNode.f(net);
                        if(outputNode.nodeValue >= 0.7){
                            outputNode.nodeValue = 1.0;
                        }
                        else if(outputNode.nodeValue <= 0.3){
                            outputNode.nodeValue = 0.0;
                        }
                    }

                    double accuracy = 1.0;
                    if(outputLayer.get(0).nodeValue != genEx.afrikaansClassification)
                        accuracy = 0.0;
                    if(outputLayer.get(1).nodeValue != genEx.englishClassification)
                        accuracy = 0.0;

                    generalizationAccuracy += accuracy;
            }

            generalizationAccuracy = generalizationAccuracy/generalizationExamples.size()*100;
            //Get to two decimal places
            DecimalFormat df = new DecimalFormat("#.##");
            generalizationAccuracy = Double.valueOf(df.format(generalizationAccuracy));
            trainingAccuracy = Double.valueOf(df.format(trainingAccuracy));

            result += epochCounter+","+trainingAccuracy+","+generalizationAccuracy+",\n";
        }
        return result.trim();
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
