package escalonador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//https://github.com/ramonfernandes/T1-Escalonador

/**
 * Autores: Bárbara Mesquita Ferreira e Ramon Fernandes
 * <p>
 * Objetivo: O algoritmo deve receber um arquivo de texto descrevendo um número n de processos
 * estes processos possuem tempo de chegada, tempo de execução, prioridade e tempos de acesso a operações de E/S.
 * Dado isso, o algoritmo deve simular a execução destes processos ultilizando um algoritmo de Round-Robin com prioridade
 */

public class Escalonador {

    public static Scanner input = new Scanner(System.in);
    public static List<Processo> listaProcessos = new ArrayList<Processo>();
    public static String FILE_PATH;
    private int nProcessos;
    private int fatiaTempo;
    private int tempo = 0;
    private int ciclo = 1;
    private Map<Integer, PriorityQueue<Processo>> mapEmExecucao = new HashMap<Integer, PriorityQueue<Processo>>();
    private ArrayList<Processo> listaAguardando;
    public static ArrayList<Processo> listaEncerrado = new ArrayList<>();
    public ArrayList<Processo> listaOperacoesEntradaESaida = new ArrayList<>();
    private String result = "";
    private Processo proximoProcesso;
    private static int tempoDeRespostaMedio = 0;
	private static int tempoDeEsperaMedio = 0;
	

