import java.util.ArrayList;

/**
 * Created by wernermostert on 2015/05/24.
 */
public class Node {
    public ArrayList<Edge> weightsIn;
    public ArrayList<Edge> weightsOut;
    private ActivationFunction func;
    public double nodeValue;
    public String label;

    public Node(ArrayList<Edge> weightsIn, ArrayList<Edge> weightsOut, ActivationFunction func,String label) {
        this.weightsIn = weightsIn;
        this.weightsOut = weightsOut;
        this.func = func;
        nodeValue = 0.0;
        this.label = label;
    }

    public Node(ActivationFunction func,String label) {
        this.func = func;
        this.weightsIn = new ArrayList<Edge>();
        this.weightsOut = new ArrayList<Edge>();
        nodeValue = 0.0;
        this.label = label;
    }

    public double f(double net){
        this.nodeValue = func.f(net);
        return nodeValue;
    }
}
