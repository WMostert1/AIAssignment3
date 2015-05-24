/**
 * Created by wernermostert on 2015/05/24.
 */
public class SigmoidActivationFunction implements ActivationFunction {
    @Override
    public double f(double net) {
        return 1.0/(1+Math.pow(Math.E,-net));
    }
}
