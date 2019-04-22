package escalonador;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

//https://github.com/ramonfernandes/T1-Escalonador
/**
 * Autores: Ramon Fernandes
 *
 * Objetivo: O algoritmo deve receber um arquivo de texto descrevendo um número n de processos
 * estes processos possuem tempo de chegada, tempo de execução, prioridade e tempos de acesso a operações de E/S.
 * Dado isso, o algoritmo deve simular a execução destes processos ultilizando um algoritmo de Round-Robin com prioridade
 */
public class App {

    public static void main(String[] args) {

        Escalonador escalonador = new Escalonador();
        String result = escalonador.escalonadorRun();
        System.out.println(result);
        for(Processo p : Escalonador.listaEncerrado)
            System.out.println(p.getTempoDeEspera(result));
        for(Processo p : Escalonador.listaEncerrado)
            System.out.println(p.getTempoDeResposta(result));

        System.out.println("Tempo de Espera Medio: " + (escalonador.getTempodeEsperaTotal()/Config.nProcessos));
        System.out.println("Tempo de Resposta Medio: " + (escalonador.getTempodeRespostaTotal()/Config.nProcessos));
    }
}