package escalonador;

import java.util.ArrayList;

public class Processo implements Comparable {

    private int id;
    private int fatiaDeTempoAtual;
    private int tempoChegada;
    private int tempoExecucao;
    private int duracaoRestante;
    private int tempoEncerramento;
    private ArrayList<Integer> operacoesDeES = new ArrayList<>();
    private int tempoEntradaESaida = 0;

    public int getTempoEntradaESaida() {
        return tempoEntradaESaida;
    }

    public void setTempoEntradaESaida(int tempoEntradaESaida) {
        this.tempoEntradaESaida = tempoEntradaESaida;
    }

    public void setTempoEncerramento(int tempoEncerramento) {
        this.tempoEncerramento = tempoEncerramento;
    }

    public Processo(int id, int tempoChegada, int tempoExecucao) {
        this.id = id;
        this.tempoChegada = tempoChegada;
        this.tempoExecucao = tempoExecucao;
        this.duracaoRestante = tempoExecucao;
        this.tempoEncerramento = 0;
        this.fatiaDeTempoAtual = 4;
    }

    public int getTempoExecucao() {
        return tempoExecucao;
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

    public int getDuracaoRestante() {
        return duracaoRestante;
    }

    public Processo setDuracaoRestante(int duracaoRestante) {
        this.duracaoRestante = duracaoRestante;
        return this;
    }

    public boolean hasOperacaoEntradaESaida() {
        if (!operacoesDeES.isEmpty()) {
            Integer op = operacoesDeES.remove(0);
            op--;
            if (op == 0)
                return true;
            else {
                operacoesDeES.add(0, op);
                return false;
            }
        }
        return false;
    }

    public void addOperacaoDeEntradaESaida(int tempoDaOperacao) {
        operacoesDeES.add(tempoDaOperacao);
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

    public int getFatiaDeTempoAtual() {
        return fatiaDeTempoAtual;
    }

    public void decrementaFatiaDeTempoAtual() {
        this.fatiaDeTempoAtual--;
    }

    public void setFatiaDeTempoAtual(int fatiaDeTempoAtual) {
        this.fatiaDeTempoAtual = fatiaDeTempoAtual;
    }
}

