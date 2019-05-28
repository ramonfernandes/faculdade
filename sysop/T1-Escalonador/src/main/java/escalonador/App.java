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
 * Autores: Matheus Ferreira e Ramon Fernandes
 * <p>
 * Objetivo: O algoritmo deve receber um arquivo de texto descrevendo um número n de processos
 * estes processos possuem tempo de chegada, tempo de execução, prioridade e tempos de acesso a operações de E/S.
 * Dado isso, o algoritmo deve simular a execução destes processos ultilizando um algoritmo de Round-Robin
 */
public class App {

    public static void main(String[] args) {

        Escalonador escalonador = new Escalonador();
        System.out.println(escalonador.escalonadorRun("file"));
        escalonador = new Escalonador();
        System.out.println(escalonador.escalonadorRun("teste1.txt"));
        escalonador = new Escalonador();
        System.out.println(escalonador.escalonadorRun("teste2.txt"));
        escalonador = new Escalonador();
        System.out.println(escalonador.escalonadorRun("teste3.txt"));
        escalonador = new Escalonador();
        System.out.println(escalonador.escalonadorRun("teste4.txt"));
    }
}