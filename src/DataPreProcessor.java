import java.util.ArrayList;

/**
 * Created by wernermostert on 2015/05/23.
 */
public class DataPreProcessor{
    private ArrayList<DataPrepStrategy> transforms = new ArrayList<DataPrepStrategy>();

    public void addDataPrepStrategy(DataPrepStrategy dataPrepStrategy){
        transforms.add(dataPrepStrategy);
    }

    public void removeDataPrepStrategy(DataPrepStrategy dataPrepStrategy){
        transforms.remove(dataPrepStrategy);
    }

    public LanguageTrainingExample applyTransforms(LanguageTrainingExample languageTrainingExample){
        for(DataPrepStrategy f : transforms)
            languageTrainingExample = f.transform(languageTrainingExample);

        return languageTrainingExample;
    }

    public ArrayList<LanguageTrainingExample> applyTransforms(ArrayList<LanguageTrainingExample> languageTrainingExamples){
        for(LanguageTrainingExample languageTrainingExample : languageTrainingExamples)
            for(DataPrepStrategy f : transforms)
                languageTrainingExample = f.transform(languageTrainingExample);

        return languageTrainingExamples;
    }
}
