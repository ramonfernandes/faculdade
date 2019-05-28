import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Bloco.zeraCouter();
        Reader reader = new Reader();
        BufferedReader buffer = reader.setupFilePath("file1.txt");
        Gerenciador gerenciador = new Gerenciador();

        String modo = buffer.readLine();
        gerenciador.setupFaixaDeMemoria(buffer.readLine(), buffer.readLine());
        List<String> list = new ArrayList<>();
        while (true) {
            String line = buffer.readLine();
            if (line != null)
                list.add(line);
            else
                break;
        }
        List<Bloco> result = gerenciador.run(list);
        System.out.println("\n\nESTADO FINAL DA FAIXA DE MEMÓRIA:\n\n" + gerenciador.getFaixaDeMemoria().printList(result));
    }
}
