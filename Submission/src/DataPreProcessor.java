import java.util.ArrayList;

/**
 * Created by wernermostert on 2015/05/23.
 * This class is responsible for doing pre-processing on data
 */
public class DataPreProcessor{
    private ArrayList<DataPrepStrategy> transforms = new ArrayList<DataPrepStrategy>();

    public void addDataPrepStrategy(DataPrepStrategy dataPrepStrategy){
        transforms.add(dataPrepStrategy);
    }

    public void removeDataPrepStrategy(DataPrepStrategy dataPrepStrategy){
        transforms.remove(dataPrepStrategy);
    }

    /**
     * Applies the transformations required on the training example
     * @param languageTrainingExample the training example
     * @return The transformed training example
     */
    public LanguageTrainingExample applyTransforms(LanguageTrainingExample languageTrainingExample){
        for(DataPrepStrategy f : transforms)
            languageTrainingExample = f.transform(languageTrainingExample);

        return languageTrainingExample;
    }

    /**
     * Applies the transformations required on the training examples
     * @param languageTrainingExamples the training examples
     * @return The transformed training examples
     */
    public ArrayList<LanguageTrainingExample> applyTransforms(ArrayList<LanguageTrainingExample> languageTrainingExamples){
        for(LanguageTrainingExample languageTrainingExample : languageTrainingExamples)
            for(DataPrepStrategy f : transforms)
                languageTrainingExample = f.transform(languageTrainingExample);

        return languageTrainingExamples;
    }
}
