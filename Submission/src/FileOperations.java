import java.io.*;
import java.util.ArrayList;

/**
 * Created by wernermostert on 2015/04/28.
 * This is a simple utility library for file operations.
 */

public class FileOperations {
    /**
     * Reads a text file
     * @param filename The name of the file
     * @return The contents of the file
     * @throws java.io.FileNotFoundException The file does not exist
     * @throws java.io.IOException An IO error occured
     */
    public static String readFile(String filename) throws IOException
    {
        String content = "";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null)
            if(!line.equals(""))
            content += line+"\r\n";

        return content;
    }

    /**
     * Writes content to a file.
     * @param content String representation of file content to be written
     * @param filename Name of the file to which is to be written
     */
    public static void writeToFile(String content, String filename){
        FileWriter fw;
        File file;
        try {
            file=new File(filename);
            if(!file.exists()) {
                if(!file.createNewFile()) throw new IOException();
            }
            fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getFileNamesAt(String path, String expression){
        ArrayList<String> results = new ArrayList<String>();

        File[] files = new File(path).listFiles();
        if(files == null) return results;

        for (File file : files) {
            if (file.isFile()) {
                if(file.getName().matches(".*"+expression+".*"))
                results.add(file.getName());
            }
        }
        return results;
    }
}