    /* Método que orquestra as chamadas dos outros métodos
     * método "raíz"*/
    public String escalonadorRun() {
        try {
            listaAguardando = (ArrayList) setupListaAguardando();
            proximoProcesso = listaAguardando.remove(0);
            while (listaAguardando.size() != 0
                    || existeProcessoParaSerExecutadoNoMapa()
                    || !listaOperacoesEntradaESaida.isEmpty()) {
                adicionaProcessosProntosParaSeremExecutadosAFila();
                if (ciclo == 4)
                    encerraCiclo();
                if (existeProcessoParaSerExecutadoNoMapa()) {
                    iniciaCiclo(retornaPrioridadeMaximaDoMapa());
                    encerraCiclo();
                } else {
                    ciclo++;
                    result += "-";
                }
                tempo++;
                if (!listaOperacoesEntradaESaida.isEmpty()) {
                    List<Integer> indexesToRemove = new ArrayList<>();
                    for (Processo p : listaOperacoesEntradaESaida) {
                        int aux = p.getTempoEntradaESaida();
                        p.setTempoEntradaESaida(aux + 1);
                        if (p.getTempoEntradaESaida() == 4) {
                            adicionaProcessoAFilaDeExecucao(p);
                            p.setTempoEntradaESaida(0);
                            indexesToRemove.add(listaOperacoesEntradaESaida.indexOf(p));
                        }
                    }
                    for(int i : indexesToRemove)
                        listaOperacoesEntradaESaida.remove(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*Ordena a lista de processos por ordem de chegada para facilitar a leitura do algoritmo
     * aumentando a velocidade ao não percorrer toda a lista procurando um processo que possa começar a ser executado*/
    public Processo[] ordenaPorTempoDeChegada(Processo[] vetorDeProcesso) {
        for (int i = 0; i < vetorDeProcesso.length; i++)
            for (int j = 0; j < vetorDeProcesso.length; j++)
                if (vetorDeProcesso[i].getTempoChegada() < vetorDeProcesso[j].getTempoChegada()) {
                    Processo aux = vetorDeProcesso[i];
                    vetorDeProcesso[i] = vetorDeProcesso[j];
                    vetorDeProcesso[j] = aux;
                }
        return vetorDeProcesso;
    }

    /*Leitura de Arquivo*/
    private void setupFilePath() {
        File currentDirFile = new File("");
        String separator = System.getProperty("file.separator");
        FILE_PATH = currentDirFile.getAbsolutePath() + separator + "src" + separator + "main" + separator + "java" + separator + "escalonador/file4.txt";
    }

    /*Cria a lista de processos inicial em tempo zero
     * quando nenhum processo ainda está sendo executado*/
    public List<Processo> setupListaAguardando() throws IOException {
        setupFilePath();
        FileReader filereader = new FileReader(FILE_PATH);
        BufferedReader buffer = new BufferedReader(filereader);
        nProcessos = Integer.parseInt(buffer.readLine());
        fatiaTempo = Integer.parseInt(buffer.readLine());

        ArrayList<Processo> result = new ArrayList<Processo>();
        Processo[] vetorAux = new Processo[nProcessos];
        for (int j = 0; j < nProcessos; j++) {
            String[] vetor = buffer.readLine().split(" ");
            Processo processo = new Processo(j + 1, Integer.parseInt(vetor[0]), Integer.parseInt(vetor[1]),
                    Integer.parseInt(vetor[2]));
            if (vetor.length > 3)
                for (int index = 3; index < vetor.length; index++)
                    processo.addOperacaoDeEntradaESaida(Integer.parseInt(vetor[index]));
            vetorAux[j] = processo;
        }
        vetorAux = ordenaPorTempoDeChegada(vetorAux);
        for (Processo p : vetorAux)
            result.add(p);
        return result;
    }

    /*Método que representa uma fatia de tempo do processador
     * executa três processamentos e para, caso um processo mais prioritário entre no lugar o ciclo atual é interrompido*/
    //retorna true caso o ciclo seja completo e false caso o ciclo seja interrompido devido às finalização do processo
    public boolean iniciaCiclo(int index) {
        while (ciclo <= fatiaTempo) {
            if (existeProcessoComMaiorPrioridade(index))
                return false;
            mapEmExecucao.get(index).peek().decramentaDuracaoRestante();
            result += mapEmExecucao.get(index).peek().getId();
            if (mapEmExecucao.get(index).peek().getDuracaoRestante() == 0) {
                mapEmExecucao.get(index).peek().setTempoEncerramento(tempo);
                listaEncerrado.add(mapEmExecucao.get(index).remove());
                return false;
            }
            if(mapEmExecucao.get(index).peek().isOperacaoEntradaESaida()) {
                listaOperacoesEntradaESaida.add(mapEmExecucao.get(index).remove());
                return false;
            }
            ciclo++;
            tempo++;
            adicionaProcessosProntosParaSeremExecutadosAFila();
        }
        mapEmExecucao.get(index).add(mapEmExecucao.get(index).remove());
        return true;
    }

    /*Método que, quando chegado o tempo, remove o processo da filda de aguardando e insere na de prontos para executar*/
    public void adicionaProcessosProntosParaSeremExecutadosAFila() {
        if (proximoProcesso != null)
            while (proximoProcesso.getTempoChegada() <= tempo) {
                insereProcessoNoMapa(mapEmExecucao, proximoProcesso);
                if (listaAguardando.size() != 0) {
                    proximoProcesso = listaAguardando.remove(0);
                } else {
                    proximoProcesso = null;
                    break;
                }
            }
    }

    /*encerra o ciclo*/
    public void encerraCiclo() {
        ciclo = 1;
        tempo++;
        result += "C";
    }

    /*Insere processos na posição correta do map de filas*/
    public Map<Integer, PriorityQueue<Processo>> insereProcessoNoMapa(Map<Integer, PriorityQueue<Processo>> map, Processo processo) {
        if (!map.containsKey(processo.getPrioridade()))
            map.put(processo.getPrioridade(), new PriorityQueue<Processo>());
        map.get(processo.getPrioridade()).add(processo);
        return map;
    }

    /*Verifica no mapa inteiro se existe um processo que possa ser executado
     * feito apenas para que todas as verificações não precisem ser executadas para que nenhum processo seja executado*/
    public boolean existeProcessoParaSerExecutadoNoMapa() {
        boolean existeProcessoParaSerExecutado = false;
        for (Map.Entry<Integer, PriorityQueue<Processo>> entry : mapEmExecucao.entrySet())
            if (!entry.getValue().isEmpty())
                existeProcessoParaSerExecutado = true;
        return existeProcessoParaSerExecutado;
    }

    /*Retorna a fila de maior prioridade do mapa que possua um processo pronto para ser executado*/
    public int retornaPrioridadeMaximaDoMapa() {
        int result = 21;
        for (Map.Entry<Integer, PriorityQueue<Processo>> entry : mapEmExecucao.entrySet())
            if (!entry.getValue().isEmpty() && entry.getValue().peek().getPrioridade() < result)
                result = entry.getValue().peek().getPrioridade();
        return result;
    }

    /*Verifica se existe no momento de chamada um processo com prioridade maior que o Processo sendo executado no momento
     * interrompe o ciclo se necessário*/
    public boolean existeProcessoComMaiorPrioridade(int prioridadeAtual) {
        prioridadeAtual--;
        while (prioridadeAtual >= 0) {
            if (mapEmExecucao.containsKey(prioridadeAtual))
                if (!mapEmExecucao.get(prioridadeAtual).isEmpty())
                    return true;
            prioridadeAtual--;
        }
        return false;
    }

    public void adicionaProcessoAFilaDeExecucao(Processo p) {
        if (!mapEmExecucao.containsKey(p.getPrioridade()))
            mapEmExecucao.put(p.getId(), new PriorityQueue<Processo>());
        mapEmExecucao.get(p.getId()).add(p);
    }
    
    public static void somaTempoDeEspera(int valor) {
    	tempoDeEsperaMedio += valor;
    }
    
    public static void somaTempoDeResposta(int valor) {
    	tempoDeRespostaMedio += valor;
    }
    
    public int getNProcessos() {
    	return nProcessos;
    }
    
    public int getTempodeRespostaTotal() {
    	return tempoDeRespostaMedio;
    }
    
    public int getTempodeEsperaTotal() {
    	return tempoDeEsperaMedio;
    }

}
