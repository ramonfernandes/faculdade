package escalonador;

import java.util.ArrayList;

public class Processo implements Comparable {

    private int id;
    private int tempoChegada;
    private int tempoExecucao;
    private int prioridade;
    private int duracaoRestante;
    private int tempoEncerramento;
    private int tempoEspera;
    ArrayList<Integer> operacoesDeES = new ArrayList<>();
    private int tempoEntradaESaida = 0;

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public ArrayList<Integer> getOperacoesDeES() {
        return operacoesDeES;
    }

    public void setOperacoesDeES(ArrayList<Integer> operacoesDeES) {
        this.operacoesDeES = operacoesDeES;
    }

    public int getTempoEntradaESaida() {
        return tempoEntradaESaida;
    }

    public void setTempoEntradaESaida(int tempoEntradaESaida) {
        this.tempoEntradaESaida = tempoEntradaESaida;
    }

    public int getTempoEncerramento() {
        return tempoEncerramento;
    }

    public void setTempoEncerramento(int tempoEncerramento) {
        this.tempoEncerramento = tempoEncerramento;
    }

    public Processo(int id, int tempoChegada, int tempoExecucao, int prioridade) {
        this.id = id;
        this.tempoChegada = tempoChegada;
        this.tempoExecucao = tempoExecucao;
        this.duracaoRestante = tempoExecucao;
        this.prioridade = prioridade;
        this.tempoEncerramento = 0;
    }

    public Processo() {
        this.id = 0;
        this.tempoChegada = 0;
        this.tempoExecucao = 0;
        this.duracaoRestante = 0;
        this.prioridade = 0;
    }

    public int getTempoExecucao() {
        return tempoExecucao;
    }

    public Processo setTempoExecucao(int tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
        return this;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public Processo setPrioridade(int prioridade) {
        this.prioridade = prioridade;
        return this;
    }

    public int getId() {
        return id;
    }

    public Processo setId(int id) {
        this.id = id;
        return this;
    }

    public int getTempoChegada() {
        return tempoChegada;
    }

    public Processo setTempoChegada(int tempoChegada) {
        this.tempoChegada = tempoChegada;
        return this;
    }

    public int getDuracaoRestante() {
        return duracaoRestante;
    }

    public Processo setDuracaoRestante(int duracaoRestante) {
        this.duracaoRestante = duracaoRestante;
        return this;
    }

    public void incrementaTempoEspera() {
        tempoEspera += 1;
    }

    public boolean isOperacaoEntradaESaida() {
        if (!operacoesDeES.isEmpty())
            return (tempoExecucao - duracaoRestante) == operacoesDeES.get(0);
        return false;
    }

    public String getTempoDeEspera(String result) {
        int trocasDeContexto = 0;
        for (Character c : result.substring(
                result.indexOf(String.valueOf(this.getId())),
                result.lastIndexOf(String.valueOf(this.getId())))
                .toCharArray())
            if (c == 'C')
                trocasDeContexto++;

    	Escalonador.somaTempoDeEspera((this.tempoEncerramento - this.tempoChegada - this.tempoExecucao - trocasDeContexto));
        return "Tempo de Espera para P" + this.getId() + " é de " +
                (this.tempoEncerramento - this.tempoChegada - this.tempoExecucao - trocasDeContexto);
    }

    public String getTempoDeResposta(String result) {
    	Escalonador.somaTempoDeResposta((result.indexOf(String.valueOf(this.getId())) - this.getTempoChegada() - 1));
        return "Tempo de Resposta para P" + this.getId() + " é de " + (result.indexOf(String.valueOf(this.getId())) - this.getTempoChegada() - 1);
    }

    public void addOperacaoDeEntradaESaida(int tempoDaOperacao) {
        operacoesDeES.add(tempoDaOperacao);
    }

    public void removeFirstFromListaEntradaESaida(){
        operacoesDeES.remove(0);
    }

    @Override
    public String toString() {
        return "P" + getId() + "(" + getTempoChegada() + " " + getTempoExecucao() + ")";
    }

    public void decramentaDuracaoRestante() {
        this.duracaoRestante--;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}

