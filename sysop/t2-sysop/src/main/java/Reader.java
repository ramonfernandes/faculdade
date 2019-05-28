import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Reader {

    private String FILE_PATH = "";

    /*Leitura de Arquivo*/
    public BufferedReader setupFilePath(String filename) {
        try {
            File currentDirFile = new File("");
            String separator = System.getProperty("file.separator");
            FILE_PATH = currentDirFile.getAbsolutePath() + separator + "src" + separator + "main" + separator + "java" + separator + "/" + filename;

            FileReader filereader = new FileReader(FILE_PATH);
            BufferedReader buffer = new BufferedReader(filereader);
            return buffer;
        } catch (FileNotFoundException e) {
            System.out.println("Erro na leitura do arquivo");
        }
        return null;
    }
}
