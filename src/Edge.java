/**
 * Created by wernermostert on 2015/05/24.
 */
public class Edge {
    public Node from;
    public Node to;
    public double weight;
    public double changeInWeight;
    public String label;

    public Edge(Node to, Node from, String label) {
        this.to = to;
        this.from = from;
        this.label = label;
        changeInWeight = 0.0;
    }

    public Edge(Node from, Node to, String label, double weight) {

        this.from = from;
        this.to = to;
        this.weight = weight;
        this.label = label;
        changeInWeight = 0.0;
    }
}
