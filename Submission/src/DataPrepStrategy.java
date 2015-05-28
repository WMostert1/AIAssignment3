/**
 * Created by wernermostert on 2015/05/23.
 * Data PrepStrategy is the abstract class for the Strategy pattern to do pre pre-processing on the neural network
 */
public interface DataPrepStrategy<T> {
    public LanguageTrainingExample transform(LanguageTrainingExample languageTrainingExample);
}
