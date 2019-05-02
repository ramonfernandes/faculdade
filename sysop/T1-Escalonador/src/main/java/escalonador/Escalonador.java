package escalonador;

import java.io.IOException;
import java.util.*;

import static escalonador.Config.fatiaTempo;
import static escalonador.Config.setupListaDeProcessosAguardando;

//https://github.com/ramonfernandes/T1-Escalonador

/**
 * Autores: Ramon Fernandes
 * <p>
 * Objetivo: O algoritmo deve receber um arquivo de texto descrevendo um número n de processos
 * estes processos possuem tempo de chegada, tempo de execução, prioridade e tempos de acesso a operações de E/S.
 * Dado isso, o algoritmo deve simular a execução destes processos ultilizando um algoritmo de Round-Robin com prioridade
 */

public class Escalonador {

    private static final String FILE_NAME = "teste4.txt";
    private int tempo = 1;
    private LinkedList<Processo> filaDeProcessosProntosParaExecucao = new LinkedList<>();
    public LinkedList<Processo> filaOperacoesEntradaESaida = new LinkedList<>();
    private ArrayList<Processo> listaAguardando;
    public static ArrayList<Processo> listaEncerrado = new ArrayList<>();
    private String result = "";

    /* Método que orquestra as chamadas dos outros métodos
     * método "raíz"*/
    public String escalonadorRun() {
        try {
            listaAguardando = (ArrayList) setupListaDeProcessosAguardando(FILE_NAME);
            while (existirProcessoPorSerExecutado()) {
                adicionaProcessosProntosParaSeremExecutadosAFila();
                while (!existeProcessoParaSerExecutadoNaLista()) {
                    result += "-";
                    tempo++;
                    adicionaProcessosProntosParaSeremExecutadosAFila();
                    if (existeProcessoParaSerExecutadoNaLista()) {
                        trocaDeContexto();
                    }
                }
                iniciaCiclo(filaDeProcessosProntosParaExecucao.remove());
                trocaDeContexto();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void removeProcessosDaListaDeEntradaESaida() {
        if (!filaOperacoesEntradaESaida.isEmpty()) {
            List<Integer> indexesToRemove = new ArrayList<>();
            for (Processo p : filaOperacoesEntradaESaida) {
                if (tempo - p.getTempoEntradaESaida() >= 2) {
                    p.setTempoEntradaESaida(0);
                    indexesToRemove.add(filaOperacoesEntradaESaida.indexOf(p));
                }
            }
            for (int i : indexesToRemove)
                filaDeProcessosProntosParaExecucao.addLast(filaOperacoesEntradaESaida.remove(i));
        }
    }

    private boolean existeProcessoParaSerExecutadoNaLista() {
        return !filaDeProcessosProntosParaExecucao.isEmpty();
    }

    /*Método que representa uma fatia de tempo do processador
     executa três processamentos e para, caso um processo mais prioritário entre no lugar o ciclo atual é interrompido
    retorna true caso o ciclo seja completo e false caso o ciclo seja interrompido devido às finalização do processo
    ou operação de entrada e saída*/
    private boolean iniciaCiclo(Processo processo) {
        while (processo.getFatiaDeTempoAtual() >= 1) {
            adicionaProcessosProntosParaSeremExecutadosAFila();
            processo.decramentaDuracaoRestante();
            processo.decrementaFatiaDeTempoAtual();
            result += processo.getId();
            if (processo.getDuracaoRestante() == 0) {
                processo.setTempoEncerramento(tempo);
                listaEncerrado.add(processo);
                return false;
            }
            if (processo.hasOperacaoEntradaESaida()) {
                processo.setTempoEntradaESaida(tempo);
                filaOperacoesEntradaESaida.add(processo);
                return false;
            }
            tempo++;
            removeProcessosDaListaDeEntradaESaida();
        }
        processo.setFatiaDeTempoAtual(4);
        filaDeProcessosProntosParaExecucao.addLast(processo);
        return true;
    }

    private void trocaDeContexto() {
        result += "C";
        tempo++;
    }

    /*Método que, quando chegado o tempo, remove o processo da filda de aguardando e insere na de prontos para executar*/
    public void adicionaProcessosProntosParaSeremExecutadosAFila() {
        removeProcessosDaListaDeEntradaESaida();
        while (existirProcessoAguardandoProntoParaSerExecutado()) {
            filaDeProcessosProntosParaExecucao.addLast(listaAguardando.remove(0));
        }
    }

    private boolean existirProcessoAguardandoProntoParaSerExecutado() {
        if (!listaAguardando.isEmpty())
            return listaAguardando.get(0).getTempoChegada() <= tempo;
        return false;
    }

    private boolean existirProcessoPorSerExecutado() {
        return listaAguardando.size() != 0
                || !filaDeProcessosProntosParaExecucao.isEmpty()
                || !filaOperacoesEntradaESaida.isEmpty();
    }

    public int getFatiaTempo() {
        return fatiaTempo;
    }
}
