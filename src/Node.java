import java.util.ArrayList;

/**
 * Created by wernermostert on 2015/05/24.
 */
public class Node {
    public ArrayList<Edge> weightsIn;
    public ArrayList<Edge> weightsOut;
    private ActivationFunction func;
    public double nodeValue;
    public double errorSignal;
    public String label;
    private boolean isBias;
    public double Ak = -1.0;

    public Node(ArrayList<Edge> weightsIn, ArrayList<Edge> weightsOut, ActivationFunction func,String label) {
        this.weightsIn = weightsIn;
        this.weightsOut = weightsOut;
        this.func = func;
        nodeValue = 0.0;
        this.label = label;
        isBias = false;
        errorSignal = 0.0;
    }

    public Node(ActivationFunction func,String label) {
        this.func = func;
        this.weightsIn = new ArrayList<Edge>();
        this.weightsOut = new ArrayList<Edge>();
        nodeValue = 0.0;
        this.label = label;
        isBias = false;
        errorSignal = 0.0;
    }

    public Node(ActivationFunction func,String label,boolean isBias) {
        this.func = func;
        this.weightsIn = new ArrayList<Edge>();
        this.weightsOut = new ArrayList<Edge>();
        nodeValue = 0.0;
        this.label = label;
        this.isBias = isBias;
        errorSignal = 0.0;
    }

    public double f(double net){
        if(isBias) {
            this.nodeValue = -1.0;
            return -1.0;
        }
        this.nodeValue = func.f(net);
        return nodeValue;
    }
}
