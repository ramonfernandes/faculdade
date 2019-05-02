package escalonador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {

    public static int fatiaTempo;
    public static int nProcessos;

    /*Leitura de Arquivo*/
    private static String setupFilePath(String fileName) {
        File currentDirFile = new File("");
        String separator = System.getProperty("file.separator");
        return currentDirFile.getAbsolutePath() +
                separator + "src" +
                separator + "main" +
                separator + "java" +
                separator + "escalonador" +
                separator + "testFiles" +
                separator +
                fileName;
    }

    /*Cria a lista de processos inicial em tempo zero
     * quando nenhum processo ainda está sendo executado*/
    public static List<Processo> setupListaDeProcessosAguardando(String file) throws IOException {
        String filePath = setupFilePath(file);
        FileReader filereader = new FileReader(filePath);
        BufferedReader buffer = new BufferedReader(filereader);
        nProcessos = Integer.parseInt(buffer.readLine());
        fatiaTempo = Integer.parseInt(buffer.readLine());

        ArrayList<Processo> result = new ArrayList<Processo>();
        Processo[] vetorAux = new Processo[nProcessos];
        for (int j = 0; j < nProcessos; j++) {
            String[] vetor = buffer.readLine().split(" ");
            Processo processo = new Processo(j + 1, Integer.parseInt(vetor[0]), Integer.parseInt(vetor[1]));
            if (vetor.length > 2)
                for (int index = 2; index < vetor.length; index++)
                    processo.addOperacaoDeEntradaESaida(Integer.parseInt(vetor[index]));
            vetorAux[j] = processo;
        }
        vetorAux = ordenaPorTempoDeChegada(vetorAux);
        for (Processo p : vetorAux)
            result.add(p);
        return result;
    }

    /*Ordena a lista de processos por ordem de chegada para facilitar a leitura do algoritmo
     * aumentando a velocidade ao não percorrer toda a lista procurando um processo que possa começar a ser executado*/
    public static Processo[] ordenaPorTempoDeChegada(Processo[] vetorDeProcesso) {
        for (int i = 0; i < vetorDeProcesso.length; i++)
            for (int j = 0; j < vetorDeProcesso.length; j++)
                if (vetorDeProcesso[i].getTempoChegada() < vetorDeProcesso[j].getTempoChegada()) {
                    Processo aux = vetorDeProcesso[i];
                    vetorDeProcesso[i] = vetorDeProcesso[j];
                    vetorDeProcesso[j] = aux;
                }
        return vetorDeProcesso;
    }

}
