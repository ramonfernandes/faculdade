import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static BufferedReader buffer;
    private static Gerenciador gerenciador;

    public static void main(String[] args) {
        setup();
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setup() {
        Bloco.zeraCouter();
        Reader reader = new Reader();
        buffer = reader.setupFilePath("file1.txt");
        gerenciador = new Gerenciador();
    }
}
