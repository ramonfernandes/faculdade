import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /*
     Autor: Matheus Ferreira e Ramon Fernandes
     Data: 17 Jun 2019

     O problema consiste em receber uma faixa de memória com blocos a serem adicionados na mesma
    * o algoritmo deve fazer a gestão destes blocos, inserindo-os, removendo-os e guardando os que não conseguirem ser
    * alocados para sua inserção caso memória seja liberada*/

    /*Observações sobre o uso do algoritmo:
     *       - O arquivo de arquivo que será lido deve ser configurado passando o nome do mesmo para o metodo
     *       setupFilePath
     */
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
